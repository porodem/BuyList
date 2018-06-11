package com.porodem.buylist.cheque;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.porodem.buylist.R;

import java.util.zip.Inflater;

public class PickMonthDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "PickMonthDialog";

    public static final String EXTRA_MONTH = "com.porodem.buylist.cheque.month";

    private Button mJan, mFeb, mMar, mApr, mMay, mJun, mJul, mAug, mSep, mOct, mNov, mDec;
    private Button mButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_pickmonth,null, false);
        mJan = v.findViewById(R.id.jan);
        mJan.setOnClickListener(this);
        mFeb = v.findViewById(R.id.feb);
        mFeb.setOnClickListener(this);
        mMar = v.findViewById(R.id.mar);
        mMar.setOnClickListener(this);
        mApr = v.findViewById(R.id.apr);
        mApr.setOnClickListener(this);
        mMay = v.findViewById(R.id.may);
        mMay.setOnClickListener(this);
        mJun = v.findViewById(R.id.jun);
        mJun.setOnClickListener(this);
        mJul = v.findViewById(R.id.jul);
        mJul.setOnClickListener(this);
        mAug = v.findViewById(R.id.aug);
        mAug.setOnClickListener(this);
        mSep = v.findViewById(R.id.sep);
        mSep.setOnClickListener(this);
        mOct = v.findViewById(R.id.oct);
        mOct.setOnClickListener(this);
        mNov = v.findViewById(R.id.nov);
        mNov.setOnClickListener(this);
        mDec = v.findViewById(R.id.dec);
        mDec.setOnClickListener(this);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.monthly_cheques)
                .create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jan:
                Log.d(TAG, "clicked: Jan");
                setMonth("янв", Activity.RESULT_OK);
                break;
            case R.id.feb:
                Log.d(TAG, "clicked Feb");
                PickMonthDialog.this.getDialog().cancel();
                break;
            case R.id.mar:
                break;
            case R.id.apr:
                break;
            case R.id.may:
                break;
            case R.id.jun:
                Log.d(TAG, "clicked: Jun");
                setMonth("июня", Activity.RESULT_OK);
                break;
            case R.id.jul:
                break;
            case R.id.aug:
                break;
            case R.id.sep:
                break;
            case R.id.oct:
                break;
            case R.id.nov:
                break;
            case R.id.dec:
                break;
            default:
                    break;
        }
    }

    private void setMonth(String month, int resultCode){
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MONTH, month);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        PickMonthDialog.this.getDialog().cancel();
    }
}
