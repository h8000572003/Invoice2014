package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 14/12/9.
 */
public class MainDTO {
    private String number="";
    private String yyymm;

    public String getYyymm() {
        return yyymm;
    }

    public void setYyymm(String yyymm) {
        this.yyymm = yyymm;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
