package com.porodem.buylist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.porodem.buylist.cheque.Cheque;
import com.porodem.buylist.Product;
import com.porodem.buylist.database.ProductDbSchema.ProductTable;

import java.util.Date;
import java.util.UUID;

import static com.porodem.buylist.database.ProductDbSchema.*;

public class ProductCursorWrapper extends CursorWrapper {
    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProduct() {
        String uuidString = getString(getColumnIndex(ProductTable.Cols.UUID));
        String title = getString(getColumnIndex(ProductTable.Cols.TITLE));
        String type = getString(getColumnIndex(ProductTable.Cols.TYPE));

        Product product = new Product(UUID.fromString(uuidString));
        product.setName(title);
        product.setType(type);

        return product;
    }

    public Cheque getCheque() {

        String data = getString(getColumnIndex(ChequeTable.Cols.DATE)); // Day and Time of day
        String month = getString(getColumnIndex(ChequeTable.Cols.MONTH));
        String year = getString(getColumnIndex(ChequeTable.Cols.YEAR));
        String sum = getString(getColumnIndex(ChequeTable.Cols.SUM));
        String label = getString(getColumnIndex(ChequeTable.Cols.LABEL));

        Cheque cheque = new Cheque(label);
        cheque.setDate(data, month, year);
        cheque.setSum(Integer.valueOf(sum));

        return cheque;
    }
}


