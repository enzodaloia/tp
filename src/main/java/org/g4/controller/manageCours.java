package org.g4.controller;

import org.g4.SQLiteConnection;

import java.sql.*;
import java.util.Scanner;

public class manageCours {
    public static void manageCourses() {
        Scanner scanner = new Scanner(System.in);
        Connection conn = SQLiteConnection.connect();

        if (conn == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Delete Course");
            System.out.println("4. View Courses");
            System.out.println("5. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCourse(conn, scanner);
                    break;
                case 2:
                    updateCourse(conn, scanner);
                    break;
                case 3:
                    deleteCourse(conn, scanner);
                    break;
                case 4:
                    viewCourses(conn);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void addCourse(Connection conn, Scanner scanner) {
        System.out.println("Enter course name:");
        String name = scanner.nextLine();

        String sql = "INSERT INTO courses(name) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Course added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateCourse(Connection conn, Scanner scanner) {
        System.out.println("Enter course ID:");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new course name:");
        String name = scanner.nextLine();

        String sql = "UPDATE courses SET name = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, courseId);
            pstmt.executeUpdate();
            System.out.println("Course updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteCourse(Connection conn, Scanner scanner) {
        System.out.println("Enter course ID:");
        int courseId = scanner.nextInt();

        String sql = "DELETE FROM courses WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            pstmt.executeUpdate();
            System.out.println("Course deleted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewCourses(Connection conn) {
        String sql = "SELECT * FROM courses";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}