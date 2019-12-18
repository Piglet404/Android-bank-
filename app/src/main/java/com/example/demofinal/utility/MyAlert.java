package com.example.demofinal.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.demofinal.R;


public class MyAlert {
    private Context context;

    //Create constructor
    public MyAlert(Context context) {
        this.context = context;
    }

    public void normalDialog(String titleString, String messageString) {
        //can be reuse
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_alert);
        builder.setTitle(titleString);
        builder.setMessage(messageString);
        //Button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            // Ctrl + space after new key word.
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}