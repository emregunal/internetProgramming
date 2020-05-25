package Serv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import Classes.Kolon;
import Classes.Table;
import DAO.TabloIslemleri;

@WebServlet("/KolonGetir")
public class KolonGetir extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public KolonGetir() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Kolon> tabloListesi = TabloIslemleri.getColumnsByTableName(request.getParameter("tabloAdi"));
		String json = new Gson().toJson(tabloListesi);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tabloAdi = request.getParameter("tabloAdi");
		String columnDegerJSON = request.getParameter("kolonDegerListesi");
		Table table = new Table(tabloAdi);
		table.setKolonlar(new ArrayList<Kolon>());
		JSONParser parser = new JSONParser();
		
		try {
			JSONArray columnArray = (JSONArray) parser.parse(columnDegerJSON);

			for (int i = 0; i < columnArray.size(); i++) {
				JSONObject columnInfo = (JSONObject) columnArray.get(i);
				String columnName = (String) columnInfo.get("columnName");
				String columnValue = (String) columnInfo.get("columnValue");

				Kolon kolon = new Kolon(columnName, "");
				kolon.setColumnValue(columnValue);

				table.getKolonlar().add(kolon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TabloIslemleri.degerOlustur(table);
		doGet(request, response);
	}

}
