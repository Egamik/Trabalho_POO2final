package _TrabalhoFinal_POO2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class FuncionarioFile {

	private HashMapFile<String, Funcionario[]> mapFile = new HashMapFile<String, Funcionario[]>("employ.txt");
	private HashMap<String, Funcionario[]> map;
	
	public FuncionarioFile() {
		super();
		if(mapFile.readMap() == null) {
			this.map = new HashMap<String, Funcionario[]>();
		} else {
			readFile();
		}
	}
	
	HashMap<String, Funcionario[]> getMap() { 
		return this.map;
	}
	
	void saveFile() {
		mapFile.writeMap(this.map);
	}
	
	void readFile() {
		this.map = mapFile.readMap();
	}
	
	HashMap<String, Funcionario[]> returnFile(){
		return mapFile.readMap();
	}
	
	void createCategory(String categoria) {
		Funcionario[] f = new Funcionario[10];
		map.put(categoria, f);
	}
	
	String[] getAllRole() {
		Set<String> keys = map.keySet();
		TreeSet<String> sortedKeys = new TreeSet<String>(keys);
		String[] ret = Arrays.copyOf(sortedKeys.toArray(), sortedKeys.toArray().length, String[].class);
		return ret;
	}
	
	void addFunc(Funcionario func) {
		
		if(map.get(func.getRole()) == null) {
			createCategory(func.getRole());
		}
		
		Funcionario[] f = map.get(func.getRole());
		
		for(int i = 0; i<f.length; i++) {
			if(f[i] == null) {
				f[i] = func;
				map.put(func.getRole(), f);
				saveFile();
				return;
			} else if(i == f.length - 1 && f[i] != null) {
				Funcionario[] ff = new Funcionario[f.length*2];
				for(int j = 0; j< f.length; j++) {
					ff[j] = f[j];
					if(j == f.length - 1) {
						ff[j + 1] = func;
					}
				}
				map.put(func.getRole(), ff);
				saveFile();
				return;
			}
		}		
	}
	void removeFunc(String role, int code) throws IllegalArgumentException {
		if(this.map.get(role) == null) {
			throw new IllegalArgumentException();
		} else {
			Funcionario[] p = this.map.get(role);
			for(int i = 0; i<p.length; i++) {
				if(p[i].getCode() == code) {
					p[i] = null;
					this.map.put(role, p);
					saveFile();
					return;
				}
			}
		}
	}
	void editFunc(String role, Funcionario newFunc) throws IllegalArgumentException{
		Funcionario[] f = map.get(role);
		for(int i = 0; i<f.length; i++) {
			if(f[i].getCode() == newFunc.getCode()) {
				f[i] = newFunc;
				saveFile();
				return;
			}
		}
		throw new IllegalArgumentException("Invalid employee code!");
	}
	
	Funcionario getFunc(String role, int code) throws NoSuchElementException{
		if(map.get(role) == null) {
			throw new NoSuchElementException("Invalid role");
		}
		Funcionario[] f = map.get(role);
		for(int i = 0; i<f.length; i++) {
			if(f[i].getCode() == code && f[i] != null) {
				return f[i];
			}
		}
		throw new NoSuchElementException("Invalid code");
	}
	
	public static void main(String[] args) {
		Funcionario f1 = new Funcionario("Funcionario 1", "vendedor", 200, 101);
		Funcionario f2 = new Funcionario("Funcionario 2", "vendedor", 200, 102);
		Funcionario f3 = new Funcionario("Funcionario 3", "vendedor", 200, 103);
		Funcionario f4 = new Funcionario("Funcionario 4", "gerente", 400, 201);
		Funcionario f5 = new Funcionario("Funcionario 5", "gerente", 550, 203);
		Funcionario f6 = new Funcionario("Funcionario 6", "gerente", 666, 203);
		
		FuncionarioFile teste = new FuncionarioFile();
		teste.addFunc(f1);
		teste.addFunc(f2);
		teste.addFunc(f3);
		teste.addFunc(f4);
		teste.addFunc(f5);
		teste.addFunc(f6);
		
	}
}
