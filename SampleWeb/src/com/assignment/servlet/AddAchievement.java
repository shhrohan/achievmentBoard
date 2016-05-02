package com.assignment.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assignment.datastructures.Achievement;
import com.assignment.datastructures.Statistic;

/**
 * Servlet implementation class AddAchievement
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/AddAchievement" })
public class AddAchievement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAchievement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("get");

		List<Statistic> stats = Statistic.select(null);
		System.out.println(stats);
		response.setContentType("text/html");
		request.setAttribute("stats", stats);
		request.getRequestDispatcher("/AddAchievement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("post");

		String name = request.getParameter("name");
		
		Long id = Long.parseLong(request.getParameter("stat"));
		List<Long> ids = new ArrayList<>();
		ids.add(id);
		Statistic stat = Statistic.select(ids).get(0);
		
		Long value = Long.parseLong(request.getParameter("value"));

		System.out.println(name + " " + value);

		Achievement ach = new Achievement();
		ach.setName(name);

		List<Statistic> list = new ArrayList<>();
		stat.setWeight(0d);
		stat.setValue(value);
		list.add(stat);

		Achievement.insert(ach);

		doGet(request, response);
	}

}
