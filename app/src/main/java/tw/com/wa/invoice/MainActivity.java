package tw.com.wa.invoice;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import tw.com.wa.invoice.domain.Award;
import tw.com.wa.invoice.domain.MainDTO;
import tw.com.wa.invoice.util.CommomUtil;
import tw.com.wa.invoice.util.GetDataCompent;
import tw.com.wa.invoice.util.GetDataCompentImpl;


public class MainActivity extends ActionBarActivity {


    private Spinner spinner = null;

    private TextView invoviceLabel = null;
    private TextView invoiceContent = null;
    private TextView messageLabel = null;

    private MainDTO dto;


    private GetDataCompent getDataCompent = new GetDataCompentImpl();

    private CommomUtil commomUtil = new CommomUtil();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dto = new MainDTO();


        //change
        this.dto.setInvoices(this.getDataCompent.getInvoice(""));


        this.spinner = (Spinner) this.findViewById(R.id.monthSpinner);
        this.invoviceLabel = (TextView) this.findViewById(R.id.invoviceLabel);
        this.invoiceContent = (TextView) this.findViewById(R.id.invoiceContent);
        this.messageLabel = (TextView) this.findViewById(R.id.messageLabel);

        String content = "";
        content += "特別獎\t22267127\n";
        content += "特獎\t31075480\n";
        content += "頭獎\t35396804、15352117、54709991\n";
        content += "增開六獎\t114、068、476、970\n";
        invoiceContent.setText(content);


        String[] contents = new String[]{

                "5-6月份", "7-8月份"
        };
        BaseAdapter spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, contents);


        this.spinner.setAdapter(spinnerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 點擊號碼
     *
     * @param view
     */
    public void clickValue(View view) {
        this.messageLabel.setVisibility(View.INVISIBLE);


        final TextView label = (TextView) view;

        dto.setNumber(dto.getNumber() + label.getText());

        invoviceLabel.setTextColor(Color.BLACK);
        invoviceLabel.setText(dto.getNumber());


        final Award award =
                commomUtil.check3NumberAward(dto.getNumber(), dto.getInvoices());

        switch (award) {


            case Wait:

                break;

            case None:


                this.messageLabel.setVisibility(View.VISIBLE);
                this.messageLabel.setText("沒得獎，換一張");

                dto.setNumber("");


                break;


            case Finding:

                this.messageLabel.setVisibility(View.VISIBLE);
                this.messageLabel.setText("有中大獎的可能，請輸入完整發票號");


                dto.setNumber("");
                break;

            case VerySpecial:
                break;
            case Special:
                break;
            case Top:
                break;
            case Second:
                break;
            case Thrid:
                break;
            case Fouth:
                break;
            case Fifth:
                break;
            case Sixth:

                invoviceLabel.setText("中一張六獎");
                invoviceLabel.setTextColor(Color.RED);
                dto.setNumber("");

                break;


            case ExactSix:
                break;
        }


    }
}
