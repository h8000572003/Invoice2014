//package tw.com.wa.invoice.qr;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import tw.com.wa.invoice.R;
//
///**
// * This is demo code to accompany the Mobiletuts+ tutorial:
// * - Using Barcode Scanning in Android Apps
// * <p/>
// * Sue Smith
// * May 2013
// */
//public class QRCodeActivity extends FragmentActivity implements OnClickListener {
//
//    //UI instance variables
//    private Button scanBtn;
//    private TextView formatTxt, contentTxt;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.camera_activity);
//
//        //instantiate UI items
//        scanBtn = (Button) findViewById(R.id.scan_button);
//        formatTxt = (TextView) findViewById(R.id.scan_format);
//        contentTxt = (TextView) findViewById(R.id.scan_content);
//
//        //listen for clicks
//        scanBtn.setOnClickListener(this);
//    }
//
//    public void onClick(View v) {
//        //check for scan button
//        if (v.getId() == R.id.scan_button) {
//            //instantiate ZXing integration class
//            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
//            //start scanning
//            scanIntegrator.initiateScan();
//        }
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        //retrieve result of scanning - instantiate ZXing object
//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//        //check we have a valid result
//        if (scanningResult != null) {
//            //get content from Intent Result
//            String scanContent = scanningResult.getContents();
//            //get format name of data scanned
//            String scanFormat = scanningResult.getFormatName();
//            //output to UI
//            formatTxt.setText("FORMAT: " + scanFormat);
//            contentTxt.setText("CONTENT: " + scanContent);
//        } else {
//            //invalid scan data or scan canceled
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "No scan data received!", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
//
//}
