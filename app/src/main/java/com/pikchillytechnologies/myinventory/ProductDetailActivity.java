package com.pikchillytechnologies.myinventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pikchillytechnologies.myinventory.data.Contract;
import com.pikchillytechnologies.myinventory.data.MyProductsDBHelper;

public class ProductDetailActivity extends AppCompatActivity {

    String productName;
    String productCategory;
    String productQuantity;
    String productPrice;
    String productSupplierName;
    String productSupplierPhone;
    int newOrderQuantity;
    private ImageButton mBackButton;
    private HelperFile mHelperFile;
    private TextView mScreenTitle;
    private Bundle mProductBundle;
    private Integer mProductId;
    private MyProductsDBHelper productsDbHelper;
    private Cursor cursor;
    private TextInputEditText mProductName_EditText;
    private TextInputEditText mProductCategory_EditText;
    private TextInputEditText mProductQuantity_EditText;
    private TextView mNewOrderQuantity_TextView;
    private TextInputEditText mProductPrice_EditText;
    private TextInputEditText mProductSupplierName_EditText;
    private TextInputEditText mProductSupplierPhoneNumber_EditText;
    private Button mAddButton;
    private Button mReduceButton;
    private Button mOrder;
    private Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mProductBundle = getIntent().getExtras();
        newOrderQuantity = 0;

        productsDbHelper = new MyProductsDBHelper(this);

        loadData();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHelperFile.screenIntent(ProductDetailActivity.this, MyInventoryActivity.class);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteConfirmationDialog();
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newOrderQuantity = newOrderQuantity + 1;
                mNewOrderQuantity_TextView.setText(String.valueOf(newOrderQuantity));
            }
        });

        mReduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newOrderQuantity > 0) {
                    newOrderQuantity = newOrderQuantity - 1;
                    mNewOrderQuantity_TextView.setText(String.valueOf(newOrderQuantity));
                }
            }
        });

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newOrderQuantity > 0) {
                    placeOrder();

                    sendOrderDetails();

                    newOrderQuantity = 0;
                    mNewOrderQuantity_TextView.setText(String.valueOf(newOrderQuantity));
                } else {

                    mHelperFile.screenToast(getApplicationContext(), R.string.order_error_msg, Toast.LENGTH_LONG);
                }
            }
        });

    }

    public void loadData() {

        if (mProductBundle != null) {
            mProductId = Integer.valueOf(mProductBundle.getString("ProductId"));
            cursor = productsDbHelper.readSelectedProduct(mProductId);

        } else {
            mHelperFile.screenToast(this, R.string.product_error, Toast.LENGTH_LONG);
        }

        initViews();

        mScreenTitle.setText(getResources().getString(R.string.screen_prod_detail));

        while (cursor.moveToNext()) {
            setValues();
        }
    }

    /**
     * Function to perform when Order button is clicked
     */
    public void placeOrder() {

        int totalOrder = Integer.valueOf(productQuantity) + newOrderQuantity;

        productsDbHelper.update(mProductId, totalOrder);
        loadData();
    }

    /**
     * Function to send order details to supplier when Order button is clicked
     */
    public void sendOrderDetails() {

        String orderSummary;
        orderSummary = "\n Product Name:" + productName;
        orderSummary += "\n Quantity :" + newOrderQuantity;

        // Send the order in Email
        Intent sendOrderInEmailIntent = new Intent(Intent.ACTION_SENDTO);
        sendOrderInEmailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        sendOrderInEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Receipt");
        sendOrderInEmailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (sendOrderInEmailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendOrderInEmailIntent);
        }

    }

    /**
     * Function to initialize views
     */
    public void initViews() {

        mProductName_EditText = findViewById(R.id.editText_ProductName);
        mProductCategory_EditText = findViewById(R.id.editText_ProductCategory);
        mProductQuantity_EditText = findViewById(R.id.editText_ProductQuantity);
        mNewOrderQuantity_TextView = findViewById(R.id.textView_NewOrderQuantity);
        mProductPrice_EditText = findViewById(R.id.editText_ProductPrice);
        mProductSupplierName_EditText = findViewById(R.id.editText_SupplierName);
        mProductSupplierPhoneNumber_EditText = findViewById(R.id.editText_SupplierPhoneNumber);
        mDeleteButton = findViewById(R.id.button_Delete);

        mAddButton = findViewById(R.id.button_Add);
        mReduceButton = findViewById(R.id.button_Reduce);
        mOrder = findViewById(R.id.button_Order);
        mBackButton = findViewById(R.id.button_Back);
        mScreenTitle = findViewById(R.id.textView_ScreenTitle);
        mHelperFile = new HelperFile();

    }

    /**
     * Function to set values
     */
    public void setValues() {

        int columnIdIndex = cursor.getColumnIndex(Contract.ProductEntry._ID);
        int productNameColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_NAME);
        int productCategoryColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_CATEGORY);
        int productQuantityColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int productPriceColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_PRICE);
        int productSupplierNameColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
        int productSupplierPhoneColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER);

        productName = cursor.getString(productNameColumnIndex);
        productCategory = cursor.getString(productCategoryColumnIndex);
        productQuantity = cursor.getString(productQuantityColumnIndex);
        productPrice = cursor.getString(productPriceColumnIndex);
        productSupplierName = cursor.getString(productSupplierNameColumnIndex);
        productSupplierPhone = cursor.getString(productSupplierPhoneColumnIndex);

        mProductName_EditText.setText(productName);
        mProductCategory_EditText.setText(productCategory);
        mProductQuantity_EditText.setText(productQuantity);
        mNewOrderQuantity_TextView.setText(String.valueOf(newOrderQuantity));
        mProductPrice_EditText.setText(productPrice);
        mProductSupplierName_EditText.setText(productSupplierName);
        mProductSupplierPhoneNumber_EditText.setText(productSupplierPhone);
    }

    /**
     * Function to show Delete Dialog box
     */
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_msg);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Call Message to delete
                productsDbHelper.deleteProduct(mProductId);
                mHelperFile.screenToast(ProductDetailActivity.this, R.string.prod_deleted, Toast.LENGTH_LONG);
                mHelperFile.screenIntent(ProductDetailActivity.this, MyInventoryActivity.class);
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
