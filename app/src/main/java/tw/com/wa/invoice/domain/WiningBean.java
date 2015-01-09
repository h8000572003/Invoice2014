package tw.com.wa.invoice.domain;

import java.io.Serializable;

/**
 * Created by Andy on 2015/1/6.
 */
public class WiningBean  implements WiningOutInfo {
    private String v = "";//版本號碼
    private String code = "";//訊息回應碼
    private String msg = "";//系統回應訊息
    private String invoYm = "";//查詢開獎期別
    private String superPrizeNo = "";//千萬特獎號碼

    private String spcPrizeNo = "";//特獎號碼
    private String spcPrizeNo2 = "";//特獎號碼 2
    private String spcPrizeNo3 = "";//特獎號碼3

    private String firstPrizeNo1 = "";//頭獎號碼 1
    private String firstPrizeNo2 = "";//頭獎號碼2
    private String firstPrizeNo3 = "";//頭獎號碼3
    private String firstPrizeNo4 = "";//頭獎號碼4
    private String firstPrizeNo5 = "";//頭獎號碼5>
    private String firstPrizeNo6 = "";//頭獎號碼6
    private String firstPrizeNo7 = "";//頭獎號碼7
    private String firstPrizeNo8 = "";//頭獎號碼8
    private String firstPrizeNo9 = "";//頭獎號碼9
    private String firstPrizeNo10 = "";//頭獎號碼10
    private String sixthPrizeNo1 = "";//六獎號碼 1
    private String sixthPrizeNo2 = "";//六獎號碼 2
    private String sixthPrizeNo3 = "";//六獎號碼 3

    private String superPrizeAmt = "";//千萬特獎金額
    private String spcPrizeAmt = "";//特獎金額
    private String firstPrizeAmt = "";//頭獎金額
    private String secondPrizeAmt = "";//二獎金額
    private String thirdPrizeAmt = "";//三獎金額
    private String fourthPrizeAmt = "";//四獎金額
    private String fifthPrizeAmt = "";//五獎金額
    private String sixthPrizeAmt = "";//六獎金額

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getInvoYm() {
        return invoYm;
    }

    public void setInvoYm(String invoYm) {
        this.invoYm = invoYm;
    }

    public String getSuperPrizeNo() {
        return superPrizeNo;
    }

    public void setSuperPrizeNo(String superPrizeNo) {
        this.superPrizeNo = superPrizeNo;
    }

    public String getSpcPrizeNo() {
        return spcPrizeNo;
    }

    public void setSpcPrizeNo(String spcPrizeNo) {
        this.spcPrizeNo = spcPrizeNo;
    }

    public String getSpcPrizeNo2() {
        return spcPrizeNo2;
    }

    public void setSpcPrizeNo2(String spcPrizeNo2) {
        this.spcPrizeNo2 = spcPrizeNo2;
    }

    public String getSpcPrizeNo3() {
        return spcPrizeNo3;
    }

    public void setSpcPrizeNo3(String spcPrizeNo3) {
        this.spcPrizeNo3 = spcPrizeNo3;
    }

    public String getFirstPrizeNo1() {
        return firstPrizeNo1;
    }

    public void setFirstPrizeNo1(String firstPrizeNo1) {
        this.firstPrizeNo1 = firstPrizeNo1;
    }

    public String getFirstPrizeNo2() {
        return firstPrizeNo2;
    }

    public void setFirstPrizeNo2(String firstPrizeNo2) {
        this.firstPrizeNo2 = firstPrizeNo2;
    }

    public String getFirstPrizeNo3() {
        return firstPrizeNo3;
    }

    public void setFirstPrizeNo3(String firstPrizeNo3) {
        this.firstPrizeNo3 = firstPrizeNo3;
    }

    public String getFirstPrizeNo4() {
        return firstPrizeNo4;
    }

    public void setFirstPrizeNo4(String firstPrizeNo4) {
        this.firstPrizeNo4 = firstPrizeNo4;
    }

    public String getFirstPrizeNo5() {
        return firstPrizeNo5;
    }

    public void setFirstPrizeNo5(String firstPrizeNo5) {
        this.firstPrizeNo5 = firstPrizeNo5;
    }

    public String getFirstPrizeNo6() {
        return firstPrizeNo6;
    }

    public void setFirstPrizeNo6(String firstPrizeNo6) {
        this.firstPrizeNo6 = firstPrizeNo6;
    }

    public String getFirstPrizeNo7() {
        return firstPrizeNo7;
    }

    public void setFirstPrizeNo7(String firstPrizeNo7) {
        this.firstPrizeNo7 = firstPrizeNo7;
    }

    public String getFirstPrizeNo8() {
        return firstPrizeNo8;
    }

    public void setFirstPrizeNo8(String firstPrizeNo8) {
        this.firstPrizeNo8 = firstPrizeNo8;
    }

    public String getFirstPrizeNo9() {
        return firstPrizeNo9;
    }

    public void setFirstPrizeNo9(String firstPrizeNo9) {
        this.firstPrizeNo9 = firstPrizeNo9;
    }

    public String getFirstPrizeNo10() {
        return firstPrizeNo10;
    }

    public void setFirstPrizeNo10(String firstPrizeNo10) {
        this.firstPrizeNo10 = firstPrizeNo10;
    }

    public String getSixthPrizeNo1() {
        return sixthPrizeNo1;
    }

    public void setSixthPrizeNo1(String sixthPrizeNo1) {
        this.sixthPrizeNo1 = sixthPrizeNo1;
    }

    public String getSixthPrizeNo2() {
        return sixthPrizeNo2;
    }

    public void setSixthPrizeNo2(String sixthPrizeNo2) {
        this.sixthPrizeNo2 = sixthPrizeNo2;
    }

    public String getSixthPrizeNo3() {
        return sixthPrizeNo3;
    }

    public void setSixthPrizeNo3(String sixthPrizeNo3) {
        this.sixthPrizeNo3 = sixthPrizeNo3;
    }

    public String getSuperPrizeAmt() {
        return superPrizeAmt;
    }

    public void setSuperPrizeAmt(String superPrizeAmt) {
        this.superPrizeAmt = superPrizeAmt;
    }

    public String getSpcPrizeAmt() {
        return spcPrizeAmt;
    }

    public void setSpcPrizeAmt(String spcPrizeAmt) {
        this.spcPrizeAmt = spcPrizeAmt;
    }

    public String getFirstPrizeAmt() {
        return firstPrizeAmt;
    }

    public void setFirstPrizeAmt(String firstPrizeAmt) {
        this.firstPrizeAmt = firstPrizeAmt;
    }

    public String getSecondPrizeAmt() {
        return secondPrizeAmt;
    }

    public void setSecondPrizeAmt(String secondPrizeAmt) {
        this.secondPrizeAmt = secondPrizeAmt;
    }

    public String getThirdPrizeAmt() {
        return thirdPrizeAmt;
    }

    public void setThirdPrizeAmt(String thirdPrizeAmt) {
        this.thirdPrizeAmt = thirdPrizeAmt;
    }

    public String getFourthPrizeAmt() {
        return fourthPrizeAmt;
    }

    public void setFourthPrizeAmt(String fourthPrizeAmt) {
        this.fourthPrizeAmt = fourthPrizeAmt;
    }

    public String getFifthPrizeAmt() {
        return fifthPrizeAmt;
    }

    public void setFifthPrizeAmt(String fifthPrizeAmt) {
        this.fifthPrizeAmt = fifthPrizeAmt;
    }

    public String getSixthPrizeAmt() {
        return sixthPrizeAmt;
    }

    public void setSixthPrizeAmt(String sixthPrizeAmt) {
        this.sixthPrizeAmt = sixthPrizeAmt;
    }
}
