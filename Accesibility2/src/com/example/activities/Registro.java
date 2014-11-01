package com.example.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.accesibility.R;
import com.example.bd.DataBaseHelper;
import com.example.bd.Usuario;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.VoicemailContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registro extends Activity {

	private TextView editNombre;
	private TextView editApellido;
	private TextView editEmail;
	private TextView editTelefono;
	private TextView editPassword;
	private TextView editPasswordConf;
	private Button aceptar;
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private String password;
	private DataBaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		dbHelper = new DataBaseHelper(this);
		editNombre = (EditText) findViewById(R.id.editTextRegNombre);
		editApellido = (EditText) findViewById(R.id.editTextRegApellido);
		editEmail = (EditText) findViewById(R.id.editTextRegEmail);
		editTelefono = (EditText) findViewById(R.id.editTextRegTelefono);
		editPassword = (EditText) findViewById(R.id.editTextRegPassword);
		editPasswordConf = (EditText) findViewById(R.id.editTextRegPasswordConf);

		aceptar = (Button) findViewById(R.id.buttonAceptarReg);

		aceptar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (verificarPassword()) {
					System.out.println("Carlos Boton");
					nombre = editNombre.getText().toString();
					apellido = editApellido.getText().toString();
					email = editEmail.getText().toString();
					telefono = editTelefono.getText().toString();
					password = editPassword.getText().toString();
					System.out.println("Carlos Boton Termino");
					agregarUsuario();
				} else {

				}
				Intent obj = new Intent(Registro.this, MainActivity.class);
				startActivity(obj);

			}

		});

	}

	private boolean verificarPassword() {
		// TODO Auto-generated method stub
		boolean var = true;
		if (!editPassword.getText().toString()
				.equals(editPasswordConf.getText().toString())) {
			Toast.makeText(this, "Verificar Contraseña", 0).show();
			var = false;
		}
		return var;
	}

	private void agregarUsuario() {
		// TODO Auto-generated method stub

		List<Usuario> lista = new ArrayList<Usuario>();
		lista = dbHelper.getTodoslUsuarios();
		System.out.println("Carlos NO FALLE");

		if (!verificarExistencia(lista)) {
			dbHelper.setUsuario(new Usuario(nombre, apellido, email, telefono,
					password));
			Toast.makeText(this, "Registro Exitoso", 0).show();
		} else {
			Toast.makeText(this, "Email Existente", 0).show();
			editEmail.setText("");

		}

	}

	private boolean verificarExistencia(List<Usuario> lista) {
		// TODO Auto-generated method stub
		boolean existe = false;
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getEmail().equalsIgnoreCase(email)) {
				existe = true;
				break;
			}
		}
		return existe;
	}
}
