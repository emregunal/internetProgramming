package Serv;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Classes.Table;
import DAO.TabloIslemleri;


@WebServlet("/TabloGetir")
public class TabloGetir extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public TabloGetir() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Table> tabloListesi = TabloIslemleri.getTables();
        String json = new Gson().toJson(tabloListesi);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
