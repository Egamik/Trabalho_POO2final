package _TrabalhoFinal_POO2;

import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;


public class Main extends JFrame{

	private FirstTab firstTab = new FirstTab();;
	private FuncionarioTab ftab;
	private Estoque etab;
	private Caixa ctab;
	private JTabbedPane mainPanel;
	private JLabel clock;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem logout, quit;
	private static String password = "admin";
	private String code, funcCat; 
	public Clock clk;
	
	public Main(){
		super("Store");
		// Layout base
		this.menu = new JMenu("Menu");
		this.menu.setMnemonic(KeyEvent.VK_M);
		this.logout = new JMenuItem("Log out");
		this.logout.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_4, ActionEvent.ALT_MASK));
		this.logout.getAccessibleContext().setAccessibleDescription("Log out from session");
		this.quit = new JMenuItem("Quit program");
		this.quit.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		this.quit.getAccessibleContext().setAccessibleDescription("Quit application");
				
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				launch();
			}		
		});
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}					
		});
				
		this.menu.add(logout);
		this.menu.addSeparator();
		this.menu.add(quit);
				
		this.menuBar = new JMenuBar();
		this.menuBar.add(menu);
		this.menuBar.add(this.clock = new JLabel(""));		
		this.setJMenuBar(menuBar);	
				
		this.mainPanel = new JTabbedPane();										
				
		this.clk = new Clock();
		// Fim do layout basico
	}
	
	private void setAdmin() {	
		etab.setAdmin();
		ftab.setAdmin();
		firstTab.setAdmin();
		mainPanel.addTab("Welcome!", firstTab);
		mainPanel.addTab("Stock", etab);		
		mainPanel.addTab("Employee", ftab);
		this.add(mainPanel);
	}
	
	private void setFunc() {
		/*this.remove(mainPanel);
		this.mainPanel = null;
		this.mainPanel = new JTabbedPane();*/
		
		etab.setFunc();
		ftab.setFunc();
		firstTab.setFunc();
		mainPanel.addTab("Welcome!", firstTab);
		mainPanel.addTab("Stock", etab);
		mainPanel.addTab("Cahsier", ctab);
		
		this.add(mainPanel);
	}
	
	// Launcher do programa
	void launch() {
		this.etab = new Estoque();				
		this.ctab = new Caixa();
		this.ftab = new FuncionarioTab();
		
		this.mainPanel.removeAll();
		this.mainPanel.revalidate();
		String[] options = {"Admin", "Funcionário"};		
		int op = JOptionPane.showOptionDialog(this,
				"Enter session",
				"Launcher",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				null);
		// Mensagem para admin
		if(op == 0) {
			JPanel p = new JPanel();
			JLabel lbl = new JLabel("Enter password: ");
			JPasswordField pass = new JPasswordField(10);
			p.add(lbl);
			p.add(pass);
			String[] ops = new String[] {"OK", "Cancel"};
			int option = JOptionPane.showOptionDialog(this,
					p,
					"Log in",
					JOptionPane.NO_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					ops,
					ops[0]);
			// Checa password de admin
			if(option == 0) {
				char[] pw = pass.getPassword();
				String ppw = "";
				for(int i=0; i<pw.length; i++) {
					ppw += pw[i];
				}
				if(password.equals(ppw)) {
					setAdmin();
					return;
				} else {
					JOptionPane.showMessageDialog(this,
							"Invalid password!",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					launch();
					return;
				}
			} else {
				System.exit(1);
			}
			
		} else if(op == 1){
			this.code = JOptionPane.showInputDialog("Enter your code: ");
			//Modifica panels com acesso funcionario
			if(code == null || !code.matches("[0-9]+") || code.equals("")) {
				// Mensagem de erro
				JOptionPane.showMessageDialog(this,
						"Invalid code!",
						"Alert",
						JOptionPane.ERROR_MESSAGE);
				launch();
				return;
			}
			this.funcCat = JOptionPane.showInputDialog("Enter your role: ");
			if(funcCat == null || funcCat.equals("")) {
				JOptionPane.showMessageDialog(this,
						"Invalid role!",
						"Alert",
						JOptionPane.ERROR_MESSAGE);
				launch();
				return;
			} else {
				int cd = Integer.parseInt(code);
				ftab.setSession(cd, funcCat);
			}
			setFunc();
		}
		else {
			System.exit(1);
		}
	}
	
	void setClock(String clk) {
		this.clock.setText(clk);
	}
	
	String getClock() {
		return this.clock.getText();
	}
		
	private class Clock{
		public int day, month, year, s, m, h;
		public String x;
		Thread th = new Thread() {
			@Override
			public void run() {
				try {
					for(;;) {
						Calendar c = new GregorianCalendar();
						day = c.get(Calendar.DAY_OF_MONTH);
						month = c.get(Calendar.MONTH);
						year = c.get(Calendar.YEAR);
						s = c.get(Calendar.SECOND);
						m = c.get(Calendar.MINUTE);
						h = c.get(Calendar.HOUR);
						int am_pm = c.get(Calendar.AM_PM);
						
						x = "";
						if(am_pm == 1) {
							x = "PM";
						} else {
							x = "AM";
						}
						month = month + 1;
						setClock(""+h+":"+m+":"+s+" "+x+"  "+day+"/"+month+"/"+year);
						sleep(1000);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		public void run() {
			th.run();
		}
		public String getTime() {
			return Integer.toString(h) +":"+Integer.toString(m)+":"+Integer.toString(s)+
					x+"\t" +Integer.toString(day)+"/"+Integer.toString(month)+"/"+Integer.toString(year);
		}
	}
	
	public static void main(String[]args) {
		Main teste = new Main();
		teste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		teste.setVisible(true);
		teste.setSize(900, 700);
		teste.launch();
		teste.clk.run();
	}
}