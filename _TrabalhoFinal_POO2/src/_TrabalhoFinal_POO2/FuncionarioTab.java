package _TrabalhoFinal_POO2;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.NoSuchElementException;

import javax.swing.*;


public class FuncionarioTab extends JPanel{
    
    private JTextArea display;
    private JLabel lbl0, lblName, lblCode, lblRole, lblSal;
    private JTextField f1, f2, f3, f4;
    private JPanel panelLbl, panelBtn, outPanel, finalPanel;
    private FuncionarioFile efile;
    
    public String fieldName, fieldRole, fieldSal, fieldCode;
    
    public FuncionarioTab() {
    	super();
    	efile = new FuncionarioFile();
    }
    public void setSession(int code, String role) {    	
    	try {
    		Funcionario f = efile.getFunc(role, code);
    		this.f1.setText(f.getName());
    		this.f2.setText(f.getRole());
    		this.f3.setText(Double.toString(f.getSal()));
    		this.f4.setText(Integer.toString(f.getCode()));	
    	} catch(NoSuchElementException e) {
    		System.err.printf("Erro session");
    	}
    	
    }
    
    private String getAllRoleDisplay() {
    	HashMap<String, Funcionario[]> map = this.efile.getMap();
    	String[] keys = this.efile.getAllRole();
    	String ret = "";
    	for(int i = 0; i<keys.length; i++) {
    		Funcionario[] f = map.get(keys[i]);
    		for(int j = 0; j<f.length; j++) {
    			if(f[j] != null) {
    				ret += f[j].getName()+"\t"+f[j].getRole()+"\t"+f[j].getSal()
    						+"\t"+f[j].getCode()+"\n";
    			}
    		}
    	}
    	return ret;
    }
    
    public final void setFunc(){ 
        
        lbl0 = new JLabel("Your information");
        this.lblName = new JLabel("Name: ");
        this.lblCode = new JLabel("Code: ");
        this.lblRole = new JLabel("Role: ");
        this.lblSal = new JLabel("Salary: ");
        this.f1 = new JTextField(20);
        this.f2 = new JTextField(20);
        this.f3 = new JTextField(20);
        this.f4 = new JTextField(20);
        f1.setEditable(false);
        f2.setEditable(false);
        f3.setEditable(false);
        f4.setEditable(false);
        this.display = new JTextArea();
        display.setRows(30);
        display.setColumns(30);
        display.setEditable(false);
        
        JPanel panel = new JPanel();
        GroupLayout groupl = new GroupLayout(panel);
        panel.setLayout(groupl);
        groupl.setAutoCreateGaps(true);
        groupl.setAutoCreateContainerGaps(true);
        GroupLayout.ParallelGroup hg = groupl.createParallelGroup(GroupLayout.Alignment.LEADING);
        hg.addComponent(lbl0);
		hg.addGroup(groupl.createSequentialGroup())
			.addComponent(lblName)
			.addComponent(f1);		
		hg.addGroup(groupl.createSequentialGroup())
			.addComponent(lblRole)
			.addComponent(f2);
		hg.addGroup(groupl.createSequentialGroup())
			.addComponent(lblSal)
			.addComponent(f3);
		hg.addGroup(groupl.createSequentialGroup())
			.addComponent(lblCode)
			.addComponent(f4);
		groupl.setHorizontalGroup(hg);
        GroupLayout.SequentialGroup vg = groupl.createSequentialGroup();
        vg.addComponent(lbl0);
		vg.addGroup(groupl.createParallelGroup())
			.addComponent(lblName)
			.addComponent(f1);
		vg.addGroup(groupl.createParallelGroup())
			.addComponent(lblRole)
			.addComponent(f2);
		vg.addGroup(groupl.createParallelGroup())
			.addComponent(lblSal)
			.addComponent(f3);
		vg.addGroup(groupl.createParallelGroup())
			.addComponent(lblCode)
			.addComponent(f4);
		groupl.setVerticalGroup(vg);
        
        
        GroupLayout gl = new GroupLayout(this);
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        GroupLayout.ParallelGroup hgroup = gl.createParallelGroup(GroupLayout.Alignment.CENTER);
        	hgroup.addComponent(panel);
        	hgroup.addComponent(display);
        gl.setHorizontalGroup(hgroup);
        GroupLayout.SequentialGroup vgroup = gl.createSequentialGroup();
        	vgroup.addComponent(panel);
        	vgroup.addComponent(display);
        gl.setVerticalGroup(vgroup);
        this.setLayout(gl);
      
    }
    
    public final void setAdmin() {
        display = new JTextArea();
        display.setEditable(false);
        display.setRows(30);
        display.setColumns(30);
        
        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");
        btnAdd.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		fieldName = f1.getText();
        		fieldRole = f2.getText();
        		fieldSal = f3.getText();
        		fieldCode = f4.getText();
        		if(!fieldSal.matches("[0-9]+")||!fieldCode.matches("[0-9]+")) {
        			JOptionPane.showMessageDialog(null,
        					"Use only numbers at salary and code fields",
        					"Error",
        					JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		double sal = Double.parseDouble(fieldSal);
        		int code = Integer.parseInt(fieldCode);
        		Funcionario f = new Funcionario(fieldName, fieldRole, sal, code);
        		efile.addFunc(f);
        		display.setText(getAllRoleDisplay());
        		fieldName = null;
        		fieldRole = null;
        		fieldSal = null;
        		fieldCode = null;
        	}
        });
        btnRemove.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		fieldRole = f2.getText();
        		fieldCode = f4.getText();
        		if(!fieldCode.matches("[0-9]+")) {
        			JOptionPane.showMessageDialog(null,
        					"Use only numbers at code field",
        					"Error",
        					JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		int code = Integer.parseInt(fieldCode);
        		try {
        			efile.removeFunc(fieldRole, code);
        		} catch(IllegalArgumentException a) {
        			JOptionPane.showMessageDialog(null,
        					"Employee not found",
        					"Error",
        					JOptionPane.ERROR_MESSAGE);
        		}
        		display.setText(getAllRoleDisplay());
        	}
        });
        
        lbl0 = new JLabel("Edit employee list");
        lblCode = new JLabel("Code:");
        lblName = new JLabel("Name:");
        lblRole = new JLabel("Role:");
        lblSal = new JLabel("Salary:");
        
        f1 = new JTextField(30);
        f2 = new JTextField(30);
        f3 = new JTextField(30);
        f4 = new JTextField(30);
        
        // Panel com labels e fields
        panelLbl = new JPanel();
        GroupLayout layout = new GroupLayout(panelLbl);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        panelLbl.setLayout(layout); 
        
        GroupLayout.ParallelGroup hgroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        	hgroup.addComponent(lbl0);
        	hgroup.addComponent(lblName);
        	hgroup.addComponent(f1);
        	hgroup.addComponent(lblRole);
        	hgroup.addComponent(f2);
        	hgroup.addComponent(lblSal);
        	hgroup.addComponent(f3);
        	hgroup.addComponent(lblCode);
        	hgroup.addComponent(f4);
        layout.setHorizontalGroup(hgroup);       
        GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
        	vgroup.addComponent(lbl0);
        	vgroup.addComponent(lblName);
        	vgroup.addComponent(f1);
        	vgroup.addComponent(lblRole);
        	vgroup.addComponent(f2);
        	vgroup.addComponent(lblSal);
        	vgroup.addComponent(f3);
        	vgroup.addComponent(lblCode);
        	vgroup.addComponent(f4);
        layout.setVerticalGroup(vgroup);
        
        // Panel com botoes
		panelBtn = new JPanel();
		GroupLayout btnLayout = new GroupLayout(panelBtn);
		btnLayout.linkSize(btnAdd, btnRemove);
		btnLayout.setAutoCreateGaps(true);
		btnLayout.setAutoCreateContainerGaps(true);	
		panelBtn.setLayout(btnLayout);
		
		GroupLayout.ParallelGroup hgroupB = btnLayout.createParallelGroup();
			hgroupB.addComponent(btnAdd);
			hgroupB.addComponent(btnRemove);
		btnLayout.setHorizontalGroup(hgroupB);		
		GroupLayout.SequentialGroup vgroupB = btnLayout.createSequentialGroup();
			vgroupB.addComponent(btnAdd);
			vgroupB.addComponent(btnRemove);
		btnLayout.setVerticalGroup(vgroupB);		
        
		// Junta os 3 panels
        outPanel = new JPanel();
        GridBagLayout gridL = new GridBagLayout();
        GridBagConstraints cc = new GridBagConstraints();
        cc.fill = GridBagConstraints.BOTH;
        cc.gridx = 0;
        cc.gridy = 0;
        outPanel.add(panelLbl, cc);
        cc.gridx = 1;
        outPanel.add(panelBtn, cc);
        cc.gridx = 2;
        cc.fill = GridBagConstraints.REMAINDER;
        outPanel.add(display, cc);
        outPanel.setLayout(gridL);

        // Junta titulo
        JLabel title = new JLabel("Employees");
        Font f = new Font("Bold", Font.BOLD, 26);
        title.setFont(f);
        
        finalPanel = new JPanel();        
        GridBagLayout finalG = new GridBagLayout();
        GridBagConstraints cg = new GridBagConstraints();
        finalPanel.setLayout(finalG);
        cg.gridx = 0;
        cg.gridy = 0;
        finalPanel.add(title, cg);
        cg.gridy = 1;
        cg.fill = GridBagConstraints.REMAINDER;
        finalPanel.add(outPanel, cg);
        
        display.setText(getAllRoleDisplay());
        this.add(finalPanel);
    }
    
    public static void main(String[] args){
        FuncionarioTab teste = new FuncionarioTab();
        JFrame frame = new JFrame();
        teste.setAdmin();
		frame.setSize(900, 700);
		frame.setVisible(true);
		frame.add(teste);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}