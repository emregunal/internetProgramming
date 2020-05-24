package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Classes.Kolon;
import Classes.Table;

public class TabloIslemleri {

	private static String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=IP_FINAL;integratedSecurity=true;";

	public static List<Table> getTables() {
		List<Table> tables = new ArrayList<Table>();
		Connection con;
		try {
			con = DriverManager.getConnection(connUrl);
			PreparedStatement pst = con.prepareStatement("SELECT NAME FROM  sys.tables");
			ResultSet resultSet = pst.executeQuery();

			while (resultSet.next()) {
				String tabloAdi = resultSet.getString("NAME");

				Table t = new Table(tabloAdi);
				tables.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tables;
	}

	/*
	 * Tablo adýna göre kolonlarýný getirir
	 */
	public static List<Kolon> getColumnsByTableName(String tableName) {
		List<Kolon> columns = new ArrayList<Kolon>();
		Connection con;
		try {
			con = DriverManager.getConnection(connUrl);
			PreparedStatement pst = con.prepareStatement(
					"select\r\n" + "    tab.name as table_name, \r\n" + "    col.name as column_name\r\n"
							+ "from sys.tables as tab\r\n" + "    inner join sys.columns as col\r\n"
							+ "        on tab.object_id = col.object_id\r\n" + "where tab.name = '" + tableName + "'");

			ResultSet resultSet = pst.executeQuery();

			while (resultSet.next()) {
				String kolonAdi = resultSet.getString("column_name");

				Kolon c = new Kolon(kolonAdi, "");
				columns.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return columns;
	}

}
