<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh Sách Sách</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="my-4 text-center">Danh Sách Sách Thư Viện ABC</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>Mã Sách</th>
            <th>Tên Sách</th>
            <th>Tác Giả</th>
            <th>Mô Tả</th>
            <th>Số Lượng</th>
            <th>Hành Động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>${book.bookId}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.description}</td>
                <td>${book.quantity}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/borrow?bookId=${book.bookId}" class="btn btn-primary btn-sm">Mượn</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/borrow-list" class="btn btn-secondary">Xem Sách Đang Mượn</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>