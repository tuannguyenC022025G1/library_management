package com.library.model;

import java.util.Date;

public class BorrowCard {
    private String borrowId;
    private String bookId;
    private String studentId;
    private Date borrowDate;
    private Date returnDate;
    private boolean status;
    private String bookTitle;
    private String studentName;

    public BorrowCard() {}
    public BorrowCard(String borrowId, String bookId, String studentId, Date borrowDate, Date returnDate, boolean status) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.studentId = studentId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getBorrowId() { return borrowId; }
    public void setBorrowId(String borrowId) { this.borrowId = borrowId; }
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
}