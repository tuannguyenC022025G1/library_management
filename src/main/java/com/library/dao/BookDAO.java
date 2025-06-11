package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setQuantity(rs.getInt("quantity"));
                books.add(book);
            }
        }
        return books;
    }

    public Book getBookById(String bookId) throws SQLException {
        String sql = "SELECT * FROM Book WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getString("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setDescription(rs.getString("description"));
                    book.setQuantity(rs.getInt("quantity"));
                    return book;
                }
            }
        }
        return null;
    }

    public void updateBookQuantity(String bookId, int quantity) throws SQLException {
        String sql = "UPDATE Book SET quantity = ? WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, bookId);
            stmt.executeUpdate();
        }
    }
}