package _TrabalhoFinal_POO2;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


// Layout
public class Estoque extends JPanel {

	private JTextField f1, f2, f3, f4, f5;
	private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, title;
	private JButton btnAdd, btnRemove;
	private JPanel panel;
	private JTextArea display;
	private EstoqueFile eFile;
	
	public String fieldName, fieldArrival, fieldPrice, fieldCode, fieldCat;
	
	public Estoque() {
		super();
		this.eFile = new EstoqueFile();
		
	}
	
	public void addProduct(Produto p) {
		eFile.addProduct(p);
	}	
	
	public String getCategoryDisplay(String categoria) {
		HashMap<String, Produto[]> map = this.eFile.getMap();
		Produto[] pro = map.get(categoria);
		String ret = "";
		for(int i = 0; i<pro.length; i++) {
			ret += pro[i].getName()+" "+ pro[i].getArrival()+" "+pro[i].getPrice()+" "+
					pro[i].getCode()+ "\n";
		}
		return ret;
	}
	
	public String getAllCatDisplay() {
		HashMap<String, Produto[]> map = this.eFile.getMap();
		String[] keys = this.eFile.getAllCat();
		String ret = "";
		for(int i = 0; i<keys.length; i++) {
			Produto[] p = map.get(keys[i]);
			for(int j = 0; j<p.length; j++) {
				if(p[j] != null) {
					ret += p[j].getName()+"\t"+ p[j].getArrival()+"\t"+p[j].getPrice()+"\t"+
					p[j].getCode()+"\t"+p[j].getCat()+"\n";
				}
			}
		}
		return ret;
	}
	
	public void removeProduct(int code, String categoria) {
		HashMap<String, Produto[]> map = this.eFile.getMap();
		Produto[] p = map.get(categoria);
		for(int i =0; i<p.length; i++) {
			if(p[i].getCode() == code) {
				p[i] = null;
				map.put(categoria, p);
				
				return;
			}
		}
	}
	
	// Layout para funcionarios
	void setFunc() {	// Só pode checar estoque
		
		display = new JTextArea();
		display.setRows(30);
		display.setColumns(30);
		display.setEditable(false);
		String a = this.getAllCatDisplay();
		this.display.setText(a);
		display.setBackground(Color.WHITE);
		
		title = new JLabel("STOCK");
		Font font = new Font("Bold", Font.BOLD, 26);
		title.setFont(font);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.ParallelGroup hgroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
		hgroup.addComponent(title);
		hgroup.addComponent(display);
		layout.setHorizontalGroup(hgroup);
		
		GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
		vgroup.addComponent(title);
		vgroup.addComponent(display);
		layout.setVerticalGroup(vgroup);
		this.setLayout(layout);
		
	}
	
	// Layout para administrador
	void setAdmin() {	// Pode editar estoque
		JPanel panelDisplay = new JPanel();
		display = new JTextArea();
		display.setEditable(false);
		display.setRows(30);
		display.setColumns(30);
		String a = this.getAllCatDisplay();
		this.display.setText(a);
		panelDisplay.add(display);
		panelDisplay.setBackground(Color.WHITE);
		
		f1 = new JTextField(20);
		f2 = new JTextField(20);
		f3 = new JTextField(20);
		f4 = new JTextField(20);
		f5 = new JTextField(20);
		
		lbl0 = new JLabel("Product");
		lbl1 = new JLabel("Name:");
		lbl2 = new JLabel("Arrival:");
		lbl3 = new JLabel("Price:");
		lbl4 = new JLabel("Code:");
		lbl5 = new JLabel("Category:");
		
		btnAdd = new JButton("Add");
		btnRemove = new JButton("Remove");
		// Falta setar display
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldName = f1.getText();
				fieldArrival = f2.getText();
				fieldPrice = f3.getText();
				fieldCode = f4.getText();
				fieldCat = f5.getText();
				if((!f3.getText().matches("[0-9]+") || !f4.getText().matches("[0-9]+"))
						&& (f3.getText() != "" || f4.getText() != "")) {
					JOptionPane.showMessageDialog(null,
							"Use only numbers at price and code fields",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if(f3.getText().equals("")||f4.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Don't leave the code and category fields blank",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				double price = Double.parseDouble(fieldPrice);
				int code = Integer.parseInt(fieldCode);
				Produto p = new Produto(fieldName, fieldArrival, price, code, fieldCat);
				addProduct(p);
				display.setText(getAllCatDisplay());
				fieldName = null;
				fieldArrival = null;
				fieldPrice = null;
				fieldCode = null;
				fieldCat = null;
			}
		});
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldCode = f4.getText();
				fieldCat = f5.getText();
				if(!f4.getText().matches("[0-9]+") && f4.getText() != "") {
					JOptionPane.showMessageDialog(null,
							"Use only integers!",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if(f4.getText().equals("") || f5.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Don't leave code or category fields empty",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String[] comp = eFile.getAllCat();
					for(int i = 0; i<comp.length; i++) {
						if(f4.getText().equals(comp[i])) {
							break;
						} else if(i == comp.length - 1 && !f4.getText().equals(comp[i])) {
							JOptionPane.showMessageDialog(null,
									"Invalid category!",
									"Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					int code = Integer.parseInt(fieldCode);
					try {					
						eFile.removeProduct(fieldCat, code);
					} catch(IllegalArgumentException illegal) {
						JOptionPane.showMessageDialog(null,
								"Invalid category",
								"Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					display.setText(getAllCatDisplay());
				}
			}
		});
		
		panel = new JPanel();
		GroupLayout glay = new GroupLayout(panel);
		glay.setAutoCreateContainerGaps(true);
		glay.setAutoCreateGaps(true);
		
		GroupLayout.ParallelGroup hgroup = glay.createParallelGroup(GroupLayout.Alignment.LEADING);
		hgroup.addComponent(lbl0);
		hgroup.addGroup(glay.createSequentialGroup())
			.addComponent(lbl1)
			.addComponent(f1);		
		hgroup.addGroup(glay.createSequentialGroup())
			.addComponent(lbl2)
			.addComponent(f2);
		hgroup.addGroup(glay.createSequentialGroup())
			.addComponent(lbl3)
			.addComponent(f3);
		hgroup.addGroup(glay.createSequentialGroup())
			.addComponent(lbl4)
			.addComponent(f4);
		hgroup.addGroup(glay.createSequentialGroup())
			.addComponent(lbl5)
			.addComponent(f5);
		glay.setHorizontalGroup(hgroup);		
		GroupLayout.SequentialGroup vgroup = glay.createSequentialGroup();
		vgroup.addComponent(lbl0);
		vgroup.addGroup(glay.createParallelGroup())
			.addComponent(lbl1)
			.addComponent(f1);
		vgroup.addGroup(glay.createParallelGroup())
			.addComponent(lbl2)
			.addComponent(f2);
		vgroup.addGroup(glay.createParallelGroup())
			.addComponent(lbl3)
			.addComponent(f3);
		vgroup.addGroup(glay.createParallelGroup())
			.addComponent(lbl4)
			.addComponent(f4);
		vgroup.addGroup(glay.createParallelGroup())
			.addComponent(lbl5)
			.addComponent(f5);
		glay.setVerticalGroup(vgroup);
		panel.setLayout(glay);
	
		
		JPanel panelBtn = new JPanel();
		GroupLayout btnLayout = new GroupLayout(panelBtn);
		btnLayout.linkSize(btnAdd, btnRemove);
		btnLayout.setAutoCreateGaps(true);
		btnLayout.setAutoCreateContainerGaps(true);
		
		GroupLayout.ParallelGroup hgroupB = btnLayout.createParallelGroup();
		hgroupB.addComponent(btnAdd);
		hgroupB.addComponent(btnRemove);
		btnLayout.setHorizontalGroup(hgroupB);	
		GroupLayout.SequentialGroup vgroupB = btnLayout.createSequentialGroup();
		vgroupB.addComponent(btnAdd);
		vgroupB.addComponent(btnRemove);
		btnLayout.setVerticalGroup(vgroupB);
		panelBtn.setLayout(btnLayout);
		
		GridBagLayout gridLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JPanel finalPanel = new JPanel();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		finalPanel.add(panel, c);
		c.gridx = 1;
		finalPanel.add(panelBtn, c);
		c.gridx = 2;
		c.fill = GridBagConstraints.REMAINDER;
		finalPanel.add(panelDisplay, c);
		finalPanel.setLayout(gridLayout);
		
		title = new JLabel("Stock");
		title.setFont(new Font("Bold", Font.BOLD, 26));
		GroupLayout finalLy = new GroupLayout(this);
		finalLy.setAutoCreateGaps(true);
		finalLy.setAutoCreateContainerGaps(true);
		GroupLayout.ParallelGroup hGroup = finalLy.createParallelGroup(GroupLayout.Alignment.CENTER);
		hGroup.addComponent(title);
		hGroup.addComponent(finalPanel);
		finalLy.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = finalLy.createSequentialGroup();
		vGroup.addComponent(title);
		vGroup.addComponent(finalPanel);
		finalLy.setVerticalGroup(vGroup);
		this.setLayout(finalLy);
		// Layout end
	}
	
	public static void main(String[] args) {
		Estoque teste = new Estoque();
		JFrame frame = new JFrame();
		teste.setAdmin();
		frame.setSize(900, 700);
		frame.setVisible(true);
		frame.add(teste);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}