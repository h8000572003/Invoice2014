package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 14/12/12.
 */
public enum  Rule {

    Veryspecia(Award.veryspecial,new int[]{1,2}),//



    ;

     Rule(Award award, int[] numberOfMatchs) {
        this.award = award;
        this.numberOfMatchs = numberOfMatchs;
    }

    final Award award;
    final int[]numberOfMatchs;
}
