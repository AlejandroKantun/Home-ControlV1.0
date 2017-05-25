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
                cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON9))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON10))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON11))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON12))),
                cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON13))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.JSON14))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Extra1))),cursor.getString(cursor.getColumnIndex((AccountDbSchema.AccountTable.Columns.Extra2))));
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
    }

    public void Delete_account(String id)
    {

            db.execSQL("  DELETE FROM cuenta where id = " + id);

    }

    public void Guardar_Configuracion(String id , String json1, String json2, String json3, String json4, String json5, String json6, String json7, String json8, String json9, String json10, String json11, String json12, String json13, String json14, String extra1, String extra2 ) {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(AccountDbSchema.AccountTable.Columns.JSON1, json1);
        values.put(AccountDbSchema.AccountTable.Columns.JSON2, json2);
        values.put(AccountDbSchema.AccountTable.Columns.JSON3, json3);
        values.put(AccountDbSchema.AccountTable.Columns.JSON4, json4);
        values.put(AccountDbSchema.AccountTable.Columns.JSON5, json5);
        values.put(AccountDbSchema.AccountTable.Columns.JSON6, json6);
        values.put(AccountDbSchema.AccountTable.Columns.JSON7, json7);
        values.put(AccountDbSchema.AccountTable.Columns.JSON8, json8);
        values.put(AccountDbSchema.AccountTable.Columns.JSON9, json9);
        values.put(AccountDbSchema.AccountTable.Columns.JSON10, json10);
        values.put(AccountDbSchema.AccountTable.Columns.JSON11, json11);
        values.put(AccountDbSchema.AccountTable.Columns.JSON12, json12);
        values.put(AccountDbSchema.AccountTable.Columns.JSON13, json13);
        values.put(AccountDbSchema.AccountTable.Columns.JSON14, json14);
        values.put(AccountDbSchema.AccountTable.Columns.Extra1, extra1);
        values.put(AccountDbSchema.AccountTable.Columns.Extra2, extra2);

        db.update(AccountDbSchema.AccountTable.Name,
                values,
                AccountDbSchema.AccountTable.Columns.ID + "= ?",
                new String[]{id}
        );
    }

    public void Update_JSON_Reyes(String id , String json10) {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(AccountDbSchema.AccountTable.Columns.JSON10, json10);

        db.update(AccountDbSchema.AccountTable.Name,
                values,
                AccountDbSchema.AccountTable.Columns.ID + "= ?",
                new String[]{id}
        );
    }


    public void Update_Jason_Frodo(String id , String json1, String json2, String json3, String json4, String json5, String json6, String json7) {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(AccountDbSchema.AccountTable.Columns.JSON1, json1);
        values.put(AccountDbSchema.AccountTable.Columns.JSON2, json2);
        values.put(AccountDbSchema.AccountTable.Columns.JSON3, json3);
        values.put(AccountDbSchema.AccountTable.Columns.JSON4, json4);
        values.put(AccountDbSchema.AccountTable.Columns.JSON5, json5);
        values.put(AccountDbSchema.AccountTable.Columns.JSON6, json6);
        values.put(AccountDbSchema.AccountTable.Columns.JSON7, json7);

        db.update(AccountDbSchema.AccountTable.Name,
                values,
                AccountDbSchema.AccountTable.Columns.ID + "= ?",
                new String[]{id}
        );
    }

    public void Update_Jason_Chino(String id , String json8, String json9) {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(AccountDbSchema.AccountTable.Columns.JSON8, json8);
        values.put(AccountDbSchema.AccountTable.Columns.JSON9, json9);

        db.update(AccountDbSchema.AccountTable.Name,
                values,
                AccountDbSchema.AccountTable.Columns.ID + "= ?",
                new String[]{id}
        );
    }

    public void Update_Jason_Madera(String id ,  String json11, String json12, String json13, String json14) {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(AccountDbSchema.AccountTable.Columns.JSON11, json11);
        values.put(AccountDbSchema.AccountTable.Columns.JSON12, json12);
        values.put(AccountDbSchema.AccountTable.Columns.JSON13, json13);
        values.put(AccountDbSchema.AccountTable.Columns.JSON14, json14);
        db.update(AccountDbSchema.AccountTable.Name,
                values,
                AccountDbSchema.AccountTable.Columns.ID + "= ?",
                new String[]{id}
        );
    }

    public void Update_Extras_Frodo(String id ,  String extra1, String extra2) {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(AccountDbSchema.AccountTable.Columns.Extra1, extra1);
        values.put(AccountDbSchema.AccountTable.Columns.Extra2, extra2);

        db.update(AccountDbSchema.AccountTable.Name,
                values,
                AccountDbSchema.AccountTable.Columns.ID + "= ?",
                new String[]{id}
        );
    }


    public List<Cuentas> Getallaccounts() {
        List<Cuentas> list = new ArrayList<Cuentas>();

        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        AccountCursor  cursor = new AccountCursor ((db.rawQuery("SELECT * FROM cuenta where tipo = 0", null)));

        while (cursor.moveToNext()) {

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

                list.add((cursor.getCuenta()));  // metodo wrappcursor
        }
        cursor.close();
        return list;
    }
    public boolean check_email(String email) {
        Cursor cursor = db.rawQuery("SELECT id from cuenta GROUP BY id HAVING e_mail = '" + email + "';", new String[]{});
        if (cursor == null) {

            return true;
        } else if (!cursor.moveToFirst()) {

            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }




}
