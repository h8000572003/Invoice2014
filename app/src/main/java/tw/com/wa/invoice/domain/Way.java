package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 15/1/23.
 */
public enum Way {


    VERYSPECIAL(Award.Veryspecial, "同期統一發票收執聯8位數號碼與上列號碼相同者獎金%d元"),///
    Special(Award.Special, "同期統一發票收執聯8位數號碼與上列號碼相同者獎金%d元"),////
    Top(Award.Top, "同期統一發票收執聯8位數號碼與上列號碼相同者獎金%d"),//
    Sec(Award.Second, "同期統一發票收執聯末7 位數號碼與頭獎中獎號碼末7 位相同者各得獎金%d元"),//
    Thrid(Award.Thrid, "同期統一發票收執聯末6 位數號碼與頭獎中獎號碼末6 位相同者各得獎金%d元"),//
    Four(Award.Fouth, "同期統一發票收執聯末5 位數號碼與頭獎中獎號碼末5 位相同者各得獎金%d元"),//
    Fifth(Award.Fifth, "同期統一發票收執聯末4 位數號碼與頭獎中獎號碼末4 位相同者各得獎金%d元"),//
    Six(Award.Sixth, "同期統一發票收執聯末3 位數號碼與頭獎中獎號碼末 位相同者各得獎金%d"),//
    EX_SIX(Award.Exactsix, "同期統一發票收執聯末3 位數號碼與上列號碼相同者各得獎金%d"),//
    ;
    final Award award;
    final String name;


    Way(Award award, String name) {
        this.award = award;
        this.name = name;
    }

    public Award getAward() {
        return award;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return String.format(name, award.dollar);
    }

    public String getCode() {
        return award.unCode;
    }
}
