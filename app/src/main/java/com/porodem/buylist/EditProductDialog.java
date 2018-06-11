package com.porodem.buylist;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.UUID;

public class EditProductDialog extends DialogFragment {

    private static final String TAG = "EditProductDialog";

    private static final String ARG_TITLE = "com.porodem.buylist.title";
    private static final String ARG_TYPE = "com.porodem.buylist.type";
    private static final String ARG_ID = "com.porodem.buylist.id";

    private Spinner mSpinner;
    private EditText mEditTitle;


    public static EditProductDialog newInstance(Product p) {
        EditProductDialog eDialog = new EditProductDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID, p.getUUID());
        args.putString(ARG_TITLE, p.getName());
        args.putString(ARG_TYPE, p.getType());
        eDialog.setArguments(args);
        return  eDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String title = bundle.getString(ARG_TITLE);
        final UUID pId = (UUID) bundle.getSerializable(ARG_ID);
        final String id = pId.toString();
        String productType = bundle.getString(ARG_TYPE);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_edit, null, false);

        mEditTitle = v.findViewById(R.id.editText);
        mEditTitle.setText(title);

        mSpinner = v.findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.types, android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        if (productType != null) {
            int spinnerPosition = adapter.getPosition(productType);
            mSpinner.setSelection(spinnerPosition);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.edit_item)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<String,String> hashMap = new HashMap<>();
                        String newTitle = mEditTitle.getText().toString();
                        ProductLab productLab = ProductLab.get(getActivity());
                        //Product product = productLab.getProduct(pId);
                        hashMap.put("id", id);
                        hashMap.put("title", newTitle);
                        hashMap.put("type", mSpinner.getSelectedItem().toString());
                        productLab.editProduct(hashMap);
                        getActivity().setResult(Activity.RESULT_OK);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditProductDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            Log.d(TAG, "sendResult");
            return;
        }
        Intent intent = new Intent();

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
