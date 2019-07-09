package _TrabalhoFinal_POO2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class Caixa extends JPanel{

	private JTextField f1, f2;	//Field para entrada de prod a vender
	private JTextArea sessionSell;		//Display de extrato de vendas da sessao atual
	private JButton sellBtn;
	private JPanel intern, labels;				//Panel que une sellProd e sellBtn
	private JLabel lbl0, lbl1, lbl2, lblCaixa;
	//private Funcionario user_in;
	private EstoqueFile efile;

	public String catField, codeField;
	
	public Caixa() {

		super();
		this.efile = new EstoqueFile();
		
		this.sessionSell = new JTextArea();
		this.sessionSell.setEditable(false);
		this.sessionSell.setRows(30);
		this.sessionSell.setColumns(30);
		
		this.sellBtn = new JButton("Sell");
		sellBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String c = f1.getText();
				String cod = f2.getText();
				if(cod.matches("[0-9]+") && c!=null) {
					int code = Integer.parseInt(cod); 
					try {
						efile.removeProduct(c, code);
					}catch(IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null,
								"Invalid category",
								"Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					try {
						Produto p = efile.getProduct(c, code);
						sessionSell.setText(sessionSell.getText()+"\n"
								+p.getName()+"\t"+p.getArrival()+"\t"
								+p.getPrice()+"\t"+p.getCode()+"\t"+p.getCat());
					} catch(NoSuchElementException a) {
						JOptionPane.showMessageDialog(null,
								"Product not found!",
								"Error",
								JOptionPane.ERROR_MESSAGE);
					}
					return;
				} else {
					JOptionPane.showMessageDialog(null,
							"Use only numbers at the code field!",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	
		this.lbl0 = new JLabel("Product");
		this.lbl1 = new JLabel("Category:");
		this.lbl2 = new JLabel("Code:");
		
		this.f1 = new JTextField(20);
		this.f2 = new JTextField(20);				

		// Layout Labels e fields
		this.labels = new JPanel();
		GroupLayout gl = new GroupLayout(labels);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);
		labels.setLayout(gl);		
		
		GroupLayout.ParallelGroup hggroup = gl.createParallelGroup(GroupLayout.Alignment.LEADING);
		hggroup.addComponent(lbl0);
		hggroup.addGroup(gl.createSequentialGroup()
			.addComponent(lbl1))
			.addComponent(f1);
		hggroup.addGroup(gl.createSequentialGroup()
			.addComponent(lbl2))
			.addComponent(f2);
		gl.setHorizontalGroup(hggroup);		
		GroupLayout.SequentialGroup vggroup = gl.createSequentialGroup();
		vggroup.addComponent(lbl0);
		vggroup.addGroup(gl.createParallelGroup())
			.addComponent(lbl1)
			.addComponent(f1);
		vggroup.addGroup(gl.createParallelGroup())
			.addComponent(lbl2)
			.addComponent(f2);
		gl.setVerticalGroup(vggroup);
		
		// Junta labels fields e display		
		this.intern = new JPanel();
		GridBagLayout gridL = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		intern.add(labels, c);
		c.gridx = 1;
		intern.add(sellBtn, c);
		c.fill = GridBagConstraints.REMAINDER;
		c.gridx = 2;
		intern.add(sessionSell, c);
		intern.setLayout(gridL);
		
		// Layout que junta tudo com titulo
		this.lblCaixa = new JLabel("Cashier");
		Font font1 = new Font("Bold", Font.BOLD, 26);
		lblCaixa.setFont(font1);		

		GroupLayout outLy = new GroupLayout(this);
		outLy.setAutoCreateGaps(true);
		outLy.setAutoCreateContainerGaps(true);	
		GroupLayout.ParallelGroup hGroup = outLy.createParallelGroup(GroupLayout.Alignment.CENTER);
			hGroup.addComponent(lblCaixa);
			hGroup.addComponent(intern);
		outLy.setHorizontalGroup(hGroup);
		GroupLayout.SequentialGroup vGroup = outLy.createSequentialGroup();
			vGroup.addComponent(lblCaixa);
			vGroup.addComponent(intern);
		outLy.setVerticalGroup(vGroup);
		this.setLayout(outLy);
		
	}
	
	public void sell(Produto p) {
		
	}
	
	public static void main(String[] args) {
		Caixa c = new Caixa();
		JFrame f = new JFrame();
		f.setSize(900, 700);
		f.setVisible(true);
		f.add(c);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
