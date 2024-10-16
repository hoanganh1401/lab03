<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<form action="${pageContext.request.contextPath}/admin/category/update" method="post" enctype="multipart/form-data">
    <input type="text" id="categoryid" name="categoryid" value="${cate.categoryid}" hidden="hidden"><br>

    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}"><br>

    <label for="imagesfile">Images File:</label><br>
    <!-- Hiển thị hình ảnh hiện tại nếu có -->
    <c:if test="${not empty cate.images}">
        <c:if test="${cate.images.substring(0, 5) != 'https'}">
            <c:url value="/upload/${cate.images}" var="imgUrl"></c:url>
        </c:if>
        <c:if test="${cate.images.substring(0, 5) == 'https'}">
            <c:url value="${cate.images}" var="imgUrl"></c:url>
        </c:if>
        <img height="150" width="200" src="${imgUrl}" alt="Current Image"/><br>
    </c:if>

    <input type="file" id="imagesfile" name="imagesfile"><br>
    
    <label for="imageslink">Images Link:</label><br>
    <input type="text" id="imageslink" name="imageslink" placeholder="Nhập link hình ảnh nếu có"><br>
    
    <label for="status">Status:</label><br>
    <input type="text" id="status" name="status" value="${cate.status}"><br>
    
    <br>
    <input type="submit" value="Submit">	 
</form>
