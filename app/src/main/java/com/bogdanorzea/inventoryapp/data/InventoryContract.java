package com.bogdanorzea.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class InventoryContract {
    public static final String CONTENT_AUTHORITY = "com.bogdanorzea.inventoryapp";
    public static final String PATH_PRODUCTS = "products";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private InventoryContract() {
    }

    public static class InventoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        // MIME Type Reference
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS + "/#";

        // Table name
        public static final String TABLE_NAME = "products";

        // Column names
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SUPPLIER = "supplier";
        public static final String COLUMN_SUPPLIER_EMAIL = "supplier_email";

        // DELETE TABLE statement
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME;

        // Data types
        static final String TEXT_DATA_TYPE = " TEXT";
        static final String PRIMARY_KEY = " PRIMARY KEY";
        static final String INTEGER_DATA_TYPE = " INTEGER";
        static final String REAL_DATA_TYPE = " REAL";
        static final String BLOB_DATA_TYPE = " BLOB";
        static final String SEPARATOR = ",";
        static final String NOT_NULL = " NOT NULL";

        // CREATE TABLE statement
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + InventoryEntry.TABLE_NAME + " (" +
                        InventoryEntry._ID + INTEGER_DATA_TYPE + PRIMARY_KEY + SEPARATOR +
                        InventoryEntry.COLUMN_PRODUCT_NAME + TEXT_DATA_TYPE + NOT_NULL + SEPARATOR +
                        InventoryEntry.COLUMN_DESCRIPTION + TEXT_DATA_TYPE + SEPARATOR +
                        InventoryEntry.COLUMN_QUANTITY + INTEGER_DATA_TYPE + NOT_NULL + SEPARATOR +
                        InventoryEntry.COLUMN_PRICE + REAL_DATA_TYPE + NOT_NULL + SEPARATOR +
                        InventoryEntry.COLUMN_IMAGE + BLOB_DATA_TYPE + SEPARATOR +
                        InventoryEntry.COLUMN_SUPPLIER + TEXT_DATA_TYPE + NOT_NULL + SEPARATOR +
                        InventoryEntry.COLUMN_SUPPLIER_EMAIL + TEXT_DATA_TYPE + NOT_NULL +")";
    }

}
