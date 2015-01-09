package tw.com.wa.invoice.domain;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tw.com.wa.invoice.util.DateUtil;
import tw.com.wa.invoice.util.InvoYm;

/**
 * Created by Andy on 2015/1/9.
 */
public class OutInfo extends WiningBean implements WiningInfo {

    private InvoiceInfoV2 infoV2 = null;
    private List<Invoice> invoices = null;

    private final static String TITLE_TEMPLATE = "%04d/%02d-%04d/%02d";
    private InvoYm invoYm;
    private String stagingYm;

    public OutInfo() {

        this.invoYm = DateUtil.getYm(super.getInvoYm());
        this.infoV2 = this.makeInvoiceInfo();
        this.stagingYm = DateUtil.yyymm(this.invoYm.getEnd());

    }

    private InvoiceInfoV2 makeInvoiceInfo() {


        InvoiceInfoV2 info = new InvoiceInfoV2();
        info.setCheck(true);
        info.setInfo("");
        info.setTitle(this.getTitle(super.getInvoYm()));
        info.setDateOfBegin(this.invoYm.getBeging().getTime());
        info.setDateOfEnd(this.invoYm.getEnd().getTime());

        this.invoices = new ArrayList<>();
        this.invoices.addAll(this.makeSuperPrize());
        this.invoices.addAll(this.makeSpcPrize());
        this.invoices.addAll(this.makeFirstPrize());
        this.invoices.addAll(this.makeSixPrize());
        return info;

    }

    private List<Invoice> makeSixPrize() {
        List<Invoice> invoiceList = new ArrayList<>();

        if (!TextUtils.isEmpty(super.getSixthPrizeNo1())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Exactsix.unCode);
            invoice01.setNumber(super.getFirstPrizeNo1());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(super.getSixthPrizeNo2())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Exactsix.unCode);
            invoice01.setNumber(super.getSixthPrizeNo2());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(super.getSixthPrizeNo3())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Exactsix.unCode);
            invoice01.setNumber(super.getSixthPrizeNo3());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);

        }
        return invoiceList;
    }


    private List<Invoice> makeFirstPrize() {
        List<Invoice> invoiceList = new ArrayList<>();
        if (!TextUtils.isEmpty(super.getFirstPrizeNo1())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(super.getFirstPrizeNo1());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(super.getFirstPrizeNo2())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(super.getFirstPrizeNo2());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(super.getFirstPrizeNo3())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(super.getFirstPrizeNo3());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(super.getFirstPrizeNo4())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(super.getFirstPrizeNo4());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(super.getFirstPrizeNo5())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(super.getFirstPrizeNo5());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(super.getFirstPrizeNo6())) {

            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Top.unCode);
            invoice01.setNumber(super.getFirstPrizeNo6());
            invoice01.setSpecialize(false);
            invoiceList.add(invoice01);
        }
        return invoiceList;
    }


    private List<Invoice> makeSuperPrize() {
        List<Invoice> invoiceList = new ArrayList<>();
        if (!TextUtils.isEmpty(super.getSuperPrizeNo())) {
            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Special.unCode);
            invoice01.setNumber(super.getSuperPrizeNo());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);
        }
        return invoiceList;
    }

    private List<Invoice> makeSpcPrize() {

        List<Invoice> invoiceList = new ArrayList<>();
        if (!TextUtils.isEmpty(super.getSpcPrizeNo())) {
            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Special.unCode);
            invoice01.setNumber(super.getSpcPrizeNo());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);
        }
        if (!TextUtils.isEmpty(super.getSpcPrizeNo2())) {
            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Special.unCode);
            invoice01.setNumber(super.getSpcPrizeNo2());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);

        }
        if (!TextUtils.isEmpty(super.getSpcPrizeNo3())) {
            Invoice invoice01 = new Invoice();
            invoice01.setTitle(getTitle());
            invoice01.setAwards(Award.Special.unCode);
            invoice01.setNumber(super.getSpcPrizeNo3());
            invoice01.setSpecialize(true);
            invoiceList.add(invoice01);
        }

        return invoiceList;
    }

    private String getTitle(String invoYm) {

        int year = Integer.parseInt(invoYm.substring(0, 3)) + 1911;
        return String.format(TITLE_TEMPLATE, year, Integer.parseInt(invoYm.substring(3)) - 1, year, Integer.parseInt(invoYm.substring(3)));

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
