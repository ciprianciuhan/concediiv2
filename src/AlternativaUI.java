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
			con = DriverManager.getConnection("jdbc:mysql://localhost/concedii", "root", "nero1234");
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
			//pst = con.prepareStatement("update angajati set nume="+nume+", prenume="+prenume+", id_departament="+id_departament+" where cnp="+cnp);
			pst = con.prepareStatement("update angajati set nume = ?, prenume = ?, id_departament = ? where cnp = ?");
			//pst.setString(1, cnp);
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
				
				String nume, prenume, CNP;
				int id_angajat, id_departament;
				
				nume = txtNumeAngajat.getText();
				prenume = txtPrenumeAngajat.getText();
				id_angajat = Integer.parseInt(txtIdAngajat.getText());
				id_departament = Integer.parseInt(txtIdDepartament.getText());
				CNP = txtCnp.getText();
				
				try {
					pst = con.prepareStatement("INSERT INTO angajati (id_angajat, prenume, nume, id_departament, CNP) values (?, ?, ?, ?, ?)");
					pst.setInt(1, id_angajat);
					pst.setString(2, prenume);
					pst.setString(3, nume);
					pst.setInt(4, id_departament);
					pst.setString(5, CNP);
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
		saveButton.setBounds(10, 375, 110, 28);
		frame.getContentPane().add(saveButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(10, 482, 110, 28);
		frame.getContentPane().add(exitButton);
		
		JButton clearButton = new JButton("View Angajati");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				angajat_table_load();
			}
		});
		clearButton.setBounds(282, 376, 110, 28);
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
					Date data_incepere = Date.valueOf(txtDataIncepere.getText());
					Date data_finalizare = Date.valueOf(txtDataFinalizare.getText());
					int id_concediu = Integer.parseInt(txtIdConcediu.getText());
					
					try {
						update_concediu_table_load(data_incepere, data_finalizare, id_concediu);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} else {
					String nume_nou = txtNumeAngajat.getText();
					String prenume_nou = txtPrenumeAngajat.getText();
					int id_dep_nou = Integer.parseInt(txtIdDepartament.getText());
					String cnp = txtCnp.getText();
					
					update_angajat_table_load(nume_nou, prenume_nou, id_dep_nou, cnp);
				}
				
				
				
			}
			
		});
		updateButton.setBounds(479, 602, 110, 28);
		frame.getContentPane().add(updateButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//aici

				if (txtIdConcediu.getText().equals("")) {

					int id_angajat = Integer.parseInt(txtIdAngajat.getText());

					delete_angajat_table_load(id_angajat);

					txtIdAngajat.setText("");
				}

				else {

					int id_concediu=Integer.parseInt(txtIdConcediu.getText());

					delete_concediu_table_load(id_concediu);

					txtIdConcediu.setText("");
				}

			}
		});
		deleteButton.setBounds(638, 602, 110, 28);
		frame.getContentPane().add(deleteButton);
		
		JButton btnCautaCnp = new JButton("Cauta dupa CNP");
		btnCautaCnp.setBounds(10, 412, 129, 23);
		frame.getContentPane().add(btnCautaCnp);
		
		JButton btnCautaZi = new JButton("Cauta dupa zi");
		btnCautaZi.setBounds(10, 447, 120, 23);
		frame.getContentPane().add(btnCautaZi);
		
		JButton viewConcediiBtn = new JButton("View Concedii");
		viewConcediiBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				concedii_table_load();
				
			}
		});
		viewConcediiBtn.setBounds(275, 416, 117, 29);
		frame.getContentPane().add(viewConcediiBtn);
		
		JButton btnInsertConcediu = new JButton("Insert concediu");
		btnInsertConcediu.setBounds(135, 376, 135, 29);
		frame.getContentPane().add(btnInsertConcediu);

		btnInsertConcediu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id_angajat,id_concediu;
				Date data_incepere,data_finalizare;
				id_angajat=Integer.parseInt(txtIdAngajatRequest.getText());
				id_concediu=Integer.parseInt(txtIdConcediu.getText());
				data_incepere=Date.valueOf(txtDataIncepere.getText());
				data_finalizare=Date.valueOf(txtDataFinalizare.getText());

				try {
					pst = con.prepareStatement("INSERT INTO concedii (id_angajat, data_incepere, data_finalizare, id_concediu) values (?, ?, ?, ?)");

					pst.setInt(1,id_angajat);
					pst.setDate(2,data_incepere);
					pst.setDate(3,data_finalizare);
					pst.setInt(4,id_concediu);

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


		btnCautaCnp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String CNP_1 = txtCnp.getText();
				cautare_cnp_table_load(CNP_1);
				
			}
		});
		
	}
}
