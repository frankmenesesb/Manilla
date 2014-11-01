package com.example.activities;


import com.example.accesibility.R;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AdminTelephone extends Activity{
	
	Button btnFace;
	Button btnTwitter;
	Button btnGmail;
	Button btnWhatsapp;
	ListView listaColores;
	ArrayAdapter<String> adaptador ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telefono);
		/*
		listaColores= (ListView) findViewById(R.id.listcolores);
		String[] listaColoresString ={"Azul","Verde","Rojo","Amarillo","Purpura","Cian","Blanco"};
		adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaColoresString);
		listaColores.setAdapter(adaptador);
		*/
		btnFace= (Button) findViewById(R.id.buttonFace);
		
		btnFace.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Dialog dialog = new Dialog(AdminTelephone.this);
				dialog.setContentView(R.layout.menu_face);
				dialog.setTitle("Colores...");	
				dialog.show();
			}
		});
		
	}
	
	
	
	
}
