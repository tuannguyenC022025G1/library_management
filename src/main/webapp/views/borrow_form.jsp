<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Mượn Sách</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
<div class="container">
  <h2 class="my-4 text-center">Mượn Sách</h2>
  <c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        ${error}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>
  <form action="${pageContext.request.contextPath}/borrow" method="post">
    <div class="mb-3">
      <label class="form-label">Mã Mượn Sách</label>
      <input type="text" class="form-control" name="borrowId" pattern="MS-\d{4}" placeholder="MS-XXXX" required>
      <div class="form-text">Mã mượn sách phải có định dạng MS-XXXX (X là số).</div>
    </div>
    <div class="mb-3">
      <label class="form-label">Tên Sách</label>
      <input type="text" class="form-control" value="${book.title}" readonly>
      <input type="hidden" name="bookId" value="${book.bookId}">
    </div>
    <div class="mb-3">
      <label class="form-label">Học Sinh</label>
      <select class="form-select" name="studentId" required>
        <option value="">Chọn học sinh</option>
        <c:forEach var="student" items="${students}">
          <option value="${student.studentId}">${student.fullName} (${student.className})</option>
        </c:forEach>
      </select>
    </div>
    <div class="mb-3">
      <label class="form-label">Ngày Mượn</label>
      <input type="text" class="form-control" value="<fmt:formatDate value="${now}" pattern="dd/MM/yyyy"/>" readonly>
    </div>
    <div class="mb-3">
      <label class="form-label">Ngày Trả</label>
      <input type="text" class="form-control" name="returnDate" placeholder="dd/MM/yyyy" required>
      <div class="form-text">Ngày trả phải có định dạng dd/MM/yyyy và không được trước ngày mượn.</div>
    </div>
    <button type="submit" class="btn btn-primary">Mượn Sách</button>
    <button type="button" class="btn btn-secondary" onclick="confirmBack()">Trở Về</button>
  </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  function confirmBack() {
    if (confirm("Bạn có chắc muốn trở về danh sách sách?")) {
      window.location.href = "${pageContext.request.contextPath}/books";
    }
  }
</script>
</body>
</html>