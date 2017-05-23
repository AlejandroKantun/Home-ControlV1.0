package com.fiuady.home_controlv10.db;

import android.content.ContentValues;
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
                cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Usuario))), cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Correo))), cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Contrasena))) ,cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Pin))),
                cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON1))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON2))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON3))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON4))),
                cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON5))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON6))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON7))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON8))),
                cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON9))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON10))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON11))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON12))));

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

    public Cuentas getAccountbyid(String id) {
        // ArrayList<Customers> list = new ArrayList<Customers>();
        // Cuentas cuentas = new Cuentas();
        AccountCursor cursor = new AccountCursor(db.rawQuery(" SELECT * FROM cuenta where id = " + id , null));// ORDER BY last_name
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

    public void CreateNonAdminAccount(String usuario, String email , String password, String Pin) {

        int mx = -1;
        Cursor cursor = db.rawQuery("SELECT max(id) from cuenta", new String[]{});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mx = cursor.getInt(0);
            }
            cursor.close();
        } else {
            mx = -1;
        }
        if (mx != -1) {
            //ContentValues values = new ContentValues();
            //values.put(CategoriesTable.Columns.DESCRIPTION, category.getDescription());
            //db.execSQL(" INSERT INTO cuenta (id,tipo, usuario, e_mail, password, Pin) VALUES (2, 0, 'AARON', 'p', '123','123');");
            db.execSQL("INSERT INTO cuenta (id,tipo, usuario, e_mail, password, Pin) VALUES (" + String.valueOf(mx + 1) + "," + 0 + ",'" + usuario + "', '" + email + "', '" + password + "', '" + Pin + "');");

        }
    }

    public void modify_account(String id, String usuario, String password, String Pin) {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(AccountDbSchema.AccountTable.Columns.Usuario, usuario);
        values.put(AccountDbSchema.AccountTable.Columns.Contrasena, password);
        values.put(AccountDbSchema.AccountTable.Columns.Pin, Pin);
        db.update(AccountDbSchema.AccountTable.Name,
                values,
                AccountDbSchema.AccountTable.Columns.ID + "= ?",
                new String[]{id}
        );

        //  db.rawQuery("UPDATE products " +
        //   "SET qty = "+  qty + "" +
        //   " WHERE id = "+ id  , null);

    }



}
