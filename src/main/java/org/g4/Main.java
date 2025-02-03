package org.g4;
import java.util.Scanner;

import static org.g4.controller.importCSV.importCSV;
import static org.g4.controller.manageClasses.manageClasses;
import static org.g4.controller.manageCours.manageCourses;
import static org.g4.controller.manageNotes.manageGrades;
import static org.g4.controller.readCSV.readCSV;

public class Main {
    public static void main(String[] args) {
        CreateTable.main(new String[]{});

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choisissez une option:");
            System.out.println("1. Lire un fichier CSV");
            System.out.println("2. Importer un fichier CSV");
            System.out.println("3. Gérer les classes");
            System.out.println("4. Gérer les cours");
            System.out.println("5. Gérer les notes");
            System.out.println("6. Quitter");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    readCSV();
                    break;
                case 2:
                    importCSV();
                    break;
                case 3:
                    manageClasses();
                    break;
                case 4:
                    manageCourses();
                    break;
                case 5:
                    manageGrades();
                    break;
                case 6:
                    System.out.println("Quitter...");
                    return;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }
}