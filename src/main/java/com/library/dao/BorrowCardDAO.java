package com.library.dao;

import com.library.model.BorrowCard;
import com.library.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowCardDAO {
    public void addBorrowCard(BorrowCard borrowCard) throws SQLException {
        String sql = "INSERT INTO BorrowCard (borrow_id, book_id, student_id, borrow_date, return_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, borrowCard.getBorrowId());
            stmt.setString(2, borrowCard.getBookId());
            stmt.setString(3, borrowCard.getStudentId());
            stmt.setDate(4, new java.sql.Date(borrowCard.getBorrowDate().getTime()));
            stmt.setDate(5, borrowCard.getReturnDate() != null ? new java.sql.Date(borrowCard.getReturnDate().getTime()) : null);
            stmt.setBoolean(6, borrowCard.isStatus());
            stmt.executeUpdate();
        }
    }

    public List<BorrowCard> getBorrowingBooks(String bookTitle, String studentName) throws SQLException {
        List<BorrowCard> borrowCards = new ArrayList<>();
        String sql = "SELECT bc.*, b.title, s.full_name FROM BorrowCard bc " +
                "JOIN Book b ON bc.book_id = b.book_id " +
                "JOIN Student s ON bc.student_id = s.student_id " +
                "WHERE bc.status = TRUE AND b.title LIKE ? AND s.full_name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (bookTitle != null ? bookTitle : "") + "%");
            stmt.setString(2, "%" + (studentName != null ? studentName : "") + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BorrowCard bc = new BorrowCard();
                    bc.setBorrowId(rs.getString("borrow_id"));
                    bc.setBookId(rs.getString("book_id"));
                    bc.setStudentId(rs.getString("student_id"));
                    bc.setBorrowDate(rs.getDate("borrow_date"));
                    bc.setReturnDate(rs.getDate("return_date"));
                    bc.setStatus(rs.getBoolean("status"));
                    bc.setBookTitle(rs.getString("title"));
                    bc.setStudentName(rs.getString("full_name"));
                    borrowCards.add(bc);
                }
            }
        }
        return borrowCards;
    }

    public void returnBook(String borrowId) throws SQLException {
        String sql = "UPDATE BorrowCard SET status = FALSE WHERE borrow_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, borrowId);
            stmt.executeUpdate();
        }
    }
}