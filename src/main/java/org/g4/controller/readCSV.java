package org.g4.controller;

import org.g4.CSVReader;
import org.g4.SQLiteConnection;
import org.g4.models.Student;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class readCSV {
    public static void readCSV() {
        Connection conn = SQLiteConnection.connect();
        if (conn != null) {
            CSVReader csvReader = new CSVReader();
            try {
                List<Student> students = csvReader.readStudentsFromCSV("src/main/resources/students.csv");
                for (Student student : students) {
                    System.out.println("Lastname: " + student.getLastName() + " Firstname: " + student.getFirstName() + ", Age: " + student.getAge() + ", Email: " + student.getEmail());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}