import java.sql.Date;


public class Concediu {
	
	private int id_concediu;
	private Date data_incepere;
	private Date data_finalizare;
	private int id_angajat;
	
	public int getId_concediu() {
		return id_concediu;
	}
	public void setId_concediu(int id_concediu) {
		this.id_concediu = id_concediu;
	}
	public Date getData_incepere() {
		return data_incepere;
	}
	public void setData_incepere(Date data_incepere) {
		this.data_incepere = data_incepere;
	}
	public Date getData_finalizare() {
		return data_finalizare;
	}
	public void setData_finalizare(Date data_finalizare) {
		this.data_finalizare = data_finalizare;
	}
	public int getId_angajat() {
		return id_angajat;
	}
	public void setId_angajat(int id_angajat) {
		this.id_angajat = id_angajat;
	}
	
}
