package _TrabalhoFinal_POO2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class HashMapFile <K, V>{

	private String fileName;
	private HashMap<K, V> map;
	
	private FileOutputStream fop;
	private ObjectOutputStream oop;
	private FileInputStream fis;
	private ObjectInputStream ois;
	//private EstoqueFile testeMaps;
	
	HashMapFile(String fileName){
		this.fileName = fileName;
		if(readMap() == null) {
			this.map = new HashMap<K, V>();
		} else {
			this.map = readMap();
		}
	}
	
	// Escreve mapa dado como entrada
	void writeMap(HashMap<K, V> map) {
		this.map = map;
		try 
		{
			// Delete file anterior para nao salvar multiplos mapas
			File file = new File(fileName);
			if(file.exists()) {
				file.delete();
			}
			fop = new FileOutputStream(file);
			oop = new ObjectOutputStream(fop);
			oop.writeObject(map);
			System.out.println("Objeto escrito com sucesso!");
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(oop != null || fop != null) {
				try {
					fop.close();
					oop.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	// Le mapa salvo em file especificada no construtor
	// Aloca mapa lido na variavel map
	HashMap<K, V> readMap() {
		try
		{
			this.fis = new FileInputStream(new File(this.fileName));
			this.ois = new ObjectInputStream(fis);
			Object maps = new HashMap<K, V>();
			maps = ois.readObject();
			this.map = (HashMap<K, V>) maps;
		}
		catch(FileNotFoundException fn) {
			System.err.printf("File not found!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(fis != null || ois != null) {
				try {
					fis.close();
					ois.close();
					//System.out.println("Fechou read com sucesso!");
					return map;
				} catch(IOException e) {
					e.printStackTrace();
				}	
			}
		}
		System.err.printf("deu ruim read");
		return null;
	}

	public static void main(String[]args) {
		HashMapFile<String, Produto[]> test = new HashMapFile<String, Produto[]>("produtos.txt");
		
		HashMap<String, Produto[]> mapTest = new HashMap<String, Produto[]>();
		
		Produto p = new Produto("prodTeste", 420);
		Produto[] array = new Produto[10];
		array[0] = p;
		mapTest.put("catTeste", array);
		
		test.writeMap(mapTest);
		
		System.out.println(test.readMap().get("catTeste"));
		Produto[] s = test.readMap().get("catTeste");
		System.out.println(s[0].getName()+"\t"+s[0].getCode());
		
	}

}
