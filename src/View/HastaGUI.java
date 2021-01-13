package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.DBConnection;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private Clinic clinic = new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectDoctorID = 0;
	private String selectDoctorName = null;
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public HastaGUI(Hasta hasta) throws SQLException {

		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];

		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doktor";
		colAppoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];
		for (int i = 0; i < appoint.getHastaList(hasta.getId()).size(); i++) {
            appointData[0] = appoint.getHastaList(hasta.getId()).get(i).getId();   
            appointData[1] = appoint.getHastaList(hasta.getId()).get(i).getDoctorName();   
            appointData[2] = appoint.getHastaList(hasta.getId()).get(i).getAppDate();  
            appointModel.addRow(appointData);
		}

		setResizable(false);
		setTitle("Hastane Y�netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel label = new JLabel("Ho�geldiniz, Say�n " + hasta.getName());
		label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		label.setBounds(10, 11, 247, 27);
		w_pane.add(label);

		JButton button = new JButton("��k�� Yap");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		button.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 10));
		button.setBounds(623, 11, 111, 27);
		w_pane.add(button);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 75, 713, 385);
		w_pane.add(w_tab);

		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tab.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 30, 280, 316);
		w_appointment.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		JLabel label_1 = new JLabel("Doktor Listesi");
		label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		label_1.setBounds(10, 1, 247, 26);
		w_appointment.add(label_1);

		JLabel label_2 = new JLabel("Poliklinik Ad\u0131");
		label_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		label_2.setBounds(300, 30, 151, 26);
		w_appointment.add(label_2);

		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(300, 59, 150, 35);
		select_clinic.addItem("--Poliklinik Se�--");
		for (int i = 0; i < clinic.getList().size(); i++) {
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}
			}
		});
		w_appointment.add(select_clinic);

		JLabel lblDoktorSe = new JLabel("Doktor Se\u00E7");
		lblDoktorSe.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblDoktorSe.setBounds(300, 105, 151, 21);
		w_appointment.add(lblDoktorSe);

		JButton btn_selDoctor = new JButton("Se\u00E7");
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if (row >= 0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					table_whour.setModel(whourModel);
					selectDoctorID = id;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
					// System.out.println(selectDoctorID + " - " + selectDoctorName);

				} else {
					Helper.showMsg("L�tfen bir doktor se�iniz!");
				}
			}
		});
		btn_selDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		btn_selDoctor.setBounds(300, 127, 151, 29);
		w_appointment.add(btn_selDoctor);

		JLabel label_3 = new JLabel("Doktor Listesi");
		label_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		label_3.setBounds(458, 1, 240, 26);
		w_appointment.add(label_3);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(458, 30, 240, 316);
		w_appointment.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);

		JLabel lblRandevuAl = new JLabel("Randevu");
		lblRandevuAl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblRandevuAl.setBounds(300, 167, 151, 21);
		w_appointment.add(lblRandevuAl);

		JButton btn_addAppoint = new JButton("Randevu Al");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, hasta.getId(), selectDoctorName,
								hasta.getName(), date);
						if (control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							updateAppointModel(hasta.getId());
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("L�tfen ge�erli bir tarih giriniz!");
				}
			}
		});
		btn_addAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		btn_addAppoint.setBounds(300, 189, 151, 29);
		w_appointment.add(btn_addAppoint);

		JPanel w_appoint = new JPanel();
		w_tab.addTab("Randevular�m", null, w_appoint, null);
		w_appoint.setLayout(null);

		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 525, 335);
		w_appoint.add(w_scrollAppoint);

		table_appoint = new JTable(appointModel);
		w_scrollAppoint.setViewportView(table_appoint);
		


		
		//silme i�lemi ba�l�yor
				JButton btnNewButton = new JButton("Randevu Sil");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
						int selRow=table_appoint.getSelectedRow();
						if(selRow>=0) {
							
							if(Helper.confirm("sure")) {
							
							String selectRow=table_appoint.getModel().getValueAt(selRow,0).toString();
							
							int selID=Integer.parseInt(selectRow);
							
							String tarih=table_appoint.getModel().getValueAt(selRow,2).toString();
							
							 
							
							try {
								boolean control=hasta.deleteRandevu(selID);
								if(control) {
									Helper.showMsg("success");
									updateAppointModel(hasta.getId());
									hasta.updateWhourStatusA(selectDoctorID, tarih);
								}
								else {
									Helper.showMsg("error");
								}
							} catch (SQLException e11) {
								 
								e11.printStackTrace();
							}
						}
							
						}
							else {
							Helper.showMsg("L�tfen Bir Tarih Se�iniz!");
						}
						
						
					}
					
				});
				
				btnNewButton.setBounds(555, 15, 145, 23);
				w_appoint.add(btnNewButton);
				
				 
		 // bitiyor
		
		
		
		
		
		
		
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(4);
	}

	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
	
	public void updateAppointModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < appoint.getHastaList(hasta_id).size(); i++) {
            appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();   
            appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorName();   
            appointData[2] = appoint.getHastaList(hasta_id).get(i).getAppDate();  
            appointModel.addRow(appointData);
		}
	}
}
