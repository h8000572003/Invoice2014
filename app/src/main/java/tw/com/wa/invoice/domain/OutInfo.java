package tw.com.wa.invoice.domain;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.util.DateUtil;
import tw.com.wa.invoice.util.InvoYm;

/**
 * Created by Andy on 2015/1/9.
 */
public class OutInfo implements WiningInfo, Serializable {

    private final static String TITLE_TEMPLATE = "%04d/%02d-%02d";
    private InvoiceInfoV2 infoV2 = null;
    private List<Invoice> invoices = null;
    private InvoYm invoYm = null;
    private String stagingYm;
    private String title;
    private WiningBean bean;

    public OutInfo(WiningBean bean) {
        this.bean = bean;
        this.makeTitle();
        this.invoYm = DateUtil.getYm(bean.getInvoYm());
        this.infoV2 = this.makeInvoiceInfo();
        this.stagingYm = DateUtil.yyymm(this.invoYm.getEnd());

        this.changeAwrd(bean);

        BeanUtil.setInfo(this);
    }

    public WiningBean getBean() {
        return bean;
    }

    public void setBean(WiningBean bean) {
        this.bean = bean;
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

    @Override
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

    private void addInvoice(Award award, List<Invoice> invoices, String number, boolean isSpecail) {
        if (!TextUtils.isEmpty(number)) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(award.unCode);
            invoice01.setNumber(number);
            invoice01.setSpecialize(isSpecail);
            invoices.add(invoice01);

        }
    }

    private List<Invoice> makeSixPrize() {
        List<Invoice> invoiceList = new ArrayList<>();


        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo1(), true);
        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo2(), true);
        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo3(), true);
        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo4(), true);
        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo5(), true);
        this.addInvoice(Award.Exactsix, invoiceList, bean.getSixthPrizeNo6(), true);


        return invoiceList;
    }


    private List<Invoice> makeFirstPrize() {

        final List<Invoice> invoiceList = new ArrayList<>();
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo1(), false);
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo2(), false);
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo3(), false);
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo4(), false);
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo5(), false);
        this.addInvoice(Award.Top, invoiceList, bean.getFirstPrizeNo6(), true);

        return invoiceList;
    }


    private List<Invoice> makeSuperPrize() {

        final List<Invoice> invoiceList = new ArrayList<>();
        this.addInvoice(Award.Veryspecial, invoiceList, bean.getSuperPrizeNo(), true);

        return invoiceList;
    }

    private List<Invoice> makeSpcPrize() {

        final List<Invoice> invoiceList = new ArrayList<>();

        this.addInvoice(Award.Special, invoiceList, bean.getSpcPrizeNo(), true);
        this.addInvoice(Award.Special, invoiceList, bean.getSpcPrizeNo2(), true);
        this.addInvoice(Award.Special, invoiceList, bean.getSpcPrizeNo3(), true);


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
