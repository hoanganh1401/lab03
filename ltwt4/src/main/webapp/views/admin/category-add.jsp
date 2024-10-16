<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<form action = "${pageContext.request.contextPath}/admin/category/insert" method="post" enctype="multipart/form-data">
	<label for="fname">Category name:</label><br> 
	<input type="text" id="categoryname" name="categoryname" required><br> <!-- Thêm required để bắt buộc nhập -->
	
	<label for="images">Link images:</label><br> 
	<input type="text" id="images" name="images"><br> <!-- Thêm placeholder để hướng dẫn người dùng -->
	
	<label for="images1">Upload images:</label><br> 
	<input type="file" id="images1" name="images1"><br>
	
	<label for="status">Status</label><br> 
	<input type="radio" id="ston" name="status" value="1" checked> 
	<label for="ston">Hoạt động</label><br> 
	<input type="radio" id="stoff" name="status" value="0"> 
	<label for="stoff">Khóa</label> <br>
	
	<br> 
	<input type="submit" value="Insert">
</form>