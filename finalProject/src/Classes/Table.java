package Classes;

import java.util.List;

public class Table {
	private String tabloAdi;
	private List<Kolon> kolonlar;
	

	public Table(String tabloAdi) {
		this.tabloAdi = tabloAdi;
	}

	public String getTabloAdi() {
		return tabloAdi;
	}

	public void setTabloAdi(String tabloAdi) {
		this.tabloAdi = tabloAdi;
	}

	public List<Kolon> getKolonlar() {
		return kolonlar;
	}

	public void setKolonlar(List<Kolon> kolonlar) {
		this.kolonlar = kolonlar;
	}

}
