package com.pikchillytechnologies.myinventory.data;

import android.provider.BaseColumns;

public class Contract {

    public Contract() {
    }

    public class ProductEntry implements BaseColumns {

        // Table Name
        public final static String TABLE_NAME = "my_products";

        // Table Columns
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "name";
        public final static String COLUMN_PRODUCT_CATEGORY = "category";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

        public final static String PRODUCT_CATEGORY_PHONE = "Phone";
        public final static String PRODUCT_CATEGORY_TABLET = "Tablet";
        public final static String PRODUCT_CATEGORY_LAPTOP = "Laptop";

        public final static String PRODUCT_SUPPLIER_APPLE = "Apple";
        public final static String PRODUCT_SUPPLIER_ANDROID = "Google";
        public final static String PRODUCT_SUPPLIER_LAPTOP = "Microsoft";

    }
}