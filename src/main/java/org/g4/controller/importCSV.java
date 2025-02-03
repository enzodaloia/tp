package org.g4.controller;

import org.g4.CSVReader;
import org.g4.SQLiteConnection;
import org.g4.models.Student;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class importCSV {
    public static void importCSV() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an import option:");
        System.out.println("1. Import 'students.csv' from resources");
        System.out.println("2. Import a CSV file from your computer");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String filePath;
        if (choice == 1) {
            filePath = "src/main/resources/students.csv";
        } else if (choice == 2) {
            System.out.println("Enter the file path:");
            filePath = scanner.nextLine();
        } else {
            System.out.println("Invalid choice");
            return;
        }

        Connection conn = SQLiteConnection.connect();
        if (conn != null) {
            CSVReader csvReader = new CSVReader();
            try {
                List<Student> students = csvReader.readStudentsFromCSV(filePath);
                String insertSql = "INSERT INTO students(lastName, firstName, age, email) VALUES(?, ?, ?, ?)";
                String checkSql = "SELECT COUNT(*) FROM students WHERE lastName = ? AND firstName = ? AND age = ? AND email = ?";
                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql);
                     PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                    for (Student student : students) {
                        checkPstmt.setString(1, student.getLastName());
                        checkPstmt.setString(2, student.getFirstName());
                        checkPstmt.setInt(3, student.getAge());
                        checkPstmt.setString(4, student.getEmail());
                        ResultSet rs = checkPstmt.executeQuery();
                        if (rs.next() && rs.getInt(1) == 0) {
                            insertPstmt.setString(1, student.getLastName());
                            insertPstmt.setString(2, student.getFirstName());
                            insertPstmt.setInt(3, student.getAge());
                            insertPstmt.setString(4, student.getEmail());
                            insertPstmt.addBatch();
                        }
                    }
                    insertPstmt.executeBatch();
                    displayDatabaseContents(conn);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void displayDatabaseContents(Connection conn) {
        String sql = "SELECT lastName, firstName, age, email FROM students";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Lastname: " + rs.getString("lastName") +
                        ", Firstname: " + rs.getString("firstName") +
                        ", Age: " + rs.getInt("age") +
                        ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}