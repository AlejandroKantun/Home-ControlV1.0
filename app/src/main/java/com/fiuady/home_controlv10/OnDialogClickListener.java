package com.fiuady.home_controlv10;

import android.app.DialogFragment;

/**
 * Created by Manuel on 24/05/2017.
 */

public interface OnDialogClickListener {
    void onDialogPositiveClick(DialogFragment dialogFragment, int code, String Name, String Email, String Password, String Pin, int accountId);
    void onDialogNegativeClick(DialogFragment dialogFragment, int code, String Name, String Email, String Password, String Pin, int accountId);

}
