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
	private JTextField txtCautareDupaZi;
	private JTextField txtCautareDupaCNP;
	private JTextField txtCautareDupaIdAngajat;
	private JTextField txtCautareDupaDataIncepere;
	private JTextField txtCautareDupaDataFinalizare;

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
		
		JButton saveButton = new JButton("Save");
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
		exitButton.setBounds(138, 375, 110, 28);
		frame.getContentPane().add(exitButton);
		
		JButton clearButton = new JButton("Clear\r\n");
		clearButton.setBounds(268, 375, 110, 28);
		frame.getContentPane().add(clearButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(402, 68, 450, 523);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cautare angajat", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 414, 382, 74);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_4_1 = new JLabel("Dupa zi");
		lblNewLabel_1_1_4_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_4_1.setBounds(10, 21, 99, 14);
		panel_1.add(lblNewLabel_1_1_4_1);
		
		txtCautareDupaZi = new JTextField();
		txtCautareDupaZi.setColumns(10);
		txtCautareDupaZi.setBounds(80, 19, 193, 20);
		panel_1.add(txtCautareDupaZi);
		
		JLabel lblNewLabel_1_1_4_1_1 = new JLabel("Dupa CNP\r\n");
		lblNewLabel_1_1_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_4_1_1.setBounds(10, 46, 99, 14);
		panel_1.add(lblNewLabel_1_1_4_1_1);
		
		txtCautareDupaCNP = new JTextField();
		txtCautareDupaCNP.setColumns(10);
		txtCautareDupaCNP.setBounds(80, 44, 193, 20);
		panel_1.add(txtCautareDupaCNP);
		
		JButton btnCautaZi = new JButton("Cauta");
		btnCautaZi.setBounds(283, 18, 89, 23);
		panel_1.add(btnCautaZi);
		
		JButton btnCautaCnp = new JButton("Cauta");
		btnCautaCnp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
                    String CNP_1 = txtCautareDupaCNP.getText();
                    
                    System.out.println(CNP_1);
                    
                    pst = con.prepareStatement("select id_angajat, prenume, nume, id_departament, CNP from angajati where CNP = ?");
                    pst.setString(1, CNP_1);
                    ResultSet rs = pst.executeQuery();
                    
                    
                    if (rs.next() == true) {
                        int id_angajat = rs.getInt(1);
                        String prenume = rs.getString(2);
                        String nume = rs.getString(3);
                        int id_departament = rs.getInt(4);
                        String CNP = rs.getString(5);
                        
                       
                        JOptionPane.showMessageDialog(null, "Angajatul este: " + prenume + nume + " sa ma bata mama");
                        table.setModel(DbUtils.resultSetToTableModel(rs));
                        
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
				
			}
		});
		btnCautaCnp.setBounds(283, 43, 89, 23);
		panel_1.add(btnCautaCnp);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Cautare concedii", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 499, 382, 51);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("ID Angajat\r\n");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_1_1.setBounds(10, 21, 67, 14);
		panel_2.add(lblNewLabel_1_1_1_1);
		
		txtCautareDupaIdAngajat = new JTextField();
		txtCautareDupaIdAngajat.setColumns(10);
		txtCautareDupaIdAngajat.setBounds(117, 19, 240, 20);
		panel_2.add(txtCautareDupaIdAngajat);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Cautare dupa perioada", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 556, 382, 74);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_1_1_5_1 = new JLabel("Data incepere");
		lblNewLabel_1_1_5_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_5_1.setBounds(10, 22, 99, 14);
		panel_3.add(lblNewLabel_1_1_5_1);
		
		txtCautareDupaDataIncepere = new JTextField();
		txtCautareDupaDataIncepere.setColumns(10);
		txtCautareDupaDataIncepere.setBounds(119, 20, 240, 20);
		panel_3.add(txtCautareDupaDataIncepere);
		
		JLabel lblNewLabel_1_1_6_1 = new JLabel("Data finalizare");
		lblNewLabel_1_1_6_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_6_1.setBounds(10, 47, 99, 14);
		panel_3.add(lblNewLabel_1_1_6_1);
		
		txtCautareDupaDataFinalizare = new JTextField();
		txtCautareDupaDataFinalizare.setColumns(10);
		txtCautareDupaDataFinalizare.setBounds(119, 45, 240, 20);
		panel_3.add(txtCautareDupaDataFinalizare);
		
		JButton updateButton = new JButton("Update\r\n");
		updateButton.setBounds(479, 602, 110, 28);
		frame.getContentPane().add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(638, 602, 110, 28);
		frame.getContentPane().add(deleteButton);
	}
}
