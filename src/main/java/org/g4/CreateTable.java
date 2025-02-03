package org.g4;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTable {
    public static void main(String[] args) {
        String student = "CREATE TABLE IF NOT EXISTS students (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    lastName TEXT NOT NULL,\n"
                + "    firstName TEXT NOT NULL,\n"
                + "    age INTEGER NOT NULL,\n"
                + "    email TEXT NOT NULL,\n"
                + "    class_id INTEGER,\n"
                + "    FOREIGN KEY (class_id) REFERENCES classes(id)\n"
                + ");";

        String cours = "CREATE TABLE IF NOT EXISTS courses (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    name TEXT NOT NULL\n"
                + ");";

        String grade = "CREATE TABLE IF NOT EXISTS grades (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    student_id INTEGER NOT NULL,\n"
                + "    course_id INTEGER NOT NULL,\n"
                + "    grade REAL NOT NULL,\n"
                + "    FOREIGN KEY (student_id) REFERENCES students(id),\n"
                + "    FOREIGN KEY (course_id) REFERENCES courses(id)\n"
                + ");";

        String studentCours = "CREATE TABLE IF NOT EXISTS student_courses (\n"
                + "    student_id INTEGER NOT NULL,\n"
                + "    course_id INTEGER NOT NULL,\n"
                + "    PRIMARY KEY (student_id, course_id),\n"
                + "    FOREIGN KEY (student_id) REFERENCES students(id),\n"
                + "    FOREIGN KEY (course_id) REFERENCES courses(id)\n"
                + ");";

        String classes = "CREATE TABLE IF NOT EXISTS classes (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    name TEXT NOT NULL\n"
                + ");";

        try (Connection conn = SQLiteConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(student);
            stmt.execute(cours);
            stmt.execute(grade);
            stmt.execute(studentCours);
            stmt.execute(classes);
            System.out.println("Tables have been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}