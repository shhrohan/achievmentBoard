package com.assignment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assignment.datastructures.Game;

/**
 * Servlet implementation class HomeListener
 */
@WebServlet("/HomeListener")
public class HomeListener extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public HomeListener() {
		super();
		// TODO Auto-generated constructor stub
		try {
			Model.init();
			Game.setup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// content type must be set to text/event-stream
		response.setContentType("text/event-stream");

		// encoding must be set to UTF-8
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();

		for (int i = 0; i < 10; i++) {

			writer.write("data: " + System.currentTimeMillis() + "\n\n");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		writer.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
