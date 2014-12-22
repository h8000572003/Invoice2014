package tw.com.wa.invoice.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import tw.com.wa.invoice.R;

/**
 * Created by Andy on 2014/12/19.
 */
public class DialogBuilder extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.message_layout, null));
        builder.setMessage(R.string.know)
                .setPositiveButton(R.string.know, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogBuilder.this.getDialog().cancel();
                    }
                });
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        DialogBuilder.this.getDialog().cancel();
//                    }
//                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

