package com.assignment.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assignment.datastructures.Player;
import com.assignment.datastructures.Statistic;

/**
 * Servlet implementation class AddStatistic
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/AddStatistic" })
public class AddStatistic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStatistic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("get");
		response.sendRedirect("AddStatistic.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("post");
		
		String name = request.getParameter("name");
		Double weight = Double.parseDouble(request.getParameter("weight"));
		Long value = Long.parseLong(request.getParameter("value"));
		
		System.out.println(name+  " " +  weight + " " + value);
		
		Statistic stat = new Statistic(name, weight, value);
		Statistic.insert(stat);
		
		for(Player player : Player.select(null)){
			player.setStatistics(Statistic.select(null));
			Player.update(player);
		}
		
		
		
		doGet(request, response);
	}

}
