package tw.com.wa.invoice.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Andy on 14/12/12.
 */

// Armor.java

@ParseClassName("InvoiceFlag")
public class InvoiceFlag extends ParseObject {

    private boolean isFlag;
    private String title;

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        this.put("title",title);

    }

    public boolean isFlag() {

        return getBoolean("isFlag");
    }

    public void setFlag(boolean isFlag) {

        put("isFlag",isFlag);
    }
}
