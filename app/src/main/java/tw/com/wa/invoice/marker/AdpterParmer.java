package tw.com.wa.invoice.marker;

/**
 * Created by Andy on 2015/1/15.
 */
public class AdpterParmer {
    private String key;
    private String value;

    public AdpterParmer(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public AdpterParmer() {
        this("", "");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getKey() + "=" + this.getValue();
    }

    public static AdpterParmer get(String key, String value) {
        return new AdpterParmer(key, value);
    }


}
