package tw.com.wa.invoice.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2014/12/17.
 */
public class RecDTO {
    private List<MainNumber> mainNumbers=new ArrayList<MainNumber>();

    public List<MainNumber> getMainNumbers() {
        return mainNumbers;
    }

    public void setMainNumbers(List<MainNumber> mainNumbers) {
        this.mainNumbers = mainNumbers;
    }
}
