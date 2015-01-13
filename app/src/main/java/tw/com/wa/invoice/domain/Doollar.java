package tw.com.wa.invoice.domain;

/**
 * Created by Andy on 2015/1/13.
 */
public class Doollar extends Invoice implements InvoiceDoollar {
    private int door = 0;


    public Doollar(WiningBean bean) {


        Award award =
                Award.lookup(getAwards());


        door = this.getMoney(bean, award);
    }

    private int getMoney(WiningBean bean, Award award) {
        String money = null;
        switch (award) {

            case Veryspecial:
                money = bean.getSuperPrizeAmt();

                break;


            case Special:
                money = bean.getSpcPrizeAmt();

                break;
            case Exactsix:
                money = bean.getSixthPrizeAmt();
                break;
            case Top:
                money = bean.getFirstPrizeAmt();
                break;

            case Second:
                money = bean.getSecondPrizeAmt();
                break;
            case Thrid:

                money = bean.getThirdPrizeAmt();
                break;

            case Fouth:

                money = bean.getFourthPrizeAmt();
                break;

            case Fifth:
                money = bean.getFifthPrizeAmt();

                break;
            case Sixth:
                money = bean.getSixthPrizeAmt();
                break;

            default:
                return 0;

        }
        return Integer.parseInt(money);


    }

    @Override
    public int getDoor() {
        return door;
    }
}
