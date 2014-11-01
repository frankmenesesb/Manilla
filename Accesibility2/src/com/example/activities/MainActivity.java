package com.example.activities;

import com.example.accesibility.R;
import com.example.bd.DataBaseHelper;
import com.example.bd.Usuario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {

	private Button btnAceptar;
	private TextView btnRegistrar;
	private DataBaseHelper dbHelper;
	private String email;
	private String password;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Llamado al XML
		setContentView(R.layout.activity_main);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Log.d("---------","ENTRO MAIN");
		//Vincular el boton
		btnAceptar = (Button) findViewById(R.id.btn_Aceptar);
		
		
		
		dbHelper = new DataBaseHelper(this);
		
		
		btnAceptar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email = ((EditText) findViewById(R.id.edit_email)).getText().toString();
				password = ((EditText) findViewById(R.id.edit_pass)).getText().toString();
				verificarAcceso();
				Intent btintent = new Intent(MainActivity.this,BluetoothPaired.class);
				startActivity(btintent);
				Toast.makeText(MainActivity.this, "Bienvenido", 0).show();
				
				
			}
		});

		btnRegistrar = (TextView) findViewById(R.id.btn_Registrar);

		btnRegistrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentobj = new Intent(MainActivity.this,
						Registro.class);
				startActivity(intentobj);

			}
		});
	}

	public void verificarAcceso() {
		// TODO Auto-generated method stub
		Usuario us = new Usuario();
		System.out.println("Carlos ENTRO MAIN");
		us = dbHelper.getUsuario(email);
		System.out.println("Carlos paso MAIN con email  "+ us.getEmail());
		if(us.getEmail() != null){
			if(us.getPassword().equals(password)){
				Intent btintent = new Intent(this,BluetoothPaired.class);
				startActivity(btintent);
				Toast.makeText(this, "Bienvenido", 0).show();
				
			}
			
		}else{
			Toast.makeText(this, "Usuario No Registrado", 0).show();
		}
		
	}
	

}
