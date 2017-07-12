package com.bogdanorzea.inventoryapp.Data;

import android.provider.BaseColumns;

public class InventoryContract {
    private InventoryContract() {
    }

    public static class InventoryEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "product";

        // Column names
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_PRICE = "price";

        // Data types
        static final String TEXT_DATA_TYPE = " TEXT";
        static final String PRIMARY_KEY = " PRIMARY KEY";
        static final String INTEGER_DATA_TYPE = " INTEGER";
        static final String REAL_DATA_TYPE = " REAL";
        static final String SEPARATOR = ",";
        static final String NOT_NULL = " NOT NULL";

        // CREATE TABLE statement
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + InventoryEntry.TABLE_NAME + " (" +
                        InventoryEntry._ID + INTEGER_DATA_TYPE + PRIMARY_KEY + SEPARATOR +
                        InventoryEntry.COLUMN_NAME_PRODUCT_NAME + TEXT_DATA_TYPE + NOT_NULL + SEPARATOR +
                        InventoryEntry.COLUMN_NAME_DESCRIPTION + TEXT_DATA_TYPE + SEPARATOR +
                        InventoryEntry.COLUMN_NAME_QUANTITY + INTEGER_DATA_TYPE + NOT_NULL + SEPARATOR +
                        InventoryEntry.COLUMN_NAME_PRICE + REAL_DATA_TYPE + NOT_NULL + ")";

        // DELETE TABLE statement
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME;
    }

}
