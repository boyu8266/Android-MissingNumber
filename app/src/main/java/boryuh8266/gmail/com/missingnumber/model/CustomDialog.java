package boryuh8266.gmail.com.missingnumber.model;

import android.content.Context;

import boryuh8266.gmail.com.missingnumber.R;
import boryuh8266.gmail.com.missingnumber.model.interfaces.Closure;

public class CustomDialog {

    private Context context;
    private AwesomeSuccessDialog dialog;

    public CustomDialog(Context context) {
        this.context = context;

        dialog = new AwesomeSuccessDialog(context);
    }

    public AwesomeSuccessDialog getSuccessDialog() {
        dialog.setTitle(R.string.success)
                .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_success, R.color.white)
                .setCancelable(false)
                .setPositiveButtonText(context.getString(R.string.dialog_game_button))
                .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                .setPositiveButtonTextColor(R.color.white);

        return dialog;
    }

    public AwesomeSuccessDialog getFailDialog() {
        dialog.setTitle(R.string.fail)
                .setColoredCircle(R.color.dialogErrorBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                .setCancelable(false)
                .setNegativeButtonText(context.getString(R.string.fail_negative_button))
                .setNegativeButtonbackgroundColor(R.color.dialogErrorBackgroundColor)
                .setNegativeButtonTextColor(R.color.white)
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                });

        return dialog;
    }

    public AwesomeSuccessDialog getWarningDialog() {
        dialog.setTitle(R.string.fail)
                .setTitle(R.string.warn)
                .setColoredCircle(R.color.dialogWarningBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white)
                .setCancelable(false)
                .setNegativeButtonText(context.getString(R.string.fail_negative_button))
                .setNegativeButtonbackgroundColor(R.color.dialogWarningBackgroundColor)
                .setNegativeButtonTextColor(R.color.white)
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                });

        return dialog;
    }

}
