<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sách Đang Mượn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="my-4 text-center">Danh Sách Sách Đang Mượn</h2>
    <form action="${pageContext.request.contextPath}/borrow-list" method="get" class="mb-4">
        <div class="row g-3">
            <div class="col-md-5">
                <input type="text" class="form-control" name="bookTitle" placeholder="Tìm theo tên sách" value="${param.bookTitle}">
            </div>
            <div class="col-md-5">
                <input type="text" class="form-control" name="studentName" placeholder="Tìm theo tên học sinh" value="${param.studentName}">
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100">Tìm Kiếm</button>
            </div>
        </div>
    </form>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>Mã Mượn</th>
            <th>Tên Sách</th>
            <th>Học Sinh</th>
            <th>Ngày Mượn</th>
            <th>Ngày Trả</th>
            <th>Hành Động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bc" items="${borrowCards}">
            <tr>
                <td>${bc.borrowId}</td>
                <td>${bc.bookTitle}</td>
                <td>${bc.studentName}</td>
                <td><fmt:formatDate value="${bc.borrowDate}" pattern="dd/MM/yyyy"/></td>
                <td><fmt:formatDate value="${bc.returnDate}" pattern="dd/MM/yyyy"/></td>
                <td>
                    <form action="${pageContext.request.contextPath}/return" method="post" onsubmit="return confirm('Bạn có chắc muốn trả sách này?')">
                        <input type="hidden" name="borrowId" value="${bc.borrowId}">
                        <input type="hidden" name="bookId" value="${bc.bookId}">
                        <button type="submit" class="btn btn-danger btn-sm">Trả Sách</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Trở Về Danh Sách Sách</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>