package com.example.bd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "LightMeBD";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "CREATE TABLE usuarios (nombre TEXT NOT NULL, apellido TEXT NOT NULL, "
			+ "email TEXT PRIMARY KEY,"
			+ "telefono TEXT NOT NULL, password TEXT NOT NULL, empresa TEXT, tag TEXT, mac TEXT)";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + "usuarios");

		// Create tables again
		onCreate(db);
	}

	// Adding new contact
	public void setUsuario(Usuario usuario) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("nombre", usuario.getNombre());
		values.put("apellido", usuario.getApellido());
		values.put("email", usuario.getEmail());
		values.put("telefono", usuario.getNum_telefono());
		values.put("password", usuario.getPassword());

		db.insert("usuarios", null, values);
		db.close();
	}

	// Getting single contact
	public Usuario getUsuario(String email) {
		System.out.println("Carlos " + String.valueOf(email));

		SQLiteDatabase db = this.getReadableDatabase();
		System.out.println("Carlos REAdable");
		//Cursor cursor = db.query("usuarios", new String[] { "nombre",
			//	"apellido", "email", "telefono", "password", "empresa", "tag",
				//"mac" }, "email=?", new String[] {email }, null, null, null,
				//null);
		String[] args = new String[] {email};
		
		Cursor cursor = db.rawQuery("SELECT nombre,apellido,email,telefono,password " +
				"FROM usuarios WHERE email =?", args);
		
		System.out.println("Carlos CURSOR"+cursor.toString());
		Usuario usuario = new Usuario();
		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			System.out.println("Carlos CURSOR  "+cursor.getString(2));
			usuario = new Usuario(cursor.getString(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), null,
					null, null);
		}else{
			usuario = new Usuario();
		}
		// return contact
		return usuario;
	}
	

	public List<Usuario> getTodoslUsuarios() {

		List<Usuario> contactList = new ArrayList<Usuario>();
		// Select All Query
		System.out.println("Carlos Antes QUERY");
		String selectQuery = "SELECT * FROM " + "usuarios";

		SQLiteDatabase db = this.getWritableDatabase();
		System.out.println("Carlos Antes QUERY 2");

		Cursor cursor = db.rawQuery(selectQuery, null);
		System.out.println("Carlos getTodos");

		// looping through all rows and adding to list
		if (cursor != null) {

			if (cursor.moveToFirst()) {
				do {
					Usuario usuario = new Usuario(cursor.getString(0),
							cursor.getString(1), cursor.getString(2),
							cursor.getString(3), cursor.getString(4),
							cursor.getString(5), cursor.getString(6),
							cursor.getString(7));
					// Adding contact to list
					contactList.add(usuario);
				} while (cursor.moveToNext());
			}
		} else {

		}

		// return contact list
		return contactList;
	}
}
