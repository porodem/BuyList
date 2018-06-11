package com.porodem.buylist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddProductsDialog extends DialogFragment {

    public static final String EXTRA_DATE = "com.porodem.buylist.products_list";

    public static final String ARG_PRODUCTS = "products";

    private EditText mNewProducts;

    public static AddProductsDialog newInstance(){
        /*Bundle args = new Bundle();
        args.putString(ARG_PRODUCTS, products);*/
        AddProductsDialog fragment = new AddProductsDialog();
        //fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Get the layoutinflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_item, null, false);
        mNewProducts = v.findViewById(R.id.new_products);

        builder.setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String list = mNewProducts.getText().toString();
                        sendResult(Activity.RESULT_OK, list);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddProductsDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultCode, String list){
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, list);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
