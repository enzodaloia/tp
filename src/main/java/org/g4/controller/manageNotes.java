package org.g4.controller;

import org.g4.SQLiteConnection;
import org.g4.models.Classes;

import java.sql.*;
import java.util.Scanner;

public class manageNotes {
    public static void manageGrades() {
        Scanner scanner = new Scanner(System.in);
        Connection conn = SQLiteConnection.connect();

        if (conn == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }

        System.out.println("Existing classes:");
        String sql = "SELECT id, name FROM classes";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Enter class ID:");
        int classId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Classes selectedClass = null;
        sql = "SELECT id, name FROM classes WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                selectedClass = new Classes(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (selectedClass == null) {
            System.out.println("Class not found.");
            return;
        }

        while (true) {
            System.out.println("Choisissez une option :");
            System.out.println("1. Ajoutez les notes");
            System.out.println("2. Modifier les notes");
            System.out.println("3. Supprimer les notes");
            System.out.println("4. Voir les notes");
            System.out.println("5. Retour au menu principal");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addGrade(conn, scanner, selectedClass);
                    break;
                case 2:
                    updateGrade(conn, scanner, selectedClass);
                    break;
                case 3:
                    deleteGrade(conn, scanner, selectedClass);
                    break;
                case 4:
                    viewGrades(conn, selectedClass);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }

    private static void addGrade(Connection conn, Scanner scanner, Classes selectedClass) {
        System.out.println("Étudiant dans la classe " + selectedClass.getName() + ":");
        String sql = "SELECT id, lastName, firstName FROM students WHERE class_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selectedClass.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("lastName") + " " + rs.getString("firstName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Enter student ID:");
        int studentId = scanner.nextInt();
        System.out.println("Enter course ID:");
        int courseId = scanner.nextInt();
        System.out.println("Enter grade:");
        double grade = scanner.nextDouble();

        sql = "INSERT INTO grades(student_id, course_id, grade) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setDouble(3, grade);
            pstmt.executeUpdate();
            System.out.println("Grade added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateGrade(Connection conn, Scanner scanner, Classes selectedClass) {
        System.out.println("Enter grade ID:");
        int gradeId = scanner.nextInt();
        System.out.println("Enter new grade:");
        double grade = scanner.nextDouble();

        String sql = "UPDATE grades SET grade = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, grade);
            pstmt.setInt(2, gradeId);
            pstmt.executeUpdate();
            System.out.println("Grade updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteGrade(Connection conn, Scanner scanner, Classes selectedClass) {
        System.out.println("Enter grade ID:");
        int gradeId = scanner.nextInt();

        String sql = "DELETE FROM grades WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gradeId);
            pstmt.executeUpdate();
            System.out.println("Grade deleted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewGrades(Connection conn, Classes selectedClass) {
        String sql = "SELECT * FROM grades WHERE student_id IN (SELECT id FROM students WHERE class_id = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selectedClass.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Student ID: " + rs.getInt("student_id") +
                        ", Course ID: " + rs.getInt("course_id") +
                        ", Grade: " + rs.getDouble("grade"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}