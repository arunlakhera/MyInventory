package com.pikchillytechnologies.myinventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pikchillytechnologies.myinventory.data.Contract;
import com.pikchillytechnologies.myinventory.data.MyProductsDBHelper;

import butterknife.BindView;

public class InventoryAdapter extends CursorAdapter {

    private TextView productNameTextView;
    private TextView productPriceTextView;
    private TextView productQuantityTextView;
    private HelperFile mHelperFile;
    private Button productSaleButton;
    private Button productDetailButton;
    private MyProductsDBHelper productsDbHelper;
    Context mContext;

    public InventoryAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.my_inventory_list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        productNameTextView = view.findViewById(R.id.textView_ProductName);
        productPriceTextView = view.findViewById(R.id.textView_ProductPrice);
        productQuantityTextView = view.findViewById(R.id.textView_ProductQuantity);
        productSaleButton = view.findViewById(R.id.button_Sale);
        productDetailButton = view.findViewById(R.id.button_Detail);
        mHelperFile = new HelperFile();

        int columnIdIndex = cursor.getColumnIndex(Contract.ProductEntry._ID);
        int productNameColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_NAME);
        int productPriceColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_PRICE);
        int productQuantityColumnIndex = cursor.getColumnIndex(Contract.ProductEntry.COLUMN_PRODUCT_QUANTITY);

        final String productID = cursor.getString(columnIdIndex);
        String productName = cursor.getString(productNameColumnIndex);
        String productPrice = view.getResources().getString(R.string.label_currency) + cursor.getString(productPriceColumnIndex);
        final String productQuantity = cursor.getString(productQuantityColumnIndex);

        setValues(productName,productPrice,productQuantity);

        productSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyInventoryActivity Activity = (MyInventoryActivity) context;
                Activity.productSale(Integer.valueOf(productID), Integer.valueOf(productQuantity));
            }
        });

        productDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperFile.screenIntent(mContext,ProductDetailActivity.class,productID);
            }
        });
    }

    public void setValues(String productName, String productPrice, String productQuantity){

        productNameTextView.setText(productName);
        productPriceTextView.setText(productPrice);
        productQuantityTextView.setText(productQuantity);
    }
}
