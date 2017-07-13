package com.bogdanorzea.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bogdanorzea.inventoryapp.data.InventoryContract;

public class MainActivity extends AppCompatActivity {
    private ProductCursorAdaptor mProductCursorAdaptor;

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection = {
                    InventoryContract.InventoryEntry._ID,
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                    InventoryContract.InventoryEntry.COLUMN_PRICE,
                    InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                    InventoryContract.InventoryEntry.COLUMN_DESCRIPTION
            };

            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(getBaseContext(), InventoryContract.InventoryEntry.CONTENT_URI, projection, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mProductCursorAdaptor.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mProductCursorAdaptor.swapCursor(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                insertDummyProduct();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Set adaptor for the product list view
        mProductCursorAdaptor = new ProductCursorAdaptor(this, null);

        ListView mProductListView = (ListView) findViewById(R.id.product_list);
        mProductListView.setAdapter(mProductCursorAdaptor);

        mProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(getBaseContext(), ProductEditorActivity.class);

                Uri data = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, id);
                editIntent.setData(data);

                startActivity(editIntent);
            }
        });

        // Prepare the loader.  Either re-connect with an existing one or start a new one.
        getLoaderManager().initLoader(0, null, mLoaderCallbacks);
    }

    private void insertDummyProduct() {
        // Maps values to columns
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, "Computer");
        values.put(InventoryContract.InventoryEntry.COLUMN_DESCRIPTION, "Laptop");
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, 2);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, 999.99);

        // Insert the new row, returning the URI of the new row
        Uri newRowUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);

        Log.i("CatalogActivity", "New row URI " + newRowUri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_add:
                insertDummyProduct();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
