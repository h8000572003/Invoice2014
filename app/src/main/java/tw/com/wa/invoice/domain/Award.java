package tw.com.wa.invoice.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy on 14/12/12.
 */
public enum Award {
    Veryspecial("E7", 8, "特別獎", 1, 1),//特別獎
    Special("E8", 8, "特獎", 2, 1),//特獎
    Exactsix("E9", 3, "增開六獎", 9, 1),//額外六


    Top("O1", 8, "頭獎", 3, 1),//頭獎
    Second("O2", 7, "二獎", 4, 1),//
    Thrid("O3", 6, "三獎", 5, 1),//
    Fouth("O4", 5, "四獎", 6, 1),//第四講
    Fifth("O5", 4, "五獎", 7, 1),//第五
    Sixth("O6", 3, "六獎", 8, 1),//六


    ;

    public final static Map<String, Award> MAP;

    public final String unCode;
    public final int checKLegth;
    public String message = "";
    public final int order;
    public final int dollar;


    static {

        Map<String, Award> map = new HashMap<String, Award>();

        for (Award award : Award.values()) {
            map.put(award.unCode, award);
        }
        MAP = Collections.unmodifiableMap(map);

    }

    public static Award lookup(String unCode) {
        return MAP.get(unCode);
    }


    Award(String unCode, int checKLegth, String message, int order, int dollar) {
        this.checKLegth = checKLegth;
        this.unCode = unCode;
        this.message = message;
        this.order = order;
        this.dollar = dollar;
    }


}
