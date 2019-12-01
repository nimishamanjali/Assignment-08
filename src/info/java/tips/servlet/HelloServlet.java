package info.java.tips.servlet;

import java.io.BufferedReader;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	public ArrayList<User> list = new ArrayList<>();
	ObjectMapper mapper = new ObjectMapper();

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String array[] = req.getRequestURI().split("/");
		String name = null;
		System.out.println(array.length);

		if (array.length == 4) {// 4 is the one
			name = array[3];
			System.out.println(array.length);
			for (User user : list) {
				if (user.getName().equals(name)) {
					String write = mapper.writeValueAsString(user);
					res.getWriter().println("[");
					res.getWriter().println(write);
					res.getWriter().println("]");
					res.setStatus(200);
				} else if (list.isEmpty()) {
					// res.getWriter().println("No element in list");
					res.setStatus(204);
				} else {
					res.setStatus(404);
				}
			}
		} else {
			res.getWriter().println("[");
			for (int i = 0; i < list.size(); i++) {
				String json = mapper.writeValueAsString(list.get(i));
				res.getWriter().println(json + ",");
				if (i == list.size() - 1)
					res.getWriter().println(json);
			}
			res.getWriter().println("]");

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setStatus(201);
		User user = null;

		if (req.getHeader("Content-Type").equals("application/x-www-form-urlencoded")) {// check correctness
			user = new User(req.getParameter("name"), req.getParameter("address"),
					new User(req.getParameter("bestfriend")));

		} else {
			BufferedReader read = req.getReader();
			String response = new String();
			for (String line; (line = read.readLine()) != null; response += line)
				;
			JsonNode node = mapper.readTree(response);
			user = new User(node.path("name").asText(), node.path("address").asText(),
					new User(node.path("bestfriend").asText()));

		}
		list.add(user);
		res.getWriter().println(mapper.writeValueAsString(user));
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setStatus(200);
		User modify = null;

		BufferedReader read = req.getReader();
		String response = new String();
		for (String line; (line = read.readLine()) != null; response += line)
			;
		JsonNode node = mapper.readTree(response);
		modify = new User(node.path("name").asText(), node.path("address").asText(),
				new User(node.path("bestfriend").asText()));

		for (User user : list) {
			if (user.getName().equals(modify.getName())) {
				list.remove(user);
			}
		}
		list.add(modify);
		res.getWriter().println(mapper.writeValueAsString(modify));
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		User blogPost = null;

		String array[] = req.getRequestURI().split("/");
		String name = null;
		System.out.println(array.length);

		if (array.length == 4) {// 4 is the one
			name = array[3];
			for (User u : list) {
				if (u.getName().equals(name)) {
					list.remove(u);
					res.setStatus(204);
				} else {
					// res.getWriter().println("Not found");
					res.setStatus(404);
				}
			}

		}
	}

}
