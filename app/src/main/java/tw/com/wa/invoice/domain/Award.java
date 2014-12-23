package tw.com.wa.invoice.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy on 14/12/12.
 */
public enum Award {
    Veryspecial("E7", AwardInfo.Veryspecial, 8, "特別獎", 1, 10000000),//特別獎
    Special("E8", AwardInfo.Special, 8, "特獎", 2, 2000000),//特獎
    Exactsix("E9", AwardInfo.Exactsix, 3, "增開六獎", 9, 200),//額外六


    Top("O1", AwardInfo.Top, 8, "頭獎", 3, 200000),//頭獎
    Second("O2", AwardInfo.Second, 7, "二獎", 4, 40000),//
    Thrid("O3", AwardInfo.Thrid, 6, "三獎", 5, 10000),//
    Fouth("O4", AwardInfo.Fouth, 5, "四獎", 6,4000),//第四講
    Fifth("O5", AwardInfo.Fifth, 4, "五獎", 7, 1000),//第五
    Sixth("O6", AwardInfo.Sixth, 3, "六獎", 8, 200),//六


    ;

    public final static Map<String, Award> MAP;

    public final String unCode;
    public final int checKLegth;
    public final int order;
    public final int dollar;
    public final AwardInfo info;

    static {

        Map<String, Award> map = new HashMap<String, Award>();

        for (Award award : Award.values()) {
            map.put(award.unCode, award);
        }
        MAP = Collections.unmodifiableMap(map);

    }

    public String message = "";

    Award(String unCode, AwardInfo info, int checKLegth, String message, int order, int dollar) {
        this.info = info;
        this.checKLegth = checKLegth;
        this.unCode = unCode;
        this.message = message;
        this.order = order;
        this.dollar = dollar;
    }

    public static Award lookup(String unCode) {
        return MAP.get(unCode);
    }


}
