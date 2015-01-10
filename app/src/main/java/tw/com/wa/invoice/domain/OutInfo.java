package tw.com.wa.invoice.domain;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.util.DateUtil;
import tw.com.wa.invoice.util.InvoYm;

/**
 * Created by Andy on 2015/1/9.
 */
public class OutInfo implements WiningInfo {

    private final static String TITLE_TEMPLATE = "%04d/%02d-%04d/%02d";
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
        this.title = String.format(TITLE_TEMPLATE, year, Integer.parseInt(bean.getInvoYm().substring(3)) - 1, year, Integer.parseInt(bean.getInvoYm().substring(3)));
    }

    private List<Invoice> makeSixPrize() {
        List<Invoice> invoiceList = new ArrayList<>();

        if (!TextUtils.isEmpty(bean.getSixthPrizeNo1())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Exactsix.unCode);
            invoice01.setNumber(bean.getFirstPrizeNo1());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(bean.getSixthPrizeNo2())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Exactsix.unCode);
            invoice01.setNumber(bean.getSixthPrizeNo2());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(bean.getSixthPrizeNo3())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Exactsix.unCode);
            invoice01.setNumber(bean.getSixthPrizeNo3());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);

        }
        return invoiceList;
    }


    private List<Invoice> makeFirstPrize() {
        List<Invoice> invoiceList = new ArrayList<>();
        if (!TextUtils.isEmpty(bean.getFirstPrizeNo1())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(bean.getFirstPrizeNo1());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(bean.getFirstPrizeNo2())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(bean.getFirstPrizeNo2());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(bean.getFirstPrizeNo3())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(bean.getFirstPrizeNo3());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(bean.getFirstPrizeNo4())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(bean.getFirstPrizeNo4());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(bean.getFirstPrizeNo5())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(bean.getFirstPrizeNo5());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(bean.getFirstPrizeNo6())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(bean.getFirstPrizeNo6());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);
        }
        return invoiceList;
    }


    private List<Invoice> makeSuperPrize() {
        List<Invoice> invoiceList = new ArrayList<>();
        if (!TextUtils.isEmpty(bean.getSuperPrizeNo())) {
            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Special.unCode);
            invoice01.setNumber(bean.getSuperPrizeNo());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);
        }
        return invoiceList;
    }

    private List<Invoice> makeSpcPrize() {

        List<Invoice> invoiceList = new ArrayList<>();
        if (!TextUtils.isEmpty(bean.getSpcPrizeNo())) {
            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Special.unCode);
            invoice01.setNumber(bean.getSpcPrizeNo());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);
        }
        if (!TextUtils.isEmpty(bean.getSpcPrizeNo2())) {
            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Special.unCode);
            invoice01.setNumber(bean.getSpcPrizeNo2());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(bean.getSpcPrizeNo3())) {
            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Special.unCode);
            invoice01.setNumber(bean.getSpcPrizeNo3());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);
        }

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
        return this.infoV2.getTitle();
    }

    @Override
    public String getStagingYm() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public InvoYm getStages() {
        return this.invoYm;
    }





}
