package com.pikchillytechnologies.myinventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pikchillytechnologies.myinventory.data.Contract;
import com.pikchillytechnologies.myinventory.data.MyProductsDBHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class AddProductActivity extends AppCompatActivity {

    private MyProductsDBHelper productsDbHelper;
    private TextInputEditText mProductName_EditText;
    private TextInputEditText mProductCategory_EditText;
    private TextInputEditText mProductQuantity_EditText;
    private TextInputEditText mProductPrice_EditText;
    private TextInputEditText mProductSupplierName_EditText;
    private TextInputEditText mProductSupplierPhoneNumber_EditText;
    private Button mAddProduct_Button;
    private ImageButton mBackButton;

    private String mProductName;
    private String mProductCategory;
    private Integer mProductQuantity;
    private Integer mProductPrice;
    private String mProductSupplierName;
    private String mProductSupplierPhoneNumber;
    private Boolean mDataFlag = true;
    private Boolean recordExistFlag;
    private HelperFile mHelperFile;
    private TextView mScreenTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productsDbHelper = new MyProductsDBHelper(this);
        mHelperFile = new HelperFile();

        // Initialize views
        initViews();

        // Set Title
        mScreenTitle.setText(getResources().getString(R.string.screen_add_prod));

        mAddProduct_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = productsDbHelper.readProducts();
                recordExistFlag = false;

                // if all fields have been entered and product does not exist already
                if (checkProductData()) {

                    // Function to get product data entered by user
                    getProductData();

                    while (cursor.moveToNext()) {

                        String productName = cursor.getString(1);

                        if (productName.equals(mProductName)) {
                            recordExistFlag = true;
                            break;
                        }
                    }

                    if (recordExistFlag) {
                        Toast.makeText(AddProductActivity.this, getResources().getString(R.string.product_already_exist), Toast.LENGTH_LONG).show();

                    } else {
                        productsDbHelper.insertProduct(mProductName, mProductCategory, mProductQuantity, mProductPrice, mProductSupplierName, mProductSupplierPhoneNumber);
                        Toast.makeText(AddProductActivity.this, getResources().getString(R.string.product_added), Toast.LENGTH_LONG).show();
                        showAddedConfirmationDialog();
                    }
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHelperFile.screenIntent(AddProductActivity.this, MyInventoryActivity.class);

            }
        });
    }

    /**
     * Function to initialize views
     */
    public void initViews() {

        mProductName_EditText = findViewById(R.id.editText_ProductName);
        mProductCategory_EditText = findViewById(R.id.editText_ProductCategory);
        mProductQuantity_EditText = findViewById(R.id.editText_ProductQuantity);
        mProductPrice_EditText = findViewById(R.id.editText_ProductPrice);
        mProductSupplierName_EditText = findViewById(R.id.editText_SupplierName);
        mProductSupplierPhoneNumber_EditText = findViewById(R.id.editText_SupplierPhoneNumber);
        mAddProduct_Button = findViewById(R.id.button_Add);
        mBackButton = findViewById(R.id.button_Back);
        mScreenTitle = findViewById(R.id.textView_ScreenTitle);

    }

    /**
     * Function to get Product data
     */
    public void getProductData() {

        mProductName = String.valueOf(mProductName_EditText.getText());
        mProductCategory = String.valueOf(mProductCategory_EditText.getText());
        mProductQuantity = Integer.valueOf(String.valueOf(mProductQuantity_EditText.getText()));
        mProductPrice = Integer.valueOf(String.valueOf(mProductPrice_EditText.getText()));
        mProductSupplierName = String.valueOf(mProductSupplierName_EditText.getText());
        mProductSupplierPhoneNumber = String.valueOf(mProductSupplierPhoneNumber_EditText.getText());

    }

    /**
     * Function to check Product Data
     */
    public Boolean checkProductData() {

        if (String.valueOf(mProductName_EditText.getText()) == null || String.valueOf(mProductName_EditText.getText()).isEmpty()) {
            Toast.makeText(AddProductActivity.this, getResources().getString(R.string.product_name_error), Toast.LENGTH_LONG).show();
            mDataFlag = false;
        } else if (String.valueOf(mProductCategory_EditText.getText()) == null || String.valueOf(mProductCategory_EditText.getText()).isEmpty()) {
            Toast.makeText(AddProductActivity.this, getResources().getString(R.string.product_category_error), Toast.LENGTH_LONG).show();
            mDataFlag = false;
        } else if (String.valueOf(mProductQuantity_EditText.getText()) == null || String.valueOf(mProductQuantity_EditText.getText()).isEmpty()) {
            Toast.makeText(AddProductActivity.this, getResources().getString(R.string.product_quantity_error), Toast.LENGTH_LONG).show();
            mDataFlag = false;
        } else if (String.valueOf(mProductPrice_EditText.getText()) == null || String.valueOf(mProductPrice_EditText.getText()).isEmpty()) {
            Toast.makeText(AddProductActivity.this, getResources().getString(R.string.product_price_error), Toast.LENGTH_LONG).show();
            mDataFlag = false;
        } else if (String.valueOf(mProductSupplierName_EditText.getText()) == null || String.valueOf(mProductSupplierName_EditText.getText()).isEmpty()) {
            Toast.makeText(AddProductActivity.this, getResources().getString(R.string.product_supplier_name_error), Toast.LENGTH_LONG).show();
            mDataFlag = false;
        } else if (String.valueOf(mProductSupplierPhoneNumber_EditText.getText()) == null || String.valueOf(mProductSupplierPhoneNumber_EditText.getText()).isEmpty()) {
            Toast.makeText(AddProductActivity.this, getResources().getString(R.string.product_supplier_phone_error), Toast.LENGTH_LONG).show();
            mDataFlag = false;
        } else {
            mDataFlag = true;
        }

        return mDataFlag;
    }

    /**
     * Function to show Dialog box
     */
    private void showAddedConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.added_dialog_msg);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                clearViews();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                    mHelperFile.screenIntent(AddProductActivity.this, MyInventoryActivity.class);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Function to clear views
     */
    public void clearViews() {

        mProductName_EditText.setText("");
        mProductCategory_EditText.setText("");
        mProductQuantity_EditText.setText("");
        mProductPrice_EditText.setText("");
        mProductSupplierName_EditText.setText("");
        mProductSupplierPhoneNumber_EditText.setText("");

    }
}
