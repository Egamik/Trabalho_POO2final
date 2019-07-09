package _TrabalhoFinal_POO2;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FirstTab extends JPanel{

	private String admin, func;
	private JTextArea display = new JTextArea();
	
	FirstTab(){
		super();
		this.admin = "\n\n\nStock\n"
				+"\t -To add products to the stock fill all fields and press add.\n"
				+"\t -To remove products fill the code and category fields and press 'remove'.\n\n"
				+"Employee\n"
				+"\t -To add employees fill all fields and press add\n"
				+"\t -To remove emplyees fill the role and code fields and press 'remove'.";
		
		this.func = "\n\n\nStock\n"
				+"\t -Can see available stock.\n\n"
				+"Cashier\n"
				+"\t -To sell a product fill all fields and press 'sell'.";
		
		this.display.setRows(30);
		this.display.setColumns(30);
		this.display.setEditable(false);
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.add(display, BorderLayout.CENTER);
	}
	
	void setAdmin() {
		display.setText(this.admin);
	}
	
	void setFunc() {
		display.setText(this.func);
	}
}
