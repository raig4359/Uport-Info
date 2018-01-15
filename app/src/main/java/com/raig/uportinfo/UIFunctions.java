package com.raig.uportinfo;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Gaurav on 15-01-2018.
 */

public class UIFunctions {

    public void showMessage(View view, String message, int duration) {
        getSnackbar(view, message, duration).show();
    }

    public void showMessage(View view, int msgColor, String message, int duration) {
        Snackbar snackbar = getSnackbar(view, message, duration);
        snackbar.setActionTextColor(msgColor);
        snackbar.show();
    }

    public void showMessage(View view, int msgColor, int actionColor, String message,
                            int duration, View.OnClickListener listener) {
        Snackbar snackbar = getSnackbar(view, message, duration);
        snackbar.setActionTextColor(msgColor);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(actionColor);
        snackbar.show();
    }

    private Snackbar getSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        return snackbar;
    }

}
