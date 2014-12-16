package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 2014/12/16.
 */
public class MainNumber {
    private Award award;
    private int countOfInvoice;


    public int getCountOfInvoice() {
        return countOfInvoice;
    }

    public void setCountOfInvoice(int countOfInvoice) {
        this.countOfInvoice = countOfInvoice;
    }

    public MainNumber(Award award) {
        this.award = award;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }


    public int sum() {
        return award.dollar * countOfInvoice;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MainNumber) {
            if (this.award == ((MainNumber) o).award) {
                return true;
            } else {
                return false;
            }
        }
        return super.equals(o);
    }
}
