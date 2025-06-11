package com.library.controller;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.dao.BorrowCardDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.model.BorrowCard;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/borrow")
public class BorrowController extends HttpServlet {
    private BookDAO bookDAO;
    private StudentDAO studentDAO;
    private BorrowCardDAO borrowCardDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
        studentDAO = new StudentDAO();
        borrowCardDAO = new BorrowCardDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String bookId = req.getParameter("bookId");
            Book book = bookDAO.getBookById(bookId);
            if (book == null || book.getQuantity() <= 0) {
                req.setAttribute("error", "Sách không tồn tại hoặc đã hết!");
                List<Book> books = bookDAO.getAllBooks();
                req.setAttribute("books", books);
                req.getRequestDispatcher("/views/book_list.jsp").forward(req, resp);
                return;
            }
            List<Student> students = studentDAO.getAllStudents();
            req.setAttribute("book", book);
            req.setAttribute("students", students);
            req.setAttribute("now", new Date());
            req.getRequestDispatcher("/views/borrow_form.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi hiển thị form mượn sách");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String borrowId = req.getParameter("borrowId");
            String bookId = req.getParameter("bookId");
            String studentId = req.getParameter("studentId");
            String returnDateStr = req.getParameter("returnDate");

            if (!borrowId.matches("^MS-\\d{4}$")) {
                req.setAttribute("error", "Mã mượn sách phải có định dạng MS-XXXX (X là số)");
                forwardToBorrowForm(req, resp, bookId);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date borrowDate = new Date();
            Date returnDate;
            try {
                returnDate = sdf.parse(returnDateStr);
                if (returnDate.before(borrowDate)) {
                    req.setAttribute("error", "Ngày trả không được trước ngày mượn!");
                    forwardToBorrowForm(req, resp, bookId);
                    return;
                }
            } catch (Exception e) {
                req.setAttribute("error", "Định dạng ngày trả không hợp lệ (dd/MM/yyyy)!");
                forwardToBorrowForm(req, resp, bookId);
                return;
            }

            Book book = bookDAO.getBookById(bookId);
            if (book.getQuantity() <= 0) {
                req.setAttribute("error", "Sách đã hết, không thể mượn!");
                forwardToBorrowForm(req, resp, bookId);
                return;
            }

            BorrowCard borrowCard = new BorrowCard(borrowId, bookId, studentId, borrowDate, returnDate, true);
            borrowCardDAO.addBorrowCard(borrowCard);
            bookDAO.updateBookQuantity(bookId, book.getQuantity() - 1);

            resp.sendRedirect(req.getContextPath() + "/books");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi mượn sách");
        }
    }

    private void forwardToBorrowForm(HttpServletRequest req, HttpServletResponse resp, String bookId) throws ServletException, IOException {
        try {
            Book book = bookDAO.getBookById(bookId);
            List<Student> students = studentDAO.getAllStudents();
            req.setAttribute("book", book);
            req.setAttribute("students", students);
            req.setAttribute("now", new Date());
            req.getRequestDispatcher("/views/borrow_form.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi hiển thị form mượn sách");
        }
    }
}