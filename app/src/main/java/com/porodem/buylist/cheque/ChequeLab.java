package com.porodem.buylist.cheque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.porodem.buylist.database.ProductBaseHelper;
import com.porodem.buylist.database.ProductCursorWrapper;
import com.porodem.buylist.database.ProductDbSchema;

import java.util.ArrayList;
import java.util.List;

public class ChequeLab {

    private static String TAG = "ChequeLab";

    private static ChequeLab sChequeLab;
    private int mChequeSum;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Cheque> mCheques = new ArrayList<>();

    public static ChequeLab get(Context context) {
        if (sChequeLab == null) {
            sChequeLab = new ChequeLab(context);
        }
        return sChequeLab;
    }

    public void increaseChequeSum(int chequeSum) {
        mChequeSum += chequeSum;
    }

    private ChequeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ProductBaseHelper(context).getWritableDatabase();
    }

    public void addCheque(Cheque ch){
        increaseChequeSum(ch.getSum());
        ContentValues values = getContentValues(ch);
        mDatabase.insert(ProductDbSchema.ChequeTable.NAME, null, values);
    }

    public List<Cheque> getCheques(){
        List<Cheque> cheques = new ArrayList<>();

        ProductCursorWrapper cursor = queryCheques(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cheques.add(cursor.getCheque());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cheques;
    }

    public List<Cheque> getChequesMonth(String month) {
        List<Cheque> cheques = new ArrayList<>();
        String[] args = {month};
        ProductCursorWrapper cursor = queryCheques(ProductDbSchema.ChequeTable.Cols.MONTH + " = ?", args);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cheques.add(cursor.getCheque());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return cheques;
    }

    public void delAllProducts() {
        mChequeSum = 0;
        mDatabase.delete(ProductDbSchema.ChequeTable.NAME, null, null);
    }

    private static ContentValues getContentValues(Cheque cheque) {
        ContentValues values = new ContentValues();
        values.put(ProductDbSchema.ChequeTable.Cols.DATE, cheque.getDate()[Cheque.TIME_AND_DAY]);
        values.put(ProductDbSchema.ChequeTable.Cols.MONTH, cheque.getDate()[Cheque.MONTH]);
        values.put(ProductDbSchema.ChequeTable.Cols.YEAR, cheque.getDate()[Cheque.YEAR]);
        values.put(ProductDbSchema.ChequeTable.Cols.LABEL, cheque.getLable());
        values.put(ProductDbSchema.ChequeTable.Cols.SUM, cheque.getSum());
        Log.d(TAG, "cv type: " + cheque.getDate()[0] + " " + cheque.getLable() + " =" + cheque.getSum());
        return values;
    }
    private ProductCursorWrapper queryCheques(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ProductDbSchema.ChequeTable.NAME,
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
