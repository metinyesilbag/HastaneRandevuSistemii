package View;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doctorTc;
	private JPasswordField fld_doctorPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_hastaPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Hastane Y\u00F6netim Sistemine Ho\u015Fgeldiniz");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblNewLabel_2.setBounds(94, 53, 306, 27);
		w_pane.add(lblNewLabel_2);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(0, 146, 494, 225);
		w_pane.add(w_tabpane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta Giriþi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("T.C Numaran\u0131z:");
		lblNewLabel.setBounds(36, 25, 166, 30);
		w_hastaLogin.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		
		JLabel lblifre = new JLabel("\u015Eifre:");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		lblifre.setBounds(36, 66, 166, 30);
		w_hastaLogin.add(lblifre);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 18));
		fld_hastaTc.setBounds(226, 25, 218, 30);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		
		JButton btn_register = new JButton("Kayýt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_register.setBounds(36, 127, 190, 40);
		w_hastaLogin.add(btn_register);
		
		JButton btnHastaLogin = new JButton("Giri\u015F Yap");
		btnHastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMsg("fill");
				}else {
				    boolean key = true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
					    ResultSet rs = st.executeQuery("SELECT * FROM user");					    
					    while (rs.next()) {
					    	if(fld_hastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password"))) {
					    		if(rs.getString("type").equals("hasta")) {
					    			Hasta hasta = new Hasta();
					    			hasta.setId(rs.getInt("id"));
					    			hasta.setPassword("password");
					    			hasta.setTcno(rs.getString("tcno"));
						    		hasta.setName(rs.getString("name"));
						    		hasta.setType(rs.getString("type"));
						    		HastaGUI hGUI = new HastaGUI(hasta);
						    		hGUI.setVisible(true);
						    		dispose();
						    		key = false;
					    		}				    			
					    	}
					    }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(key) {
						Helper.showMsg("Böyle bir hasta bulunamadý lütfen kayýt olunuz !");
					}
				}
			}
		});
		btnHastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnHastaLogin.setBounds(254, 127, 190, 40);
		w_hastaLogin.add(btnHastaLogin);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(226, 66, 218, 30);
		w_hastaLogin.add(fld_hastaPass);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(76, 153, 46, 14);
		w_hastaLogin.add(lblNewLabel_3);
		
		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Giriþi", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);
		
		JPanel w_hastaLogin_1 = new JPanel();
		w_hastaLogin_1.setLayout(null);
		w_hastaLogin_1.setBackground(Color.WHITE);
		w_hastaLogin_1.setBounds(0, 0, 489, 197);
		w_doctorLogin.add(w_hastaLogin_1);
		
		JLabel lblNewLabel_1 = new JLabel("T.C Numaran\u0131z:");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(36, 25, 166, 30);
		w_hastaLogin_1.add(lblNewLabel_1);
		
		JLabel lblifre_1 = new JLabel("\u015Eifre:");
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		lblifre_1.setBounds(36, 70, 166, 30);
		w_hastaLogin_1.add(lblifre_1);
		
		fld_doctorTc = new JTextField();
		fld_doctorTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 18));
		fld_doctorTc.setColumns(10);
		fld_doctorTc.setBounds(226, 25, 218, 30);
		w_hastaLogin_1.add(fld_doctorTc);
		
		JButton btn_DoktorLogin = new JButton("Giri\u015F Yap");
		btn_DoktorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_doctorTc.getText().length() == 0 || fld_doctorPass.getText().length() == 0) {
				    Helper.showMsg("fill");
				}else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
					    ResultSet rs = st.executeQuery("SELECT * FROM user");
					    while (rs.next()) {
					    	if(fld_doctorTc.getText().equals(rs.getString("tcno")) && fld_doctorPass.getText().equals(rs.getString("password"))) {
					    		if(rs.getString("type").equals("bashekim")) {
					    			Bashekim bhekim = new Bashekim();
						    		bhekim.setId(rs.getInt("id"));
						    		bhekim.setPassword("password");
						    		bhekim.setTcno(rs.getString("tcno"));
						    		bhekim.setName(rs.getString("name"));
						    		bhekim.setType(rs.getString("type"));
						    		BashekimGUI bGUI = new BashekimGUI(bhekim);
						    		bGUI.setVisible(true);
						    		dispose();
					    		}
					    		
					    		if(rs.getString("type").equals("doktor")) {
					    			Doctor doctor = new Doctor();
					    			doctor.setId(rs.getInt("id"));
					    			doctor.setPassword("password");
					    			doctor.setTcno(rs.getString("tcno"));
					    			doctor.setName(rs.getString("name"));
					    			doctor.setType(rs.getString("type"));
					    			DoctorGUI dGUI = new DoctorGUI(doctor);
					    			dGUI.setVisible(true);
					    			dispose();
					    		}
					    	}
					    }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_DoktorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_DoktorLogin.setBounds(36, 128, 408, 35);
		w_hastaLogin_1.add(btn_DoktorLogin);
		
		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(226, 70, 218, 30);
		w_hastaLogin_1.add(fld_doctorPass);
	}
}
