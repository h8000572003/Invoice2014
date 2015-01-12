package tw.com.wa.invoice.domain;

import android.os.Parcel;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.util.DateUtil;
import tw.com.wa.invoice.util.InvoYm;

/**
 * Created by Andy on 2015/1/9.
 */
public class OutInfo implements WiningInfo {

    private final static String TITLE_TEMPLATE = "%04d/%02d-%02d";
    private InvoiceInfoV2 infoV2 = null;
    private List<Invoice> invoices = null;
    private InvoYm invoYm;
    private String stagingYm;
    private String title;
    private WiningBean bean;

    public OutInfo(WiningBean bean) {
        this.bean = bean;
        this.makeTitle();
        this.invoYm = DateUtil.getYm(bean.getInvoYm());
        this.infoV2 = this.makeInvoiceInfo();
        this.stagingYm = DateUtil.yyymm(this.invoYm.getEnd());


    }

    public InvoiceInfoV2 getInfoV2() {
        return this.infoV2;
    }


    private InvoiceInfoV2 makeInvoiceInfo() {


        InvoiceInfoV2 info = new InvoiceInfoV2();
        this.infoV2 = info;

        info.setCheck(true);
        info.setInfo("");
        info.setTitle(this.getTitle(bean.getInvoYm()));
        info.setDateOfBegin(this.invoYm.getBeging().getTime());
        info.setDateOfEnd(this.invoYm.getEnd().getTime());

        this.invoices = new ArrayList<>();
        this.invoices.addAll(this.makeSuperPrize());
        this.invoices.addAll(this.makeSpcPrize());
        this.invoices.addAll(this.makeFirstPrize());
        this.invoices.addAll(this.makeSixPrize());
        return info;

    }

    private void makeTitle() {
        int year = Integer.parseInt(bean.getInvoYm().substring(0, 3)) + 1911;
        this.title = String.format(TITLE_TEMPLATE, year, Integer.parseInt(bean.getInvoYm().substring(3)) - 1, Integer.parseInt(bean.getInvoYm().substring(3)));
    }

    private void addInvoice(Award award, List<Invoice> invoices, String number) {
        if (!TextUtils.isEmpty(number)) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(award.unCode);
            invoice01.setNumber(number);
            invoice01.setSpecialize(true);
            invoices.add(invoice01);

        }
    }

    private List<Invoice> makeSixPrize() {
        List<Invoice> invoiceList = new ArrayList<>();


        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo1());
        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo2());
        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo3());


        return invoiceList;
    }


    private List<Invoice> makeFirstPrize() {

        final List<Invoice> invoiceList = new ArrayList<>();
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo1());
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo2());
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo3());
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo4());
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo5());
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo6());

        return invoiceList;
    }


    private List<Invoice> makeSuperPrize() {

        final List<Invoice> invoiceList = new ArrayList<>();
        this.addInvoice(Award.Veryspecial, invoiceList, bean.getSuperPrizeNo());

        return invoiceList;
    }

    private List<Invoice> makeSpcPrize() {

        final List<Invoice> invoiceList = new ArrayList<>();

        this.addInvoice(Award.Special, invoiceList, bean.getSpcPrizeNo());
        this.addInvoice(Award.Special, invoiceList, bean.getSpcPrizeNo2());
        this.addInvoice(Award.Special, invoiceList, bean.getSpcPrizeNo3());


        return invoiceList;
    }

    private String getTitle(String invoYm) {

        return title;


    }

    @Override
    public List<Invoice> getInvoice() {
        return this.invoices;
    }

    @Override
    public String getTitle() {
        return this.title;
    }



    @Override
    public InvoYm getStages() {
        return this.invoYm;
    }



}
