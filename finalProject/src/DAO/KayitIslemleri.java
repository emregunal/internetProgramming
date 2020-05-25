package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Classes.Kolon;
import Classes.Table;

public class KayitIslemleri {

	public static void tabloOlustur(Table tablo) {

		String tabloAdi = tablo.getTabloAdi();
		String kolonlar = "";

		for (int i = 0; i < tablo.getKolonlar().size(); i++) {
			Kolon kolon = tablo.getKolonlar().get(i);
			String kolonStr = kolon.getColumnName() + " " + kolon.getDataType();

			if (!(i == tablo.getKolonlar().size() - 1)) {
				kolonStr += ",";
			}

			kolonlar += kolonStr;
		}

		String tabloSorgusu = "CREATE TABLE " + tabloAdi + " (" + kolonlar + ");";

		System.out.println(tabloSorgusu);

		String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=IP_FINAL;integratedSecurity=true;";

		try {
			Connection con = DriverManager.getConnection(connUrl);
			PreparedStatement pst = con.prepareStatement(tabloSorgusu);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void degerOlustur(Table tablo) {
		String tabloAdi = tablo.getTabloAdi();
		String kolonStr = "";
		String kolonDegerStr = "";

		for (int i = 0; i < tablo.getKolonlar().size(); i++) {
			Kolon kolon = tablo.getKolonlar().get(i);

			

			kolonStr += kolon.getColumnName();			
			kolonDegerStr += kolon.getColumnValue();
			if (!(i == tablo.getKolonlar().size() - 1)) {
				kolonStr += ",";
				kolonDegerStr += ",";
			}
		}

		String sorgu = "insert into " + tabloAdi + " (" + kolonStr + ") values (" + kolonDegerStr + ")";
		String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=IP_FINAL;integratedSecurity=true;";

		try {
			Connection con = DriverManager.getConnection(connUrl);
			PreparedStatement pst = con.prepareStatement(sorgu);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
