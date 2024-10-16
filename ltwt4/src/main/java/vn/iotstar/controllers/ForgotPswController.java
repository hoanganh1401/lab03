package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.User;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;
@WebServlet(urlPatterns = { "/forgotpsw" })
public class ForgotPswController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/forgotpsw.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		User user = new User();
		user.setEmail(req.getParameter("email"));
		UserServiceImpl service = new UserServiceImpl();
		String alertMsg = "";
		if ((service.findByEmail(user.getEmail())) != null) {
			User u = service.findByEmail(user.getEmail());
			u.setPassword(req.getParameter("newpsw"));
			try {
				service.update(u);
				alertMsg = "Đổi thành công!";
				req.setAttribute("alert", alertMsg);
				resp.sendRedirect(req.getContextPath() + "/login");
			} catch (Exception e) {
				e.printStackTrace();
				alertMsg = "System error!";
				req.setAttribute("alert", alertMsg);
				req.getRequestDispatcher(Constant.Path.ForgotPsw).forward(req, resp);
			}
			
		}else {
			alertMsg = "Email không tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.ForgotPsw).forward(req, resp);
			return;
		}
		
		
	}

}
