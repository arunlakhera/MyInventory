package com.pikchillytechnologies.myinventory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyProductsDBHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "MyInventory.db";
    private static final int DATABASE_VERSION = 1;

    public MyProductsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create Table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_TRACKING_DIARY = "CREATE TABLE " + Contract.ProductEntry.TABLE_NAME +
                "(" + Contract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                Contract.ProductEntry.COLUMN_PRODUCT_CATEGORY + " TEXT NOT NULL," +
                Contract.ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL," +
                Contract.ProductEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL," +
                Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " TEXT NOT NULL," +
                Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER + " TEXT);";
        Log.v("MyProductsDBHelper", "create table: " + CREATE_TABLE_TRACKING_DIARY);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRACKING_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.ProductEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    // Insert Products in Database
    public void insertProduct(String productName, String productCategory, Integer quantity, Integer productPrice, String supplierName, String supplierPhoneNumber) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.ProductEntry.COLUMN_PRODUCT_NAME, productName);
        values.put(Contract.ProductEntry.COLUMN_PRODUCT_CATEGORY, productCategory);
        values.put(Contract.ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(Contract.ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
        values.put(Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        values.put(Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);
        db.insert(Contract.ProductEntry.TABLE_NAME, null, values);
    }

    // Read Products from database
    public Cursor readProducts() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                Contract.ProductEntry._ID,
                Contract.ProductEntry.COLUMN_PRODUCT_NAME,
                Contract.ProductEntry.COLUMN_PRODUCT_CATEGORY,
                Contract.ProductEntry.COLUMN_PRODUCT_QUANTITY,
                Contract.ProductEntry.COLUMN_PRODUCT_PRICE,
                Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER
        };

        Cursor cursor = db.query(
                Contract.ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    // Read Selected Product Detail
    public Cursor readSelectedProduct(Integer productID){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                Contract.ProductEntry._ID,
                Contract.ProductEntry.COLUMN_PRODUCT_NAME,
                Contract.ProductEntry.COLUMN_PRODUCT_CATEGORY,
                Contract.ProductEntry.COLUMN_PRODUCT_QUANTITY,
                Contract.ProductEntry.COLUMN_PRODUCT_PRICE,
                Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER
        };

        String selection = "_ID=?";
        String selectionArgs[] = {String.valueOf(productID)};

        Cursor cursor = db.query(Contract.ProductEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
        return cursor;
    }

    // Update the quantity of Product in database
    public void update(Integer productID, Integer newQuantity) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.ProductEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);

        String whereClause = "_ID=?";
        String whereArgs[] = {String.valueOf(productID)};

        db.update(Contract.ProductEntry.TABLE_NAME, values, whereClause, whereArgs);

    }

    // Delete the Product from database
    public void deleteProduct(Integer productID) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = "_ID=?";
        String whereArgs[] = {String.valueOf(productID)};
        db.delete(Contract.ProductEntry.TABLE_NAME, whereClause, whereArgs);

    }

}