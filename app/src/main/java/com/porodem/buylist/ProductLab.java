package com.porodem.buylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.porodem.buylist.database.ProductBaseHelper;
import com.porodem.buylist.database.ProductCursorWrapper;
import com.porodem.buylist.database.ProductDbSchema;
import com.porodem.buylist.database.ProductDbSchema.ProductTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ProductLab {

    private static final String TAG = "ProductLab";

    private static ProductLab sProductLab;
    //private List<Product> mProducts;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ProductLab get(Context context) {
        if (sProductLab == null) {
            sProductLab = new ProductLab(context);
        }
        return sProductLab;
    }

    private ProductLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ProductBaseHelper(mContext)
                .getWritableDatabase();
        }

    public void addProduct(Product p) {
        ContentValues values = getContentValues(p);
        Log.d(TAG, "add product: " + p.getName());
        mDatabase.insert(ProductTable.NAME, null, values);
    }

    public void editProduct(HashMap<String, String > hashMap) {
        String stringId = hashMap.get("id");
        UUID id = UUID.fromString(stringId);
        String title = hashMap.get("title");
        String type = hashMap.get("type");
        Product p = new Product(id, title, type);
        String selection = ProductTable.Cols.UUID + " LIKE ?";
        String[] selectionArgs = {stringId};
        ContentValues values = getContentValues(p);
        mDatabase.update(ProductTable.NAME, values, selection, selectionArgs);
    }

    public void delProduct(Product p){
        String selection = ProductTable.Cols.TITLE + " LIKE ?";
        String[] selectionArgs = {p.getName().toString()};
        mDatabase.delete(ProductTable.NAME, selection, selectionArgs);
    }

    public void delAllProducts() {
        mDatabase.delete(ProductTable.NAME, null, null);
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();

        ProductCursorWrapper cursor = queryProducts(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                products.add(cursor.getProduct());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return products;
    }

    public Product getProduct(UUID id) {
        return null;
    }

    private static ContentValues getContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductTable.Cols.UUID, product.getUUID().toString());
        values.put(ProductTable.Cols.TITLE, product.getName());
        values.put(ProductTable.Cols.TYPE, product.getType());
        Log.d(TAG, "cv type: " + product.getType());
        return values;
    }

    private ProductCursorWrapper queryProducts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ProductTable.NAME,
                null, //sellect all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ProductCursorWrapper(cursor);
    }
}
