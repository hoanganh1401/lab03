<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form action="${pageContext.request.contextPath}/admin/category/insert" method="post" enctype="multipart/form-data">
  <label for="categoryname">Category name:</label><br>
  <input type="text" id="categoryname" name="categoryname"><br>
  
  <label for="imagesfile">Images File:</label><br>
  <input type="file" id="imagesfile" name="imagesfile"><br>
  
  <label for="imageslink">Images Link:</label><br>
  <input type="text" id="imageslink" name="imageslink"><br>
  
  <label for="status">Status:</label><br>
  <input type="text" id="status" name="status"><br>
  
  <br>
  <input type="submit" value="Submit">    
</form>
