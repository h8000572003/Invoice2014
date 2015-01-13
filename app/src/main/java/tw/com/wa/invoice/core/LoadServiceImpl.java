package tw.com.wa.invoice.core;

import android.app.Activity;
import android.util.Log;

import tw.com.wa.invoice.api.LoadService;
import tw.com.wa.invoice.domain.LoadDTO;
import tw.com.wa.invoice.domain.OutInfo;
import tw.com.wa.invoice.domain.WiningBean;
import tw.com.wa.invoice.marker.ApiGetter;
import tw.com.wa.invoice.marker.WiningsAdapter;
import tw.com.wa.invoice.util.CommomUtil;
import tw.com.wa.invoice.util.InvoiceBusinessException;

/**
 * Created by Andy on 2015/1/7.
 */
public class LoadServiceImpl implements LoadService {
    private static final String TAG = "LoadServiceImpl";

    private ApiGetter<WiningBean> marker = new ApiGetter();

    @Override
    public void loadData(LoadDTO dto, Activity activity) throws InvoiceBusinessException {
        try {

            final String ym = CommomUtil.getLastYm();

            dto.setYm(this.getTheNewYm(dto, ym));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new InvoiceBusinessException(e.getMessage());
        }
    }

    private String getTheNewYm(LoadDTO dto, String ym) {

        final WiningBean bean =
                this.getTheNewWinings(dto, ym);



        if (bean.getCode().equals("901")) {
            final String preYm = CommomUtil.getPreYm(ym);
            return this.getTheNewYm(dto, preYm);

        } else {
            final OutInfo info = new OutInfo(bean);
            dto.setOutInfo(info);
            return ym;
        }
    }

    private WiningBean getTheNewWinings(LoadDTO dto, String ym) throws InvoiceBusinessException {
        WiningsAdapter adapter = new WiningsAdapter(ym);
        marker.setAdapter(adapter);
        return marker.getQuery();
    }
}
