package _TrabalhoFinal_POO2;

import java.io.Serializable;

public class Funcionario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6823870130328233330L;
	
	private String name;
	private String role;
	private double sal;
	private int code;
	
	public Funcionario(String name, String role, double sal, int code) {
		super();
		this.code = code;
		this.name = name;
		this.role = role;
		this.sal = sal;
	}
	
	
	public String format() {
		return Integer.toString(this.getCode()) + this.getName() +
				this.getRole() + Double.toString(this.getSal());
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public double getSal() {
		return this.sal;
	}
	
	public void setCode(int c) {
		this.code = c;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setSal(Double sal) {
		this.sal = sal;
	}
}