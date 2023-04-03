package com.example.demo.repository;

import com.example.demo.DemoApplication;
import com.example.demo.entity.BookEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void createBookTest() {
        BookEntity entity =new BookEntity();
        entity.setTitle("The Great Gatsby");
        entity.setAuthor("F. Scott Fitzgerald");
        entity.setDescription("A novel about the decadence and excess of the Roaring Twenties.");
        entity.setPrice(15.99);
        entity.setStock(10);
        bookRepository.save(entity);
        BookEntity result = bookRepository.getById(entity.getId());
        Assert.assertTrue(result.getId()==entity.getId());
    }
    @Test
    @Transactional
    public void bookExistsTest() {
        BookEntity entity =new BookEntity();
        entity.setTitle("The Great Gatsby");
        entity.setAuthor("F. Scott Fitzgerald");
        entity.setDescription("A novel about the decadence and excess of the Roaring Twenties.");
        entity.setPrice(15.99);
        entity.setStock(10);
        bookRepository.save(entity);
        Assert.assertTrue(bookRepository.existsById(entity.getId()));
    }
    @Test
    @Transactional
    public void findByBookTitle() {
        BookEntity entity =new BookEntity();
        entity.setTitle("The Gatsby");
        entity.setAuthor("F. Scott Fitzgerald");
        entity.setDescription("A novel about the decadence and excess of the Roaring Twenties.");
        entity.setPrice(15.99);
        entity.setStock(10);

        bookRepository.save(entity);

        BookEntity result = bookRepository.findByTitle("The Gatsby");

        assertEquals(result.getTitle(),entity.getTitle());
    }
}
