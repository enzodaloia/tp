package org.g4.controller;

import org.g4.SQLiteConnection;

import java.sql.*;
import java.util.Scanner;

public class manageClasses {
    public static void manageClasses() {
        Scanner scanner = new Scanner(System.in);
        Connection conn = SQLiteConnection.connect();

        if (conn == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add Class");
            System.out.println("2. Update Class");
            System.out.println("3. Delete Class");
            System.out.println("4. View Classes");
            System.out.println("5. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addClass(conn, scanner);
                    break;
                case 2:
                    updateClass(conn, scanner);
                    break;
                case 3:
                    deleteClass(conn, scanner);
                    break;
                case 4:
                    viewClasses(conn);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void addClass(Connection conn, Scanner scanner) {
        System.out.println("Enter class name:");
        String name = scanner.nextLine();

        String sql = "INSERT INTO classes(name) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Class added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateClass(Connection conn, Scanner scanner) {
        System.out.println("Enter class ID:");
        int classId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new class name:");
        String name = scanner.nextLine();

        String sql = "UPDATE classes SET name = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, classId);
            pstmt.executeUpdate();
            System.out.println("Class updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteClass(Connection conn, Scanner scanner) {
        System.out.println("Enter class ID:");
        int classId = scanner.nextInt();

        String sql = "DELETE FROM classes WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.executeUpdate();
            System.out.println("Class deleted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewClasses(Connection conn) {
        String sql = "SELECT * FROM classes";
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