package com.porodem.buylist.cheque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.porodem.buylist.Product;
import com.porodem.buylist.R;

import java.util.ArrayList;
import java.util.List;

public class ChequeListFragment extends Fragment {

    public static final String TAG = "ChequeListFragment";

    public static final String DIALOG_CHEQUE = "DialogCheque";
    public static final String DIALOG_MONTHS = "dialog_months";

    public static final int REQUEST_CHEQUE = 0;
    public static final int REQUEST_MONTH = 1;

    Cheque mCheque;
    List<Cheque> mCheques;
    List<Product> mProducts = new ArrayList<>();
    Product mProduct;
    ChequeLab mChequeLab;

    RecyclerView mRecyclerCheque;
    ChequeAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mChequeLab = ChequeLab.get(getActivity());
        mCheques = mChequeLab.getCheques();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_cheques, container, false);
        mRecyclerCheque = v.findViewById(R.id.recycler_cheque);
        mRecyclerCheque.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class ChequeHolder extends RecyclerView.ViewHolder {

        public TextView mTvDate, mTvLable, mTvSum;

        public ChequeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cheque_item, parent, false));
            mTvDate = itemView.findViewById(R.id.cheque_date);
            mTvLable = itemView.findViewById(R.id.tvLable);
            mTvSum = itemView.findViewById(R.id.cheque_sum);
        }

        private void bind(Cheque cheque) {
            mCheque = cheque;
            mTvDate.setText(cheque.getDateString());
            mTvLable.setText(cheque.getLable());
            mTvSum.setText(String.valueOf(cheque.getSum()));
        }
    }

    private class ChequeAdapter extends RecyclerView.Adapter<ChequeHolder> {

        public ChequeAdapter(List<Cheque> cheques) {
            mCheques = cheques;
        }

        @Override
        public ChequeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ChequeHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(ChequeHolder holder, int position) {
            Cheque ch = mCheques.get(position);
            holder.bind(ch);
        }

        @Override
        public int getItemCount() {
            return mCheques.size();
        }
    }

    private void updateUI(){
        ChequeLab chequeLab = ChequeLab.get(getActivity());
        mCheques = chequeLab.getCheques();
        mAdapter = new ChequeAdapter(mCheques);
        mRecyclerCheque.setAdapter(mAdapter);

        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(String.valueOf(getChequesSum(mCheques)));
    }
    private int getChequesSum(List<Cheque> cheques) {
        int i = 0;
        for (Cheque ch: cheques) {
            i += ch.getSum();
        }
        return i;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_recycler_cheques, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        switch (item.getItemId()) {
            case R.id.new_cheque:
                AddChequeDialog dialog = new AddChequeDialog();
                dialog.setTargetFragment(ChequeListFragment.this, REQUEST_CHEQUE);
                dialog.show(fm, DIALOG_CHEQUE);
                /*Cheque ch = new Cheque();
                ch.setSum(1000);
                mChequeLab.addCheque(ch);*/
                return true;
            case R.id.monthly:
                PickMonthDialog monthDialog = new PickMonthDialog();
                monthDialog.setTargetFragment(ChequeListFragment.this, REQUEST_MONTH);
                monthDialog.show(fm, DIALOG_MONTHS);
                return true;
            case R.id.clear_list:
                mChequeLab.delAllProducts();
                return true;
                default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CHEQUE) {
            String chequeLable = data.getStringExtra(AddChequeDialog.EXTRA_CHEQUE_LABLE);
            String chequeSum = data.getStringExtra(AddChequeDialog.EXTRA_CHEQUE_SUM);
            Cheque cheque = new Cheque();
            cheque.setSum(Integer.valueOf(chequeSum));
            cheque.setLable(chequeLable);
            mChequeLab.addCheque(cheque);
            updateUI();
        }
        if (requestCode == REQUEST_MONTH) {
            String month = data.getStringExtra(PickMonthDialog.EXTRA_MONTH);
            Log.d(TAG, "result month :  - - - " + month);
            mCheques = mChequeLab.getChequesMonth(month);
            mAdapter = new ChequeAdapter(mCheques);
            mRecyclerCheque.setAdapter(mAdapter);
        }
        //super.onActivityResult(requestCode, resultCode, data);

    }


}
