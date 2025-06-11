package com.library.controller;

import com.library.dao.BookDAO;
import com.library.dao.BorrowCardDAO;
import com.library.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/return")
public class ReturnController extends HttpServlet {
    private BorrowCardDAO borrowCardDAO;
    private BookDAO bookDAO;

    @Override
    public void init() {
        borrowCardDAO = new BorrowCardDAO();
        bookDAO = new BookDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String borrowId = req.getParameter("borrowId");
            String bookId = req.getParameter("bookId");
            borrowCardDAO.returnBook(borrowId);

            Book book = bookDAO.getBookById(bookId);
            bookDAO.updateBookQuantity(bookId, book.getQuantity() + 1);

            resp.sendRedirect(req.getContextPath() + "/borrow-list");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi trả sách");
        }
    }
}