package org.g4;

import com.opencsv.bean.CsvToBeanBuilder;
import org.g4.models.Student;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVReader {
    public List<Student> readStudentsFromCSV(String filePath) throws IOException {
        return new CsvToBeanBuilder<Student>(new FileReader(filePath))
                .withType(Student.class)
                .build()
                .parse();
    }
}