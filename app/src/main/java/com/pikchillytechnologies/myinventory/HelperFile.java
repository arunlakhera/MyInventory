package com.pikchillytechnologies.myinventory;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HelperFile {

    /**
     * Function for Intent to navigate user from one screen to another
     * */
    public void screenIntent(Context context, Class<?> c){

        context.startActivity(new Intent(context, c));
    }

    /**
     * Function for Intent to navigate user from one screen to another with phone number
     */
    public void screenIntent(Context context, Class<?> c,String phone){

        Intent nextIntent = new Intent(context, c);
        nextIntent.putExtra("ProductId",String.valueOf(phone));
        context.startActivity(nextIntent);
    }

    /**
     * Function for Toast Message
     * */
    public void screenToast(Context context,Integer msg,Integer duration){

        Toast.makeText(context,msg,duration).show();
    }
}
