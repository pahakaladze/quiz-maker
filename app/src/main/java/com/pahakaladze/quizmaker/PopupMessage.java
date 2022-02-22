package com.pahakaladze.quizmaker;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.ContextWrapper;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class PopupMessage {

    private PopupMessage() {
    }

    public static void showCorrectMark(View view, ContextWrapper context) {
        show(view, context, R.layout.correct_mark);

    }

    public static void showIncorrectMark(View view, ContextWrapper context) {
        show(view, context, R.layout.incorrect_mark);
    }

    private static void show(View view, ContextWrapper context, int layoutIndex) {
        LayoutInflater layoutInflater
                = (LayoutInflater) context.getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(layoutIndex, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(view.getHeight());
        popupWindow.setWidth(view.getHeight());
        System.out.println("view height " + view.getHeight());
        System.out.println("popup height " + popupWindow.getHeight());
        System.out.println("view y " + (int) view.getY());
        popupWindow.showAsDropDown(view, 40, -view.getHeight(), Gravity.START);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                popupWindow.dismiss();
            }
        }, 500);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}
