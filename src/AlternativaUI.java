import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AlternativaUI {

	private JFrame frame;
	private JTextField txtNumeAngajat;
	private JTextField txtPrenumeAngajat;
	private JTextField txtIdAngajat;
	private JTextField txtIdDepartament;
	private JTextField txtCnp;
	private JTextField txtIdConcediu;
	private JTextField txtDataIncepere;
	private JTextField txtDataFinalizare;
	private JTextField txtIdAngajatRequest;
	private JTextField txtIdAngajatCautare;
	private JTextField txtZiCautare;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlternativaUI window = new AlternativaUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AlternativaUI() {
		initialize();
		Connect();
		angajat_table_load();
	}
	
	
	//aici conectam baza de date
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/concediidb", "root", "parola");
		} catch (ClassNotFoundException ex) {
			
		} catch (SQLException ex) {
			
		}
	}
	
	//metoda pentru afisarea tabelului de concedii
	
	public void concedii_table_load() {
		try {
			pst = con.prepareStatement("select * from concedii");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//metoda pentru update concediu
	
	public void update_concediu_table_load(Date data_inceput, Date data_sfarsit, int id_concediu) throws ParseException {
		try {

			pst = con.prepareStatement("update concedii set data_incepere = ?, data_finalizare = ? where id_concediu = ?");			
			pst.setDate(1,  data_inceput);
			pst.setDate(2,  data_sfarsit);
			pst.setInt(3, id_concediu);
			pst.executeUpdate();
			//table.setModel(DbUtils.resultSetToTableModel(rs));
			concedii_table_load();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	//metoda de stergere concediu

	public void delete_concediu_table_load(int id_concediu) {
		try {
			pst = con.prepareStatement("delete from concedii where id_concediu = ?");
			pst.setInt(1, id_concediu);

			pst.executeUpdate();
			//table.setModel(DbUtils.resultSetToTableModel(rs));


			concedii_table_load();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	//aici avem functia cu care afisam tabelul de angajati
	
	public void angajat_table_load() {
		try {
			pst = con.prepareStatement("select * from angajati");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//metoda prin care afisam angajatul dupa cnp
	
	public void cautare_cnp_table_load(String cnp) {
		try {
			pst = con.prepareStatement("select id_angajat, nume, prenume, id_departament from angajati where CNP = "+cnp);
			//pst.setString(1, cnp);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	
	//metoda de update pentru angajati
	
	public void update_angajat_table_load(String nume, String prenume, int id_departament, String cnp) {
		try {
			
			pst = con.prepareStatement("update angajati set nume = ?, prenume = ?, id_departament = ? where cnp = ?");
			
			pst.setString(1, nume);
			pst.setString(2, prenume);
			pst.setInt(3, id_departament);
			pst.setString(4, cnp);
			pst.executeUpdate();
			//table.setModel(DbUtils.resultSetToTableModel(rs));
			cautare_cnp_table_load(cnp);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	//metoda de sters angajati
	
	public void delete_angajat_table_load(int id_angajat) {
		try {
			//pst = con.prepareStatement("update angajati set nume="+nume+", prenume="+prenume+", id_departament="+id_departament+" where cnp="+cnp);
			pst = con.prepareStatement("delete from angajati where id_angajat = ?");
			//pst.setString(1, cnp);
			pst.setInt(1, id_angajat);
			
			pst.executeUpdate();
			//table.setModel(DbUtils.resultSetToTableModel(rs));
			//cautare_cnp_table_load(cnp);
			
			angajat_table_load();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	//cautarea unui concediu dupa o zi si id-ul unui angajat
	public void cautare_zi_angajat_table_load(Date zi_concediu, int id_angajat){
		try{
			pst = con.prepareStatement("select * from concedii where data_incepere <= ? and data_finalizare >= ? and id_angajat = ?");
			pst.setDate(1,zi_concediu);
			pst.setDate(2,zi_concediu);
			pst.setInt(3,id_angajat);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void cautare_concediu_dupa_id_angajat_table_load(int id_angajat){
		try{
			pst = con.prepareStatement("select * from concedii where id_angajat = ?");

			pst.setInt(1,id_angajat);

			rs = pst.executeQuery();

			table.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	//metoda pentru afisat lista angajatilor care au concediu intr-o anumita perioada
	
	public void cautare_angajati_dupa_perioada_table_load(Date zi_inceput, Date zi_sfarsit){
		try{
			//pst = con.prepareStatement("select id_angajat from concedii where data_incepere <= ? and data_finalizare >= ?");
			pst = con.prepareStatement("select nume, prenume, id_angajat from angajati join concedii using (id_angajat) where data_incepere <= ? and data_finalizare >= ?");
			pst.setDate(1,zi_inceput);
			pst.setDate(2,zi_sfarsit);
			
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.setBounds(100, 100, 888, 736);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Gestiune concedii & angajati");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(235, 11, 458, 46);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registru", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 68, 382, 296);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		
		JLabel lblNewLabel_1 = new JLabel("Nume");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 38, 42, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Prenume");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(10, 63, 54, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("ID Angajat\r\n");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_1.setBounds(10, 88, 67, 14);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("ID Departament");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_2.setBounds(10, 113, 99, 14);
		panel.add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel_1_1_3 = new JLabel("CNP\r\n");
		lblNewLabel_1_1_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_3.setBounds(10, 138, 30, 14);
		panel.add(lblNewLabel_1_1_3);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 163, 362, 7);
		panel.add(separator);
		
		JLabel lblNewLabel_1_1_4 = new JLabel("ID Concediu");
		lblNewLabel_1_1_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_4.setBounds(10, 183, 99, 14);
		panel.add(lblNewLabel_1_1_4);
		
		JLabel lblNewLabel_1_1_5 = new JLabel("Data incepere");
		lblNewLabel_1_1_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_5.setBounds(10, 208, 99, 14);
		panel.add(lblNewLabel_1_1_5);
		
		JLabel lblNewLabel_1_1_6 = new JLabel("Data finalizare");
		lblNewLabel_1_1_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_6.setBounds(10, 233, 99, 14);
		panel.add(lblNewLabel_1_1_6);
		
		JLabel lblNewLabel_1_1_7 = new JLabel("ID Angajat");
		lblNewLabel_1_1_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_7.setBounds(10, 258, 99, 14);
		panel.add(lblNewLabel_1_1_7);
		
		txtNumeAngajat = new JTextField();
		txtNumeAngajat.setBounds(122, 36, 240, 20);
		panel.add(txtNumeAngajat);
		txtNumeAngajat.setColumns(10);
		
		txtPrenumeAngajat = new JTextField();
		txtPrenumeAngajat.setColumns(10);
		txtPrenumeAngajat.setBounds(122, 61, 240, 20);
		panel.add(txtPrenumeAngajat);
		
		txtIdAngajat = new JTextField();
		txtIdAngajat.setColumns(10);
		txtIdAngajat.setBounds(122, 86, 240, 20);
		panel.add(txtIdAngajat);
		
		txtIdDepartament = new JTextField();
		txtIdDepartament.setColumns(10);
		txtIdDepartament.setBounds(122, 111, 240, 20);
		panel.add(txtIdDepartament);
		
		txtCnp = new JTextField();
		txtCnp.setColumns(10);
		txtCnp.setBounds(122, 136, 240, 20);
		panel.add(txtCnp);
		
		txtIdConcediu = new JTextField();
		txtIdConcediu.setColumns(10);
		txtIdConcediu.setBounds(119, 183, 240, 20);
		panel.add(txtIdConcediu);
		
		txtDataIncepere = new JTextField();
		txtDataIncepere.setColumns(10);
		txtDataIncepere.setBounds(119, 206, 240, 20);
		panel.add(txtDataIncepere);
		
		txtDataFinalizare = new JTextField();
		txtDataFinalizare.setColumns(10);
		txtDataFinalizare.setBounds(119, 231, 240, 20);
		panel.add(txtDataFinalizare);
		
		txtIdAngajatRequest = new JTextField();
		txtIdAngajatRequest.setColumns(10);
		txtIdAngajatRequest.setBounds(119, 256, 240, 20);
		panel.add(txtIdAngajatRequest);
		
		JButton saveButton = new JButton("Insert angajat");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Angajat a = new Angajat();
				
				a.setNume(txtNumeAngajat.getText());
				a.setPrenume(txtPrenumeAngajat.getText());
				a.setId_angajat(Integer.parseInt(txtIdAngajat.getText()));
				a.setId_departament(Integer.parseInt(txtIdDepartament.getText()));
				a.setCNP(txtCnp.getText());
				
				try {
					pst = con.prepareStatement("INSERT INTO angajati (id_angajat, prenume, nume, id_departament, CNP) values (?, ?, ?, ?, ?)");
					pst.setInt(1, a.getId_angajat());
					pst.setString(2, a.getPrenume());
					pst.setString(3, a.getNume());
					pst.setInt(4, a.getId_departament());
					pst.setString(5, a.getCNP());
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Angajat adaugat");
					angajat_table_load();
					txtNumeAngajat.setText("");
					txtPrenumeAngajat.setText("");
					txtIdAngajat.setText("");
					txtIdDepartament.setText("");
					txtCnp.setText("");
					txtNumeAngajat.requestFocus();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		saveButton.setBounds(20, 375, 135, 28);
		frame.getContentPane().add(saveButton);
		
		JButton clearButton = new JButton("View Angajati");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				angajat_table_load();
			}
		});
		clearButton.setBounds(461, 602, 135, 28);
		frame.getContentPane().add(clearButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(402, 68, 450, 523);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton updateButton = new JButton("Update\r\n");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				if (txtCnp.getText().equals("")) {
					
					Concediu c = new Concediu();
					
					c.setData_incepere(Date.valueOf(txtDataIncepere.getText()));
					c.setData_finalizare(Date.valueOf(txtDataFinalizare.getText()));
					c.setId_concediu(Integer.parseInt(txtIdConcediu.getText()));
					
					
					try {
						update_concediu_table_load(c.getData_incepere(), c.getData_finalizare(), c.getId_concediu());
						txtDataIncepere.setText("");
						txtDataFinalizare.setText("");
						txtIdConcediu.setText("");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
				} else {
					
					Angajat a = new Angajat();
					
					a.setNume(txtNumeAngajat.getText());
					a.setPrenume(txtPrenumeAngajat.getText());
					a.setId_departament(Integer.parseInt(txtIdDepartament.getText()));
					a.setCNP(txtCnp.getText());
					
					update_angajat_table_load(a.getNume(), a.getPrenume(), a.getId_departament(), a.getCNP());
					
					txtNumeAngajat.setText("");
					txtPrenumeAngajat.setText("");
					txtIdDepartament.setText("");
					txtCnp.setText("");
				}
				
				
				
			}
			
		});
		updateButton.setBounds(474, 641, 110, 28);
		frame.getContentPane().add(updateButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//aici

				if (txtIdConcediu.getText().equals("")) {
					
					Angajat a = new Angajat();
					
					a.setId_angajat(Integer.parseInt(txtIdAngajat.getText()));

					delete_angajat_table_load(a.getId_angajat());

					txtIdAngajat.setText("");
				}

				else {
					
					Concediu c = new Concediu();
					
					c.setId_concediu(Integer.parseInt(txtIdConcediu.getText()));


					delete_concediu_table_load(c.getId_concediu());

					txtIdConcediu.setText("");
				}

			}
		});
		deleteButton.setBounds(651, 641, 110, 28);
		frame.getContentPane().add(deleteButton);
		
		JButton viewConcediiBtn = new JButton("View Concedii");
		viewConcediiBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				concedii_table_load();
				
			}
		});
		viewConcediiBtn.setBounds(636, 602, 141, 29);
		frame.getContentPane().add(viewConcediiBtn);
		
		JButton btnInsertConcediu = new JButton("Insert concediu");
		btnInsertConcediu.setBounds(218, 375, 135, 29);
		frame.getContentPane().add(btnInsertConcediu);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cautare angajati", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 428, 164, 163);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnCautaCnp = new JButton("Dupa CNP\r\n");
		btnCautaCnp.setBounds(10, 44, 135, 23);
		panel_1.add(btnCautaCnp);
		
		JButton btnCautaAngajatiDupaPerioada = new JButton("Dupa perioada");
		btnCautaAngajatiDupaPerioada.setBounds(10, 93, 135, 23);
		panel_1.add(btnCautaAngajatiDupaPerioada);
		btnCautaAngajatiDupaPerioada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//aici
				
				String zi_inceput = JOptionPane.showInputDialog("Introduceti prima zi din perioada dorita");
				String zi_sfarsit = JOptionPane.showInputDialog("Introduceti ultima zi din perioada dorita");
				
				Concediu c = new Concediu();
				
				c.setData_incepere(Date.valueOf(zi_inceput));
				c.setData_finalizare(Date.valueOf(zi_sfarsit));
				
				
				cautare_angajati_dupa_perioada_table_load(c.getData_incepere(), c.getData_finalizare());
				
			}
		});
		
		
				btnCautaCnp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Angajat a = new Angajat();
						
						a.setCNP(txtCnp.getText());
						
						cautare_cnp_table_load(a.getCNP());
						txtCnp.setText("");
						
					}
				});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Cautare concedii", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(184, 428, 209, 163);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnCautareConcediuDupaZiSiAngajat = new JButton("Dupa zi & angajat\r\n");
		btnCautareConcediuDupaZiSiAngajat.setBounds(34, 43, 135, 23);
		panel_2.add(btnCautareConcediuDupaZiSiAngajat);
		
		JButton btnCautareConcediuDupaIdAngajat = new JButton("Dupa ID angajat");
		btnCautareConcediuDupaIdAngajat.setBounds(34, 92, 135, 23);
		panel_2.add(btnCautareConcediuDupaIdAngajat);
		btnCautareConcediuDupaIdAngajat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id_angajat = JOptionPane.showInputDialog("Introduceti id-ul angajatului");
				
				Angajat a = new Angajat();
				
				a.setId_angajat(Integer.parseInt(id_angajat));

				cautare_concediu_dupa_id_angajat_table_load(a.getId_angajat());
			}
		});
		btnCautareConcediuDupaZiSiAngajat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id_angajat = JOptionPane.showInputDialog("Introduceti id-ul angajatului");
				String zi_concediu = JOptionPane.showInputDialog("Introduceti ziua de concediu");
				
				Concediu c = new Concediu();
				
				c.setId_angajat(Integer.parseInt(id_angajat));
				c.setData_incepere(Date.valueOf(zi_concediu));

				cautare_zi_angajat_table_load(c.getData_incepere(), c.getId_angajat());
			}
		});

		btnInsertConcediu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Concediu c = new Concediu();
				
				c.setData_finalizare(Date.valueOf(txtDataFinalizare.getText()));
				c.setData_incepere(Date.valueOf(txtDataIncepere.getText()));
				c.setId_angajat(Integer.parseInt(txtIdAngajatRequest.getText()));
				c.setId_concediu(Integer.parseInt(txtIdConcediu.getText()));

				try {
					pst = con.prepareStatement("INSERT INTO concedii (id_angajat, data_incepere, data_finalizare, id_concediu) values (?, ?, ?, ?)");

					pst.setInt(1, c.getId_angajat());
					pst.setDate(2, c.getData_incepere());
					pst.setDate(3, c.getData_finalizare());
					pst.setInt(4, c.getId_concediu());

					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Concediu adaugat");
					concedii_table_load();

					txtIdAngajatRequest.setText("");
					txtIdConcediu.setText("");
					txtDataIncepere.setText("");
					txtDataFinalizare.setText("");
				} catch(SQLException e1) {
					e1.printStackTrace();
				}

			}
		});


		
	}
}
