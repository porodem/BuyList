package com.porodem.buylist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.porodem.buylist.cheque.ChequeListActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    private static final String TAG = "ItemFragment";
    private static final String DIALOG_ADD = "DialogAdd";
    private static final String DIALOG_CLEAR = "DialogClear";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_FRAGMENT_TAG = "item_fragment";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private ProductLab mProductLab;
    public List<Product> mProducts;

    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter mAdapter;
    private static final int REQUEST_CREATE = 0;
    private static final int REQUEST_CLEAR = 1;
    public static final int REQUEST_EDIT = 2;

    private boolean mClearItemVisible;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putSerializable(ARG_FRAGMENT_TAG, "itemfragment");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mProductLab = ProductLab.get(getActivity());
        mProducts = mProductLab.getProducts();


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        View recyclerV = view.findViewById(R.id.list);


        FloatingActionButton fab = view.findViewById(R.id.fabNewItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                AddProductsDialog dialog = AddProductsDialog.newInstance();
                dialog.setTargetFragment(ItemFragment.this, REQUEST_CREATE);
                dialog.show(manager, DIALOG_ADD);
            }
        });

        // Set the adapter
        if (recyclerV instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) recyclerV;
            if (mColumnCount <= 1) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                //linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            updateUI(false);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Product item, String fragmentTag);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_item_list, menu);

        MenuItem clearItem = menu.findItem(R.id.clear_list);
        if (mClearItemVisible) {
            clearItem.setVisible(true);
        } else {
            clearItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_product:
                FragmentManager manager = getFragmentManager();
                AddProductsDialog dialog = AddProductsDialog.newInstance();
                dialog.setTargetFragment(ItemFragment.this, REQUEST_CREATE);
                dialog.show(manager, DIALOG_ADD);
                return true;
            case R.id.clear_list:
                ClearDialog cd = new ClearDialog();
                cd.setTargetFragment(ItemFragment.this, REQUEST_CLEAR);
                cd.show(getFragmentManager(), DIALOG_CLEAR);
            case R.id.cheque:
                Intent i = new Intent(getActivity(), ChequeListActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CREATE) {
            String list = data.getStringExtra(AddProductsDialog.EXTRA_DATE);
            addNewItems(list);
            updateUI(false);
        }
        if (requestCode == REQUEST_CLEAR) {
            mProductLab.delAllProducts();
            updateUI(false);
        }
        if (requestCode == REQUEST_EDIT) {
            Log.d(TAG, "onActivityResult - Request EDIT");
            updateUI(false);
        }
    }

    public void addTouchHelper(RecyclerView recyclerView){
        //add delete on swipe item
        final ItemTouchHelper.SimpleCallback helper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Product product = mProducts.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                ProductLab productLab = ProductLab.get(getActivity());
                productLab.delProduct(product);
                Toast.makeText(getActivity(), R.string.item_deleted, Toast.LENGTH_SHORT).show();
                mAdapter.notifyItemRemoved(position);
                updateUI(true);
            }
        };

        new ItemTouchHelper(helper).attachToRecyclerView(recyclerView);
    }

    /*create new instances of Product from String that we input in Dialog. And input them to
    * database */
    public void addNewItems(String list) {
        String itemName = "";
        char ch;
        boolean isFirstSpace = false;

         for (int i = 0; i<list.length(); i++) start: {
            ch = list.charAt(i);
            if (ch != ',' & ch != '\n') {
                //don't get first space in item
                if (ch == ' ' & isFirstSpace) {
                    isFirstSpace = false;
                    break start;
                }
                itemName += ch;
            } else {
                Product product = new Product();
                product.setName(itemName);
                mProductLab.addProduct(product);
                itemName = "";
                isFirstSpace = !isFirstSpace;
            }
        }

        Product product = new Product();
        product.setName(itemName);
        mProductLab.addProduct(product); // lol
    }


    private void updateUI(boolean isSingleItemUpdated){
        mProducts = mProductLab.getProducts();
        //Sort products by type before put it to RecyclerView
        Collections.sort(mProducts, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getType().compareTo(p2.getType());
            }
        });
        if (mProducts.size()>1)  {
            mClearItemVisible = true;
        } else {
            mClearItemVisible = false;
        }
        //renew menu
        getActivity().invalidateOptionsMenu();

        if (mAdapter==null) {
            mAdapter = new MyItemRecyclerViewAdapter(mProducts, mListener, getContext());
            mAdapter.setProducts(mProducts);
            recyclerView.setAdapter(mAdapter);
            addTouchHelper(recyclerView);
        } else {
            mAdapter.setProducts(mProducts);
            if (!isSingleItemUpdated) {
                mAdapter.notifyDataSetChanged();
            }

        }
    }
}
