package com.bogdanorzea.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    public void bindView(View view, final Context context, Cursor cursor) {
        // Get views to display information
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);

        // Get the cursor column names
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
        int descriptionColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_DESCRIPTION);

        // Add information to views
        nameTextView.setText(cursor.getString(nameColumnIndex));
        priceTextView.setText(Double.toString(cursor.getDouble(priceColumnIndex)));
        quantityTextView.setText(Integer.toString(cursor.getInt(quantityColumnIndex)));
        descriptionTextView.setText(cursor.getString(descriptionColumnIndex));

        // Add button action
        Button sellButton = (Button) view.findViewById(R.id.sell);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TODO Decrease quantity by one", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
