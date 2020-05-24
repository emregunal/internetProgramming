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
import DAO.KayitIslemleri;
import DAO.TabloIslemleri;

/**
 * Servlet implementation class KolonGetir
 */
@WebServlet("/KolonGetir")
public class KolonGetir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KolonGetir() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Kolon> tabloListesi = TabloIslemleri.getColumnsByTableName(request.getParameter("tabloAdi"));

		String json = new Gson().toJson(tabloListesi);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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

			/*
			 * TABLE OLUSTUR
			 */
			KayitIslemleri.degerOlustur(table);
		} catch (Exception e) {
			e.printStackTrace();
		}

		doGet(request, response);
	}

}
