package tw.com.wa.invoice.util;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 14/12/9.
 */
public class GetDataCompentImpl implements GetDataCompent {


    @Override
    public List<Invoice> getInvoice(String yyymm) {

        //FIXME 須改為網路上抓取
        List<Invoice> invoices = new ArrayList<Invoice>();

        invoices.add(this.build(Award.Veryspecial.unCode, "22267127", true));
        invoices.add(this.build(Award.Top.unCode, "35396804", false));
        invoices.add(this.build(Award.Top.unCode, "15352117", false));
        invoices.add(this.build(Award.Top.unCode, "54709991", false));


        invoices.add(this.build(Award.Exactsix.unCode, "114", true));
        invoices.add(this.build(Award.Exactsix.unCode, "068", true));
        invoices.add(this.build(Award.Exactsix.unCode, "476", true));
        invoices.add(this.build(Award.Exactsix.unCode, "970", true));

        return invoices;
    }

    @Override
    public Invoice checkNumber(String yyymm, String number) {
        return null;
    }


    private Invoice build(String awards, String number, boolean isSepecialze) {
        return new Invoice(awards, number, isSepecialze);
    }

}
