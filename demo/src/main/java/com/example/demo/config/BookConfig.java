package com.example.demo.config;

import com.example.demo.entity.BookEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class BookConfig {
    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
        return args -> {
            BookEntity harryPotterAndPhilosophersStone = new BookEntity("Harry Potter And Philosophers Stone", "J.K.Rowling", "The first book in the Harry Potter series, following young wizard Harry Potter as he discovers his magical heritage and attends Hogwarts School of Witchcraft and Wizardry.", BigDecimal.valueOf(50l),5);

            BookEntity harryPotterAndChamberOfSecrets = new BookEntity("Harry Potter And Chamber Of Secrets", "J.K.Rowling", "In the second book of the Harry Potter series, Harry returns to Hogwarts for his second year, only to find that a mysterious force is attacking students and petrifying them.", BigDecimal.valueOf(55l),5);

            BookEntity harryPotterAndPrisonerOfAzkaban = new BookEntity("Harry Potter And Prisoner Of Azkaban", "J.K.Rowling", "The third book in the Harry Potter series finds Harry, Ron, and Hermione facing a new threat in the form of Sirius Black, an escaped prisoner from the wizarding prison Azkaban.", BigDecimal.valueOf(55l),5);

            BookEntity harryPotterAndGobletOfFire = new BookEntity("Harry Potter And Goblet Of Fire", "J.K.Rowling", "The fourth book in the Harry Potter series sees Harry competing in the Triwizard Tournament, a dangerous contest between Hogwarts and two other wizarding schools.", BigDecimal.valueOf(70l),5);

            BookEntity harryPotterAndOrderOfPhoenix = new BookEntity("Harry Potter And Order Of Phoenix", "J.K.Rowling", "In the fifth book of the Harry Potter series, Harry must contend with new challenges at Hogwarts, including a new Defense Against the Dark Arts teacher who seems to have it out for him.", BigDecimal.valueOf(40l),5);

            BookEntity harryPotterAndHalfBloodPrince = new BookEntity("Harry Potter And Half-Blood Prince", "J.K.Rowling", "As Harry prepares for his final battle with Voldemort, he delves deeper into the wizarding world's past and learns the truth about his enemy's rise to power.", BigDecimal.valueOf(50l),5);

            BookEntity harryPotterAndDeathlyHallows = new BookEntity("Harry Potter And Deathly Hallows", "J.K.Rowling", "The final book in the Harry Potter series sees Harry, Ron, and Hermione on a dangerous quest to find and destroy the remaining Horcruxes", BigDecimal.valueOf(100l),5);

            bookRepository.saveAll(
                    List.of(harryPotterAndPhilosophersStone,harryPotterAndGobletOfFire,harryPotterAndChamberOfSecrets,harryPotterAndPrisonerOfAzkaban,harryPotterAndOrderOfPhoenix,harryPotterAndHalfBloodPrince,harryPotterAndDeathlyHallows)
            );

        };
    }

}
