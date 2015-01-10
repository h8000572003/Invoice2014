package tw.com.wa.invoice.util;

/**
 * Created by Andy on 2015/1/6.
 */
public class InvoiceBusinessException extends RuntimeException {
    private String message;


    public InvoiceBusinessException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
