package com.fiuady.home_controlv10.db;

/**
 * Created by owner on 5/19/2017.
 */

public final class AccountDbSchema {

    public static final class AccountTable {
        public static final String Name = "cuenta";

        public static final class Columns {
            public static final String ID = "id";
            public static final String Tipo = "tipo";
            public static final String Usuario = "usuario";
            public static final String Correo = "e_mail";
            public static final String Contrasena = "password";
            public static final String Configuracion = "configuracion";
        }
    }

}
