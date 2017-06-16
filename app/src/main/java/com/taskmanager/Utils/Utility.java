package com.taskmanager.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pavan Nagaraju on 16-Jun-17.
 */

public class Utility {
    public static boolean isEmptyString(View view){
        if(view instanceof EditText){
            EditText editText = (EditText) view;
            if(editText.getText().toString().trim().equals("")){
                return true;
            }
        }
        else if(view instanceof TextView) {
            TextView textView = (TextView) view;
            if(textView.getText().toString().trim().equals("")){
                return true;
            }
        }
        return false;
    }

    public static String getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return  dateFormat.format(date);
    }

    public static void hideKeyboard(Context ctx) {
        if (ctx != null && ctx.getSystemService(Activity.INPUT_METHOD_SERVICE) != null) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) ctx
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(((Activity) ctx).getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {

            }
        }
    }
}
