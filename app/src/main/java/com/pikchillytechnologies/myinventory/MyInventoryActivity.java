package com.pikchillytechnologies.myinventory;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pikchillytechnologies.myinventory.data.MyProductsDBHelper;

public class MyInventoryActivity extends AppCompatActivity {

    private MyProductsDBHelper productsDbHelper;
    private InventoryAdapter mCursorAdapter;
    private ImageButton mBackButton;

    @Override
    protected void onStart() {
        super.onStart();

        loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inventory);

        productsDbHelper = new MyProductsDBHelper(this);
        mBackButton = findViewById(R.id.button_Back);
        mBackButton.setVisibility(View.INVISIBLE);

        FloatingActionButton mAddProduct = findViewById(R.id.button_AddProduct);

        // Action to perform when Add button is clicked
        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyInventoryActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        // load data in the listview
        loadData();
    }

    /**
     * Function to load the data in listview
     */
    public void loadData() {

        ListView inventoryListView = findViewById(R.id.listView_Products);

        TextView emptyView = findViewById(R.id.empty_text_view);
        inventoryListView.setEmptyView(emptyView);

        Cursor readCursor = productsDbHelper.readProducts();
        mCursorAdapter = new InventoryAdapter(this, readCursor);
        inventoryListView.setAdapter(mCursorAdapter);
        mCursorAdapter.notifyDataSetChanged();

    }

    /**
     * Function to update product quantity after sale
     */
    public void productSale(int productID, int productQuantity) {

        productQuantity = productQuantity - 1;

        // Update the product quantity if it is available for sale
        if (productQuantity >= 0) {

            productsDbHelper.update(productID, productQuantity);

            // load data once the quantity is updated in database
            loadData();

        } else {
            Toast.makeText(this, getResources().getString(R.string.product_out_of_stock), Toast.LENGTH_SHORT).show();
        }
    }
}