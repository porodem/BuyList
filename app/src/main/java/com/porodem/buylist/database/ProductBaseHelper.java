package com.porodem.buylist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.porodem.buylist.cheque.Cheque;
import com.porodem.buylist.database.ProductDbSchema.ProductTable;

import static com.porodem.buylist.database.ProductDbSchema.*;

public class ProductBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "productBase.db";

    public ProductBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ProductTable.NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    ProductTable.Cols.UUID + ", " +
                    ProductTable.Cols.TITLE + ", " +
                    ProductTable.Cols.TYPE +
                    ")"
        );
        db.execSQL("create table " + ChequeTable.NAME + "(" +
                    "_id integer primary key autoincrement, " +
                    ChequeTable.Cols.DATE + ", " +
                    ChequeTable.Cols.MONTH + ", " +
                    ChequeTable.Cols.YEAR + ", " +
                    ChequeTable.Cols.LABEL + ", " +
                    ChequeTable.Cols.SUM +
                    ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
