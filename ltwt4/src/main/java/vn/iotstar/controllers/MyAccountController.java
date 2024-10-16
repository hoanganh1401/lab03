package vn.iotstar.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = { "/myaccount" })
public class MyAccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/myaccount.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
	    resp.setCharacterEncoding("UTF-8");
	    req.setCharacterEncoding("UTF-8");
	    
	    String images = "";
	    String phone = "";        
	    String fullname = "";

	    if (JakartaServletFileUpload.isMultipartContent(req)) {
	        DiskFileItemFactory factory = DiskFileItemFactory.builder().get();
	        JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);
	        upload.setFileSizeMax(Constant.MAX_FILE_SIZE);
			upload.setSizeMax(Constant.MAX_REQUEST_SIZE);
			String uploadPath = getServletContext().getRealPath("") + File.separator + "upload";; 
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
	        try {
	            List<FileItem> formItems = upload.parseRequest(req);
	            if (formItems != null && formItems.size() > 0) {
	                for (FileItem item : formItems) {
	                    if (item.isFormField()) {
	                        // Đọc các trường form
	                        String fieldName = item.getFieldName();
	                        if ("phone".equals(fieldName)) {
	                            phone = item.getString(StandardCharsets.UTF_8); // Đọc giá trị với encoding
	                        } else if ("fullname".equals(fieldName)) {
	                            fullname = item.getString(StandardCharsets.UTF_8); // Đọc giá trị với encoding
	                        } 
	                    } else {
	                        // Xử lý file upload
	                    	if (!item.isFormField() && item.getSize() > 0) {
								String fileName = new File(item.getName()).getName();
								String fullFilePath = Path.of(fileName).toString();
								item.write(Path.of(uploadPath, fileName));  // Lưu file vào thư mục đích
								images = fullFilePath;
							}
	                    }
	                }
	            }
	 
	            	
	        }
	        	
	         catch (Exception ex) {
	            req.setAttribute("message", "There was an error: " + ex.getMessage());
	        }
	    }
	    
	    
	   
	    UserServiceImpl service = new UserServiceImpl();
	    HttpSession session = req.getSession();
	    if (session != null && session.getAttribute("account") != null) {
	        User i = (User) session.getAttribute("account");
	        
	        User u = service.findByUsername(i.getUsername());
	        u.setFullname(fullname);
	        u.setPhone(phone);
	        if(!images.isEmpty())
	        	u.setImages(images);
	        if (!u.getPhone().equals(i.getPhone())) {
	            // Kiểm tra số điện thoại có tồn tại không
	            if (service.findByPhone(u.getPhone()) != null) {
	                req.setAttribute("alert", "Số điện thoại đã tồn tại!");
	                User updatedUser = service.findById(u.getUserid());
	                req.setAttribute("account", updatedUser);
	                req.getRequestDispatcher(Constant.Path.MyAccount).forward(req, resp);
	                return;
	            }
	        }
	        
	        
	        try {
	            service.update(u);
	            req.setAttribute("alert", "Đổi thành công!");
	            User updatedUser = service.findById(u.getUserid());
	            req.setAttribute("account", updatedUser);
	            req.getRequestDispatcher(Constant.Path.MyAccount).forward(req, resp);
	        } catch (Exception e) {
	            e.printStackTrace();
	            req.setAttribute("alert", "System error!");
	            req.getRequestDispatcher(Constant.Path.MyAccount).forward(req, resp);
	        }
	    } 
		
	}
}