package com.fiuady.home_controlv10;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.home_controlv10.db.Account;

/**
 * Created by Manuel on 24/05/2017.
 */

public class MyGeneralDialog extends DialogFragment {
    private static final String EXTRA_USER_NAME="extra_name";
    private static final String EXTRA_USER_EMAiL="extra_message";
    private static final String EXTRA_USER_PIN="extra_pin";
    private static final String EXTRA_USER_PASSWORD="extra_password";
    private static final String EXTRA_USER_ID="extra_Id";
    private static final String DIALOG_CODE="dialog_code";
    private EditText txtEditUserName;
    private EditText txtEditUserPassword;
    private EditText txtEditUserPinDoors;

    private EditText txtEditUserEmail;
    private  int code;
    private String Pass;
    private int UserId;
    private  String Email;

    OnDialogClickListener callback;
    public MyGeneralDialog()
    {
        //necessary constructor
    }
    public static MyGeneralDialog newInstance(int AccountId, String Name,String Email,String Password, String Pin, int code){
        MyGeneralDialog frag = new MyGeneralDialog();
        Bundle args= new Bundle();
        args.putInt(EXTRA_USER_ID,AccountId);
        args.putString(EXTRA_USER_NAME,Name);
        args.putString(EXTRA_USER_EMAiL, Email);
        args.putString(EXTRA_USER_PASSWORD,Password);
        args.putString(EXTRA_USER_PIN,Pin);
        args.putInt(DIALOG_CODE,code);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback =(OnDialogClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement NoticeDialogListener");

        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String User_Name = getArguments().getString(EXTRA_USER_NAME);
        String User_Email= getArguments().getString(EXTRA_USER_EMAiL);
        Email = User_Email;
        String User_Password= getArguments().getString(EXTRA_USER_PASSWORD);
        Pass = User_Password;
        UserId = getArguments().getInt(EXTRA_USER_ID);
        String User_Pin = getArguments().getString(EXTRA_USER_PIN);
         code = getArguments().getInt(DIALOG_CODE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        if(code == 1)
        {
           View Dialogview = inflater.inflate(R.layout.add_account,null);
            txtEditUserName = (EditText)Dialogview.findViewById(R.id.EditText_UserName);
            txtEditUserEmail = (EditText)Dialogview.findViewById(R.id.EditEmail);
            txtEditUserPassword = (EditText)Dialogview.findViewById(R.id.EditPassword);
            txtEditUserPinDoors = (EditText)Dialogview.findViewById(R.id.EditPin);


            builder.setView(Dialogview);

        }
        else
        {
            View Dialogview = inflater.inflate(R.layout.modify_account,null);
            txtEditUserName = (EditText)Dialogview.findViewById(R.id.EditText_UserName);
            txtEditUserPassword = (EditText)Dialogview.findViewById(R.id.EditPassword);
            txtEditUserPinDoors = (EditText)Dialogview.findViewById(R.id.EditPin);

            txtEditUserName.setText(User_Name);
            txtEditUserPinDoors.setText(User_Pin);
            txtEditUserPassword.setText(User_Password);

            builder.setView(Dialogview);

        }


       // View Dialogview = inflater.inflate(R.layout.,null);
        //builder.setTitle(title);
       // builder.setMessage(message);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*callback.onDialogPositiveClick(MyGeneralDialog.this,getArguments().getInt(DIALOG_CODE));
                dialog.dismiss();*/
            }

        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*if(dialog != null){
                    callback.onDialogNegativeClick(MyGeneralDialog.this,getArguments().getInt(DIALOG_CODE));
                    dialog.dismiss();
                }*/
                dialog.dismiss();

            }
        });
        return builder.create() ;
    }
    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d!=null)
        {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String UserName = txtEditUserName.getText().toString();
                    //String UserPassword = txtEditUserPassword.getText().toString();
                    String UserPin = txtEditUserPinDoors.getText().toString();
                    String UserPassword;
                    String UserEmail;
                    Account account = new Account(getActivity());

                    if(code == 1)
                    {
                        UserPassword = txtEditUserPassword.getText().toString();
                        UserEmail = txtEditUserEmail.getText().toString();
                    }
                    else
                    {
                        UserPassword = Pass;
                        UserEmail = Email;


                    }
                    if(UserName.length()==0 || UserEmail.length() == 0 || UserPassword.length()==0 || UserPin.length() == 0)
                    {
                        Toast.makeText(getActivity(),"All Fields must be entered",Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if(code == 1)
                        {
                            if (!account.check_email(UserEmail))
                            {
                                Toast.makeText(getActivity(),"The email account already exists!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                callback.onDialogPositiveClick(MyGeneralDialog.this, code, UserName, UserEmail, UserPassword, UserPin, UserId);
                                d.dismiss();
                            }
                        }
                        else {

                            callback.onDialogPositiveClick(MyGeneralDialog.this, code, UserName, UserEmail, UserPassword, UserPin, UserId);
                            d.dismiss();
                        }
                    }
                }
            });

        }
    }


}
