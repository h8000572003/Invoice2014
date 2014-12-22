package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 14/12/21.
 */

public enum AwardInfo {


    Veryspecial(10000000),//特別獎
    Special(2000000),//特獎
    Exactsix(200),//額外六


    Top(200000),//頭獎
    Second(40000),//
    Thrid(10000),//
    Fouth(4000),//第四講
    Fifth(1000),//第五
    Sixth(200),//六
    ;

    final int door;

    AwardInfo(int door) {
        this.door = door;
    }
}
