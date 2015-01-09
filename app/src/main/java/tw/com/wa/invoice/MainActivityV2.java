package tw.com.wa.invoice;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;

import tw.com.wa.invoice.domain.MainDTO;

/**
 * Created by Andy on 2015/1/9.
 */
public class MainActivityV2 extends ActionBarActivity {
    private KeyboardView keyboardView = null;
    private MainDTO dto;
    private Vibrator myVibrator = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dto = new MainDTO();
        this.myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }
}
