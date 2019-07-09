package _TrabalhoFinal_POO2;

import java.io.Serializable;

public class Produto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;	//Product name
	private double price;	//Product price
	private String arrival;		//Date when product arrived on stock	
	private int code;
	private String categoria;
	
	public Produto(String name, int code) {
		super();
		this.code = code;
		this.name = name;
	}
	
	public Produto(String name, String fab, double price, int code, String cat) {
		super();
		this.name = name;
		this.arrival = fab;
		this.price = price;
		this.code = code;
		this.categoria = cat;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getArrival() {
		return this.arrival;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getCat() {
		return this.categoria;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setArrival(String fab) {
		this.arrival = fab;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setCat(String c) {
		this.categoria = c;
	}
}