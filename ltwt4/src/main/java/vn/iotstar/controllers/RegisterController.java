package vn.iotstar.controllers;

import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * HttpSession session = req.getSession(false); if (session != null &&
		 * session.getAttribute("username") != null) {
		 * resp.sendRedirect(req.getContextPath() + "/admin"); return; } Cookie[]
		 * cookies = req.getCookies(); if (cookies != null) { for (Cookie cookie :
		 * cookies) { if (cookie.getName().equals("username")) { session =
		 * req.getSession(true); session.setAttribute("username", cookie.getValue());
		 * resp.sendRedirect(req.getContextPath() + "/admin"); return; } } }
		 */
		req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		User u =new User();
		u.setUsername(req.getParameter("username"));
		u.setPassword(req.getParameter("password"));
		u.setEmail(req.getParameter("email"));
		u.setFullname(req.getParameter("fullname"));
		u.setPhone(req.getParameter("phone"));
		Role r= new Role();
		r.setRoleid(1);
		u.setRoleid(r);
		Date currentDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
		u.setCreateDate(sqlDate);
		UserServiceImpl service = new UserServiceImpl();
		String alertMsg = "";
		if (service.findByEmail(u.getEmail()) != null) {
			alertMsg = "Email đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}
		if (service.findByUsername(u.getUsername()) != null) {
			alertMsg = "Tài khoản đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}
		if (service.findByPhone(u.getPhone()) != null) {
			alertMsg = "Số điện thoại đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}
		User user = service.register(u);
		if (user != null) {
			// SendMail sm = new SendMail();
			// sm.sendMail(email, "Shopping.iotstar.vn", "Welcome to Shopping. Please Login
			// to use service. Thanks !");
			alertMsg = "Tạo thành công!";
			req.setAttribute("alert", alertMsg);
		    HttpSession session = req.getSession(true); 
		    session.setAttribute("account", user);
			resp.sendRedirect(req.getContextPath() + "/waiting");
		} else {
			alertMsg = "System error!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
		}
	}
}