package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 15/1/23.
 */
public enum Way {


    VERYSPECIAL(Award.Veryspecial, "同期統一發票收執聯8位數號碼與上列號碼相同者獎金%d元"),///
    Special(Award.Special, "同期統一發票收執聯8位數號碼與上列號碼相同者獎金200 萬元"),////
    Top(Award.Top, "同期統一發票收執聯8位數號碼與上列號碼相同者獎金20"),//
    Sec(Award.Second, "同期統一發票收執聯末7 位數號碼與頭獎中獎號碼末7 位相同者各得獎金4 萬元"),//
    Thrid(Award.Thrid, "同期統一發票收執聯末6 位數號碼與頭獎中獎號碼末6 位相同者各得獎金1 萬元"),//
    Four(Award.Fouth, "同期統一發票收執聯末5 位數號碼與頭獎中獎號碼末5 位相同者各得獎金4 千元"),//
    Fifth(Award.Fifth, "同期統一發票收執聯末4 位數號碼與頭獎中獎號碼末4 位相同者各得獎金1千元"),//
    Six(Award.Sixth, "同期統一發票收執聯末3 位數號碼與頭獎中獎號碼末 位相同者各得獎金2 "),//
    EX_SIX(Award.Exactsix, "同期統一發票收執聯末3 位數號碼與上列號碼相同者各得獎金2"),//
    ;
    final Award award;
    final String name;


    Way(Award award, String name) {
        this.award = award;
        this.name = name;
    }

    public String getMessage() {
        return String.format(name, award.dollar);
    }

    public String getCode() {
        return award.unCode;
    }
}
