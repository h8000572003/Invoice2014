package tw.com.wa.invoice;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Andy on 15/1/23.
 */
public class AwardTechingActivity extends ActionBarActivity {

    private RecyclerView recyclerView = null;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_award_teching_layout);

        this.recyclerView = (RecyclerView) this.findViewById(R.id.my_recycler_view);

    }
}
