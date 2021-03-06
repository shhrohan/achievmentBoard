package com.assignment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assignment.datastructures.Event;
import com.assignment.datastructures.Game;
import com.assignment.model.Model;
import com.assignment.utils.PrintUtil;

/**
 * Servlet implementation class HomeListener
 */
// @WebServlet("/HomeListener")
@WebServlet(urlPatterns = { "/mySSE" }, name = "hello-sse", asyncSupported = true)
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

		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		String next = "data: " + getData() + "\n\n";
		PrintUtil.printInNewLine("Client request received..");
		out.write(next);
		out.flush();

	}

	private String getData() {

		List<Event> events = Event.select(null);
		List<Long> ids = new ArrayList<Long>();

		StringBuilder stb = new StringBuilder();

		for (Event e : events) {
			ids.add(e.getId());
			stb.append(e.getAttacker().getName() + " ");
			stb.append(e.getAction().getName() + " ");
			stb.append(e.getDefender().getName());
			stb.append("|");
		}

		Event.delete(ids);

		return String.valueOf(stb.toString());
	}

}
