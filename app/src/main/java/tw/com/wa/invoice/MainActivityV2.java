package tw.com.wa.invoice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by Andy on 2015/1/9.
 */
public class MainActivityV2 extends ActionBarActivity {


    private static final String TAG = "MainActivityV2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_main_layout);

        try {
            Bundle bundle =
                    getIntent().getExtras();


            Serializable serializable =
                    bundle.getSerializable("dto");
            Toast.makeText(this, serializable.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }


}
