//package tw.com.wa.invoice;
//
//
//import java.io.IOException;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.google.zxing.BinaryBitmap;
//import com.google.zxing.MultiFormatReader;
//import com.google.zxing.PlanarYUVLuminanceSource;
//import com.google.zxing.RGBLuminanceSource;
//import com.google.zxing.Result;
//
//import com.google.zxing.common.HybridBinarizer;
//
//
//import android.graphics.Bitmap;
//import android.graphics.PixelFormat;
//import android.hardware.Camera;
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
///**
// * Created by Andy on 2014/12/29.
// */
//public class CameraActivity extends ActionBarActivity {
//    /**
//     * Called when the activity is first created.
//     */
//    private SurfaceView sfvCamera;
//    private SFHCamera sfhCamera;
//    private ImageView imgView;
//    private View centerView;
//    private TextView txtScanResult;
//    private Timer mTimer;
//    private MyTimerTask mTimerTask;
//    // 按照標準HVGA
//    final static int width = 480;
//    final static int height = 320;
//    int dstLeft, dstTop, dstWidth, dstHeight;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        this.setTitle("Android條碼/二維碼識別Demo-----hellogv");
//        imgView = (ImageView) this.findViewById(R.id.ImageView01);
//        centerView = (View) this.findViewById(R.id.centerView);
//        sfvCamera = (SurfaceView) this.findViewById(R.id.sfvCamera);
//        sfhCamera = new SFHCamera(sfvCamera.getHolder(), width, height,
//                previewCallback);
//        txtScanResult = (TextView) this.findViewById(R.id.txtScanResult);
//// 初始化計時器
//        mTimer = new Timer();
//        mTimerTask = new MyTimerTask();
//        mTimer.schedule(mTimerTask, 0, 80);
//    }
//
//
//    class MyTimerTask extends TimerTask {
//        @Override
//        public void run() {
//            if (dstLeft == 0) {//只賦值一次
//                dstLeft = centerView.getLeft() * width
//                        / getWindowManager().getDefaultDisplay().getWidth();
//                dstTop = centerView.getTop() * height
//                        / getWindowManager().getDefaultDisplay().getHeight();
//                dstWidth = (centerView.getRight() - centerView.getLeft()) * width
//                        / getWindowManager().getDefaultDisplay().getWidth();
//                dstHeight = (centerView.getBottom() - centerView.getTop()) * height
//                        / getWindowManager().getDefaultDisplay().getHeight();
//            }
//            sfhCamera.AutoFocusAndPreviewCallback();
//        }
//    }
//
//    /**
//     * 自動對焦後輸出圖片
//     */
//    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
//        @Override
//        public void onPreviewFrame(byte[] data, Camera arg1) {
////取得指定範圍的幀的資料
//            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, width, height, dstLeft, dstTop, dstWidth, dstHeight, false);
//
////取得灰度圖
//            Bitmap mBitmap = source.renderCroppedGreyscaleBitmap();
////顯示灰度圖
//            imgView.setImageBitmap(mBitmap);
//            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//            MultiFormatReader reader = new MultiFormatReader();
//            try {
//                Result result = reader.decode(bitmap);
//                String strResult = "BarcodeFormat:"
//                        + result.getBarcodeFormat().toString() + " text:"
//                        + result.getText();
//                txtScanResult.setText(strResult);
//            } catch (Exception e) {
//                txtScanResult.setText("Scanning");
//            }
//        }
//    };
//}
//
//
//}
//
//
