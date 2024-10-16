package vn.iotstar.controllers.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.nio.file.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryService;
import vn.iotstar.utils.Constant;

@MultipartConfig()
@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", "/admin/category/insert",
		"/admin/category/edit", "/admin/category/update","/admin/category/search", "/admin/category/delete" })
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ICategoryService cateService = new CategoryService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if (url.contains("/admin/categories")) {
			List<Category> list = cateService.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
		} else if (url.contains("/admin/category/add")) {
			req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
		} else if (url.contains("/admin/category/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Category category = cateService.findById(id);
			req.setAttribute("cate", category);
			req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
		}else if(url.contains("search")) {
			String key = req.getParameter("keyword");
			List<Category> list = cateService.searchByName(key);
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
		} else {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				cateService.delete(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// chuyển trang
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String images = "";
		String categoryname = req.getParameter("categoryname");
		int status = Integer.parseInt(req.getParameter("status"));
		
		
		//String uploadPath = UPLOAD_DIRECTORY // lưu vào thư mục trong máy
		String uploadPath = getServletContext().getRealPath("") + File.separator + "upload"; // luu vào thư mục trong prj
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) uploadDir.mkdir();

	    try {
	        String fileName = "";
	        // Duyệt qua các part trong request
	        for (Part part : req.getParts()) {
	            // Kiểm tra nếu part là file (có content-disposition)
	            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
	            	fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
	                part.write(uploadPath + File.separator + fileName);
	        	    	images = fileName;
	            }
	        }
	    } catch (FileNotFoundException fne) {
	        req.setAttribute("message", "Có lỗi xảy ra: " + fne.getMessage());
	    }
	    if(images == "" && req.getParameter("images") != null ) {
	    	images = req.getParameter("images");
	    }

	
		if(url.contains("insert")) {
		
			Category category = new Category();
			category.setCategoryname(categoryname);
			category.setStatus(status);
			category.setImages(images);
			cateService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}else if(url.contains("update")) {
			
			int categoryid = Integer.parseInt(req.getParameter("categoryid"));
			if(images == "") {
				
				Category a = cateService.findById(categoryid);
				images = a.getImages();
			}
			
			Category category = new Category();
			category.setCategoryId(categoryid);
			category.setCategoryname(categoryname);
			category.setStatus(status);
			category.setImages(images);
			cateService.update(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}
	}
	