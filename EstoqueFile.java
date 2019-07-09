package _TrabalhoFinal_POO2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;


public final class EstoqueFile {	

	private HashMapFile<String, Produto[]> mapFile = new HashMapFile<String, Produto[]>("produtos.txt");
	private HashMap<String, Produto[]> map;
	
	public EstoqueFile() {
		super();
		
		if(mapFile.readMap() == null) {
			this.map = new HashMap<String, Produto[]>();
		} else {
			readFile();
		}
	}
	
	public HashMap<String, Produto[]> getMap(){
		return this.map;
	}
	
	// Cria categoria com dado produto
	void createCategory(String categoria) {
		Produto[] pp = new Produto[10];
		map.put(categoria, pp);
	}
	
	// Retorna todas as categorias salvas
	String[] getAllCat() {
		Set<String> keys = this.map.keySet();
		TreeSet<String> sortedKeys = new TreeSet<String>(keys);
		String[] ret = Arrays.copyOf(sortedKeys.toArray(), sortedKeys.toArray().length, String[].class);
		return ret;
	}
	
	// Salva mapa em file
	void saveFile() {
		mapFile.writeMap(this.map);
	}
	
	// Le file e guarda em map
	void readFile(){
		this.map = mapFile.readMap();
	}
	
	// Retorna file
	HashMap<String, Produto[]> returnFile(){
		return mapFile.readMap();
	}
	
	// Adiciona Produto a sua categoria
	void addProduct(Produto prod) {
		
		if(this.map.get(prod.getCat()) == null) {	// Se categoria adicionada nao existe cria uma
			createCategory(prod.getCat());
		} 
		
		Produto[] pp = this.map.get(prod.getCat());
		
		for(int i = 0; i < pp.length; i++) {
			if(pp[i] == null) {
				pp[i] = prod;
				this.map.put(prod.getCat(), pp);
				saveFile();
				return;
			} else if(i == pp.length - 1 && pp[i] != null) { 	// Se array está cheio cria outro
				Produto[] newPP =  new Produto[pp.length*2];
				for(int j = 0; j<pp.length; j++) {
					newPP[j] = pp[j];
					if(j == pp.length - 1) {
						newPP[j + 1] = prod;
					}
				}
				this.map.put(prod.getCat(), newPP);
				saveFile();
				return;
			}
		}
	}
	
	void removeProduct(String cat, int code) throws IllegalArgumentException {
		if(this.map.get(cat) == null) {
			throw new IllegalArgumentException();
		} else {
			Produto[] p = this.map.get(cat);
			for(int i = 0; i<p.length; i++) {
				if(p[i].getCode() == code) {
					p[i] = null;
					this.map.put(cat, p);
					saveFile();
					return;
				}
			}
			throw new IllegalArgumentException();
		}
	}
	
	// Edita dados de dado produto com exceção do codigo, que é usado de referencia
	void editProduct(Produto novoProduto) throws IllegalArgumentException{
		Produto[] categoriaEditada = this.map.get(novoProduto.getCat());		
		for(int i = 0; i < categoriaEditada.length - 1; i++) {
			if(categoriaEditada[i].getCode() == novoProduto.getCode()) {
				categoriaEditada[i] = novoProduto;
				this.map.put(novoProduto.getCat(), categoriaEditada);
				saveFile();
				return;
			}
		}
		throw new IllegalArgumentException("Invalid product code!");
	}
	
	// Retorna produto especificado
	Produto getProduct(String cat, int code) throws NoSuchElementException{
		
		if(this.map.get(cat) == null) {
			throw new NoSuchElementException("Invalid Category!");
		}
		Produto[] pp = this.map.get(cat);
		for(int i = 0; i < pp.length; i++) {
			if(pp[i].getCode() == code && pp[i] != null) {
				return pp[i];
			}
		}
		throw new NoSuchElementException("Invalid code");
		
	}
	
	
	public static void main(String[]args) {
		Produto p1 = new Produto("Produto 1", "11/03/2019", 18.00, 421, "Categoria1");
		Produto p2 = new Produto("Produto 2", "12/03/2019", 19.00, 422, "Categoria1");
		Produto p3 = new Produto("Produto 3", "13/03/2019", 20.00, 423, "Categoria1");
		
		Produto hemp1 = new Produto("Produto 4", "20/04/2020", 42.00, 134, "Categoria2");
		Produto hemp2 = new Produto("Produto 5", "20/04/2020", 42.00, 424, "Categoria2");
		Produto hemp3 = new Produto("Produto 6", "20/04/2020", 42.00, 412, "Categoria2");
		
		EstoqueFile teste = new EstoqueFile();
		teste.addProduct(p1);
		teste.addProduct(p2);
		teste.addProduct(p3);
		teste.addProduct(hemp1);
		teste.addProduct(hemp2);
		teste.addProduct(hemp3);
	}
}