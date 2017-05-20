package com.fiuady.home_controlv10.db;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owner on 5/19/2017.
 */

class AccountCursor extends CursorWrapper {
    public AccountCursor(Cursor cursor) {
        super(cursor);
    }

    public Cuentas getCuenta() {

        Cursor cursor = getWrappedCursor();
        return new Cuentas(cursor.getInt(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.ID))), cursor.getInt(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Tipo))),
                cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Usuario))), cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Correo))), cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Contrasena))) ,cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Configuracion)))     );

    }

}

public final class Account {
    private  AccountHelper accountHelper;

    private SQLiteDatabase db;

    public Account(Context context) {
        //InventoryHelper.backupDatabaseFile(context);
        accountHelper= new AccountHelper(context);
        db = accountHelper.getWritableDatabase();
    }

    public Cuentas getAccount(String email) {
       // ArrayList<Customers> list = new ArrayList<Customers>();
       // Cuentas cuentas = new Cuentas();
        AccountCursor cursor = new AccountCursor(db.rawQuery(" SELECT * FROM cuenta where e_mail = '" + email+ "'", null));// ORDER BY last_name
       // while (cursor.moveToNext()) {
       if( cursor.moveToFirst())
       {
           Cuentas cuenta = cursor.getCuenta();
           cursor.close();
           return  cuenta;
       }
       else
       {
           return  null;
       }

        //return cuenta;
    }

}
