package com.example.bd;

public class Usuario {
	
	private String nombre;
	private String apellido;
	private String email;
	private String num_telefono;
	private String password;
	private String empresa;
	private String tag;
	private String mac;
	
	public Usuario() {

	}
	
	public Usuario(String nombre, String apellido, String email,
			String num_telefono, String password){
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.num_telefono = num_telefono;
		this.password = password;
	}
	public Usuario(String nombre, String apellido, String email,
			String num_telefono, String password, String empresa, String tag, String mac) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.num_telefono = num_telefono;
		this.password = password;
		this.empresa = empresa;
		this.tag = tag;
		this.mac = mac;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNum_telefono() {
		return num_telefono;
	}
	public void setNum_telefono(String num_telefono) {
		this.num_telefono = num_telefono;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	

}
