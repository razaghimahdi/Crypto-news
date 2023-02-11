package com.apadanah.crypto_news.presentation.util.toast;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.apadanah.crypto_news.R;
import com.google.android.material.snackbar.Snackbar;

public class MyToastSnackBar {


    public static void simpleToastFloating(Activity context, String message) {
        final Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);

        //inflate view
        View view = context.getLayoutInflater().inflate(R.layout.simple_snackbar_toast_floating, null);

        final TextView txt_simple_snackbar_toast_floating = view.findViewById(R.id.txt_simple_snackbar_toast_floating);
        txt_simple_snackbar_toast_floating.setText(message);
        toast.setView(view);
        toast.show();
    }

    public static void simpleSnackBarFloating(Activity activity, String message) {

        View parent_view = activity.findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar.make(parent_view, "", Snackbar.LENGTH_LONG);

        //inflate view
        View view = activity.getLayoutInflater().inflate(R.layout.simple_snackbar_toast_floating, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding(0, 0, 0, 0);


        final TextView txt_simple_snackbar_toast_floating = view.findViewById(R.id.txt_simple_snackbar_toast_floating);

        txt_simple_snackbar_toast_floating.setText(message);

        snackBarView.addView(view, 0);
        snackbar.show();
    }


    public static void customToastFloating(Activity context, String message, ColorStateList colorBackgroundCardView, ColorStateList colorText) {
        final Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);

        //inflate view
        View view = context.getLayoutInflater().inflate(R.layout.simple_snackbar_toast_floating, null);

        final CardView card_simple_snackbar_toast_floating = view.findViewById(R.id.card_simple_snackbar_toast_floating);
        final TextView txt_simple_snackbar_toast_floating = view.findViewById(R.id.txt_simple_snackbar_toast_floating);
        txt_simple_snackbar_toast_floating.setText(message);
        card_simple_snackbar_toast_floating.setCardBackgroundColor(colorBackgroundCardView);//context.getResources().getColor(R.color.grey_90)
        txt_simple_snackbar_toast_floating.setTextColor(colorText);//context.getResources().getColor(R.color.white)
        toast.setView(view);
        toast.show();
    }

    public static void customSnackBarFloating(Activity activity, String message, ColorStateList colorBackgroundCardView, ColorStateList colorText) {

        View parent_view = activity.findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar.make(parent_view, "", Snackbar.LENGTH_LONG);

        //inflate view
        View view = activity.getLayoutInflater().inflate(R.layout.simple_snackbar_toast_floating, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding(0, 0, 0, 0);


        final CardView card_simple_snackbar_toast_floating = view.findViewById(R.id.card_simple_snackbar_toast_floating);
        final TextView txt_simple_snackbar_toast_floating = view.findViewById(R.id.txt_simple_snackbar_toast_floating);

        card_simple_snackbar_toast_floating.setCardBackgroundColor(colorBackgroundCardView);//context.getResources().getColor(R.color.grey_90)
        txt_simple_snackbar_toast_floating.setTextColor(colorText);//context.getResources().getColor(R.color.white)
        txt_simple_snackbar_toast_floating.setText(message);

        snackBarView.addView(view, 0);
        snackbar.show();
    }


}
