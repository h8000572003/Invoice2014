package tw.com.wa.invoice.core;

import android.app.Activity;
import android.util.Log;

import tw.com.wa.invoice.api.LoadService;
import tw.com.wa.invoice.domain.Award;
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

            this.changeAwrd(bean);

            dto.setOutInfo(info);
            return ym;
        }
    }

    private void changeAwrd(WiningBean bean) {

        for (Award award : Award.values()) {
            award.dollar = this.getMoney(award, bean);
        }
    }

    private int getMoney(Award award, WiningBean bean) {
        switch (award) {
            case Veryspecial:
                return Integer.parseInt(bean.getSuperPrizeAmt());
            case Special:
                return Integer.parseInt(bean.getSpcPrizeAmt());
            case Exactsix:
                return Integer.parseInt(bean.getSixthPrizeAmt());
            case Top:
                return Integer.parseInt(bean.getFirstPrizeAmt());
            case Second:
                return Integer.parseInt(bean.getSecondPrizeAmt());
            case Thrid:
                return Integer.parseInt(bean.getThirdPrizeAmt());
            case Fouth:
                return Integer.parseInt(bean.getFourthPrizeAmt());
            case Fifth:
                return Integer.parseInt(bean.getFifthPrizeAmt());
            case Sixth:
                return Integer.parseInt(bean.getSixthPrizeAmt());
            default:
                return 0;
        }
    }

    private WiningBean getTheNewWinings(LoadDTO dto, String ym) throws InvoiceBusinessException {
        WiningsAdapter adapter = new WiningsAdapter(ym);
        marker.setAdapter(adapter);
        return marker.getQuery();
    }
}
