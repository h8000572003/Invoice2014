package tw.com.wa.invoice.core;

import android.app.Activity;
import android.util.Log;

import tw.com.wa.invoice.api.LoadService;
import tw.com.wa.invoice.domain.LoadDTO;
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

            WiningBean bean=marker.getQuery();


            dto.setWiningBean(bean);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new InvoiceBusinessException(e.getMessage());
        }
    }

    private WiningBean getTheNewWinings() throws InvoiceBusinessException {
        WiningsAdapter adapter = new WiningsAdapter(CommomUtil.getLastYm());
        marker.setAdapter(adapter);
        return marker.getQuery();
    }
}
