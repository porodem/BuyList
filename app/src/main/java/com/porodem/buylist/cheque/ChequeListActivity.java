package com.porodem.buylist.cheque;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.porodem.buylist.SingleFragmentActivity;

public class ChequeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ChequeListFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
