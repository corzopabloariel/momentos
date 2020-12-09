package com.example.momentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LodingLogin {

    private Activity _activity;
    private AlertDialog _alertDialog;

    LodingLogin(Activity activity) {
        this._activity = activity;
    }

    void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this._activity);

        LayoutInflater inflater = this._activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(true);

        this._alertDialog = builder.create();
        this._alertDialog.show();
    }

    void dismissLoading() {
        this._alertDialog.dismiss();
    }
}
