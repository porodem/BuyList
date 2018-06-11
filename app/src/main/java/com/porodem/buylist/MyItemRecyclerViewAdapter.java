package com.porodem.buylist;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.porodem.buylist.ItemFragment.OnListFragmentInteractionListener;
import com.porodem.buylist.dummy.DummyContent.DummyItem;

import java.lang.reflect.Array;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

public class MyItemRecyclerViewAdapter  extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "MyItemRecycler";

    //private final List<DummyItem> mValues;
    private List<Product> mProducts;
    private final OnListFragmentInteractionListener mListener;
    private String fragmentTag;
    private Context mContext;

    public MyItemRecyclerViewAdapter(List<Product> items, OnListFragmentInteractionListener listener, Context context) {
        mProducts = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mProducts.get(position);
        holder.mContentView.setText(mProducts.get(position).getName());
        //holder.mTypeView.setText(mProducts.get(position).getType());
        Resources res = mContext.getResources();
        TypedArray icons = res.obtainTypedArray(R.array.products_icons);
        String type = mProducts.get(position).getType();
        int i ;
        switch (type) {
            case "рыба":
                i = 1;
                break;
            case "мясо":
                i = 0;
                break;
            case "крупы":
                i = 2;
                break;
            case "химия":
                i = 3;
                break;
            case "фрукты":
                i = 4;
                break;
            case "хоз":
                i = 5;
                break;
            case "приправы":
                i = 6;
                break;
            case "аптека":
                i = 7;
                break;
            default:
                i = 8;
                break;

        }
        if (icons.getDrawable(i) != null) {
            holder.mImageType.setImageDrawable(icons.getDrawable(i));
        }
        icons.recycle();


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, fragmentTag);
                }
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        Log.d(TAG, "onDetachedFromRW");
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mContentView;
        public TextView mTypeView;
        private ImageView mImageType;
        public Product mItem;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            //mTypeView = view.findViewById(R.id.img_type);
            mImageType = view.findViewById(R.id.img_type_icon);


        }

        public Product getItem() {
            return mItem;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }
    public void setFragmentTag(Fragment fragment){
        fragmentTag = fragment.getTag();
    }
}
