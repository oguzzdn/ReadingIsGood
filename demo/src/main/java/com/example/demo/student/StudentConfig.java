package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class StudentConfig {

    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {

            Student meriam= new Student(
                    "Meriam", "meriam@test.com",
                    LocalDate.of(2000, Month.APRIL, 5)
            );
            Student alex= new Student(
                    "Alex", "alex@test.com",
                    LocalDate.of(2011, Month.APRIL, 5)
            );
            repository.saveAll(
                    List.of(meriam,alex)
            );
        };
    }
}
