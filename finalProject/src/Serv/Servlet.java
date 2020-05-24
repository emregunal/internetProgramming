package Serv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Classes.Kolon;
import Classes.Table;
import DAO.KayitIslemleri;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("RESPONSE");
		ArrayList<String> d = new ArrayList<String>();
		d.add("asd");
		d.add("aa");
		
		ArrayList<Table> tabloListesi = new ArrayList<Table>();

		Table tbl1 = new Table("TEST1");
		tabloListesi.add(tbl1);
		
        request.setAttribute("tabloListesi", tabloListesi);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String tableName = request.getParameter("txtTable");
		String columnsJSON = request.getParameter("columnList");

		Table table = new Table(tableName);
		table.setKolonlar(new ArrayList<Kolon>());
		
		
		JSONParser parser = new JSONParser();
		try {
			JSONArray columnArray = (JSONArray) parser.parse(columnsJSON);
			
			 for (int i = 0; i <columnArray.size(); i++) {
				   JSONObject columnInfo= (JSONObject) columnArray.get(i);
				   String columnName=(String) columnInfo.get("columnName");
				   String dataType=(String) columnInfo.get("dataType");
				   
				   Kolon kolon = new Kolon(columnName, dataType);
				   table.getKolonlar().add(kolon);
				 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * TABLE OLUSTUR
		 * */		
		KayitIslemleri.tabloOlustur(table);			
	}

}
