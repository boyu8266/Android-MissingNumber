package boryuh8266.gmail.com.missingnumber.model.interfaces;

import android.support.v7.app.AlertDialog;

/**
 * Listener to get input value.
 */
public interface OnInputLongListener{
    /**
     * Listener to get the double inputed value
     * @param android.support.v7.app.AlertDialog
     * @param java.lang.Long
     * @return true if listener consumes the event
     */
    boolean onInputLong(AlertDialog dialog, Long value);
}
