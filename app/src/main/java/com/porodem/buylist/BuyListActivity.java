package com.porodem.buylist;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.porodem.buylist.dummy.DummyContent;

public class BuyListActivity extends SingleFragmentActivity

    implements ItemFragment.OnListFragmentInteractionListener{

    private static final String TAG = "BuyListActivity";

    @Override
    protected Fragment createFragment() {
        return ItemFragment.newInstance(1);
    }

    @Override
    public void onListFragmentInteraction(Product item, String tag) {

        ItemFragment itemFragment = (ItemFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        FragmentManager fm = getSupportFragmentManager();
        EditProductDialog editDialog = EditProductDialog.newInstance(item);
        editDialog.setTargetFragment(itemFragment, ItemFragment.REQUEST_EDIT);
        editDialog.show(fm, "tag");
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_list);
    }*/
}
