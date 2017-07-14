package com.bogdanorzea.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.bogdanorzea.inventoryapp.data.InventoryContract.InventoryEntry;

public class ProductEditorActivity extends AppCompatActivity {
    private Uri mCurrentUri;

    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText quantityEditText;
    private EditText priceEditText;

    // Loader to fill the EditText fields
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // Projection for Cursor
            String[] projection = new String[]{
                    InventoryEntry.COLUMN_PRODUCT_NAME,
                    InventoryEntry.COLUMN_DESCRIPTION,
                    InventoryEntry.COLUMN_QUANTITY,
                    InventoryEntry.COLUMN_PRICE,
            };

            // Return a Cursor for the data to be displayed
            return new CursorLoader(getBaseContext(), mCurrentUri, projection, null, null, null);
        }

        @Override
        public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
            // Get column indexes
            int nameColumnIndex = data.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int descriptionColumnIndex = data.getColumnIndex(InventoryEntry.COLUMN_DESCRIPTION);
            int quantityColumnIndex = data.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
            int priceColumnIndex = data.getColumnIndex(InventoryEntry.COLUMN_PRICE);

            if (data.moveToFirst()) {
                // Get data from cursor
                String name = data.getString(nameColumnIndex);
                String description = data.getString(descriptionColumnIndex);
                int quantity = data.getInt(quantityColumnIndex);
                double price = data.getDouble(priceColumnIndex);

                // Set data to the corresponding EditText
                nameEditText.setText(name);
                descriptionEditText.setText(description);
                quantityEditText.setText(Integer.toString(quantity));
                priceEditText.setText(Double.toString(price));
            }
        }

        @Override
        public void onLoaderReset(android.content.Loader<Cursor> loader) {
            // Clear the EditText inputs
            nameEditText.setText("");
            descriptionEditText.setText("");
            quantityEditText.setText("");
            priceEditText.setText("");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);

        mCurrentUri = getIntent().getData();

        nameEditText = (EditText) findViewById(R.id.edit_name);
        descriptionEditText = (EditText) findViewById(R.id.edit_description);
        quantityEditText = (EditText) findViewById(R.id.edit_quantity);
        priceEditText = (EditText) findViewById(R.id.edit_price);

        if (mCurrentUri != null) {
            setTitle(getString(R.string.editor_title_edit));
            getLoaderManager().initLoader(0, null, mLoaderCallbacks);
        } else {
            setTitle(getString(R.string.editor_title_add));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // When inserting a new product, hide the "Delete" menu item.
        if (mCurrentUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitActivityWithAnimation();
    }

    /**
     * Exits the EditorActivity with a sliding animation
     */
    private void exitActivityWithAnimation() {
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert:
                insertProduct();
                exitActivityWithAnimation();
                return true;
            case R.id.action_delete:
                askDeleteProduct();
                return true;
            case android.R.id.home:
                exitActivityWithAnimation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void askDeleteProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User confirmed product deletion
                if (dialog != null) {
                    deleteProduct();
                    finish();
                }
            }
        });
        builder.setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // The user dismissed the dialog
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProduct() {
        int deletedRows = getContentResolver().delete(mCurrentUri, null, null);

        if (deletedRows == 0) {
            Toast.makeText(this, R.string.editor_delete_failed, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.editor_delete_successful, Toast.LENGTH_SHORT).show();
        }
    }

    private void insertProduct() {
        // Get the strings from EditTexts
        String nameString = nameEditText.getText().toString().trim();
        String descriptionString = descriptionEditText.getText().toString().trim();
        String quantityString = quantityEditText.getText().toString().trim();
        String priceString = priceEditText.getText().toString().trim();

        // Prevent adding products that do not have a valid name, quantity or price
        if (TextUtils.isEmpty(nameString) || TextUtils.isEmpty(quantityString) || TextUtils.isEmpty(priceString)) {
            Toast.makeText(this, R.string.editor_insert_incomplete, Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert Strings to the corresponding data types for price and quantity
        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(quantityString);
        }
        double price = 0.0;
        if (!TextUtils.isEmpty(priceString)) {
            price = Double.parseDouble(priceString);
        }

        // Map the values to the corresponding columns
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(InventoryEntry.COLUMN_DESCRIPTION, descriptionString);
        values.put(InventoryEntry.COLUMN_QUANTITY, quantity);
        values.put(InventoryEntry.COLUMN_PRICE, price);

        if (mCurrentUri == null) {
            // Insert the new row, returning the URI of the new row
            Uri newRowUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);

            // Inform the user about the insertion status
            if (newRowUri != null) {
                Toast.makeText(this, R.string.editor_insert_successfully, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.editor_insert_error, Toast.LENGTH_SHORT).show();
            }
        } else {
            // Update the current product based on the URI
            int updatedRows = getContentResolver().update(mCurrentUri, values, null, null);

            // Inform the user about the update status
            if (updatedRows > 0) {
                Toast.makeText(this, R.string.editor_update_successfully, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.editor_update_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
