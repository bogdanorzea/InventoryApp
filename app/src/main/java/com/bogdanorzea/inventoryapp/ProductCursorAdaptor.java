package com.bogdanorzea.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bogdanorzea.inventoryapp.data.InventoryContract.InventoryEntry;

class ProductCursorAdaptor extends CursorAdapter {
    private final LayoutInflater inflater;

    public ProductCursorAdaptor(Context context, Cursor c) {
        super(context, c, 0);
        inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.product_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get views to display information
        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView descriptionView = (TextView) view.findViewById(R.id.description);

        // Get the cursor column names
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int descriptionColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_DESCRIPTION);

        // Add information to views
        nameView.setText(cursor.getString(nameColumnIndex));
        descriptionView.setText(cursor.getString(descriptionColumnIndex));
    }
}
