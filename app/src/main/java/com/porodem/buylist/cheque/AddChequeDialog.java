package com.porodem.buylist.cheque;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.porodem.buylist.R;

import java.util.zip.Inflater;

public class AddChequeDialog extends DialogFragment {

    private static final String TAG = "AddChequeDialog";

    public static final String EXTRA_CHEQUE_SUM = "com.porodem.buylist.cheque.sum";
    public static final String EXTRA_CHEQUE_LABLE = "com.porodem.buylist.cheque.lable";

    private EditText mLable, mSum;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_cheq_item, null, false);
        mSum = v.findViewById(R.id.new_products);
        mLable = v.findViewById(R.id.etChequeLable);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.cheque_sum)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String s, l;
                        if (mSum.length()>0 && mLable.length()>0) {
                            s = mSum.getText().toString();
                            l = mLable.getText().toString();
                            addCheque(l, s, Activity.RESULT_OK);
                        } else {
                            Toast.makeText(getActivity(),"WTF", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .create();
    }

    private void addCheque(String lable, String sum, int resultCode) {
        Log.d(TAG, "cheque sum: " + sum);
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CHEQUE_SUM, sum);
        intent.putExtra(EXTRA_CHEQUE_LABLE, lable);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
