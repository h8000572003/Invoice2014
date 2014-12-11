package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 14/12/9.
 */
public enum Award {
    VerySpecial(0, 8),//特別獎
    Special(1, 8),//特獎
    Top(2, 8),//頭獎
    Second(3, 7),//
    Thrid(4, 6),//
    Fouth(5, 5),//第四講
    Fifth(6, 4),//第五
    Sixth(7, 3),//六
    ExactSix(8, 3),//額外六
    None(9, -1),
    Wait(10, -1),
    Finding(11, -1),;;
    ;
    final int code;
    final int length;


    Award(int code, int length) {
        this.code = code;
        this.length = length;
    }

    public static Award getAwardByCode(int code) {
        Award[] awards =
                Award.values();

        for (Award award : awards) {
            if (code == award.code) {
                return award;
            }
        }
        return null;
    }
}
