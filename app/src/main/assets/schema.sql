CREATE TABLE [cuenta](
    [id] INTEGER PRIMARY KEY,
    [tipo] INTEGER NOT NULL,
    [usuario] TEXT NOT NULL,
    [e_mail] TEXT NOT NULL,
    [password] TEXT NOT NULL,
    [configuracion] TEXT
    );