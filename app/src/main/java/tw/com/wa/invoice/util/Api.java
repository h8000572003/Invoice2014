package tw.com.wa.invoice.util;

/**
 * Created by Andy on 2015/1/6.
 */
public enum Api {


    QuryWin("查詢中獎發票號碼清單", "/PB2CAPIVAN/invapp/InvApp", "0.2"),
    QuryHead("查詢發票表頭", "/PB2CAPIVAN/invapp/InvApp", "0.3"),
    QuryDetial("查詢發票明細", "/PB2CAPIVAN/invapp/InvApp", "0.3"),
    QuryLove("愛心碼查詢", "/PB2CAPIVAN/loveCodeapp/qryLoveCode", "0.2"),
    QueryMobileHead("載具發票表頭查詢", "/PB2CAPIVAN/invServ/InvServ", "0.1"),
    QueryMobileTitile("載具發票號碼明細查詢", "/PB2CAPIVAN/invServ/InvServ", "0.1"),
    DonateInvoice("載具發票捐贈", "/PB2CAPIVAN/CarInv/Donate", "0.1");

    final String desc;
    final String url;
    final String version;


    Api(String desc, String url, String version) {
        this.desc = desc;
        this.url = url;
        this.version = version;
    }

    protected String getBaseSql() {
        return
                GetCompent.ROOT_API + url;
    }

    public String getBaseSqlAppendCommonValue() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("appID=");
        buffer.append(GetCompent.API_ID);
        buffer.append("&");
        buffer.append("UUID=");
        buffer.append("&");
        buffer.append("version=");
        buffer.append(version);

        return this.getBaseSql() + "?" + buffer.toString();
    }
}
