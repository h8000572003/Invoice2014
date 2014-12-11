package tw.com.wa.invoice.util;

import java.util.ArrayList;
import java.util.List;

import tw.com.wa.invoice.domain.Invoice;

/**
 * Created by Andy on 14/12/9.
 */
public class GetDataCompentImpl implements GetDataCompent {



    @Override
    public List<Invoice> getInvoice(String yyymm) {

        List<Invoice> invoices = new ArrayList<Invoice>();

        invoices.add(this.build("0","22267127"));
        invoices.add(this.build("1","35396804"));
        invoices.add(this.build("1","15352117"));
        invoices.add(this.build("1","54709991"));


        return invoices;
    }

    @Override
    public Invoice checkNumber(String yyymm, String number) {
        return null;
    }


    private Invoice build(String awards, String number) {
        return new Invoice(awards, number);
    }

}
