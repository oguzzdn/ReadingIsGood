package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class BookServiceTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;


    @Test
    @Transactional
    public void testCreateBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("The Great Gatsby");
        bookDTO.setAuthor("F. Scott Fitzgerald");
        bookDTO.setDescription("A novel about the decadence and excess of the Roaring Twenties.");
        bookDTO.setPrice(15.99);
        bookDTO.setStock(10);

        ResponseDTO responseDTO = bookService.createBook(bookDTO);
        BookDTO dto = (BookDTO) responseDTO.getData();
        assertNotNull(responseDTO);
        assertEquals("Book Successfully Created", responseDTO.getMessage());
        assertEquals("200", responseDTO.getStatus());
    }
    @Test
    @Transactional
    public void testFailCreateBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("");
        bookDTO.setAuthor("");
        bookDTO.setDescription(null);
        bookDTO.setPrice(0);
        bookDTO.setStock(-10);

        ResponseDTO responseDTO = bookService.createBook(bookDTO);
        BookDTO dto = (BookDTO) responseDTO.getData();
        assertNotNull(responseDTO);
        assertEquals("Invalid book details!", responseDTO.getMessage());
        assertEquals("500", responseDTO.getStatus());
    }
    @Test
    @Transactional
    public void isBookExist() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("The Great Gatsby");
        bookDTO.setAuthor("F. Scott Fitzgerald");
        bookDTO.setDescription("A novel about the decadence and excess of the Roaring Twenties.");
        bookDTO.setPrice(15.99);
        bookDTO.setStock(10);

        ResponseDTO responseDTO = bookService.createBook(bookDTO);
        BookDTO dto = (BookDTO) responseDTO.getData();
        assertNotNull(responseDTO);
        boolean result = bookRepository.existsById(dto.getId());
        Assert.assertTrue(result);
    }

    @Test
    @Transactional
    public void testGetAllBooks() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("The Great Gatsby");
        bookDTO.setAuthor("F. Scott Fitzgerald");
        bookDTO.setDescription("A novel about the decadence and excess of the Roaring Twenties.");
        bookDTO.setPrice(15.99);
        bookDTO.setStock(10);
        List<BookDTO> bookDTOs = bookService.getAllBooks();
        int previousCount = bookDTOs == null ? 0 : bookDTOs.size();
        ResponseDTO responseDTO = bookService.createBook(bookDTO);
        bookDTOs = bookService.getAllBooks();
        assertNotNull(bookDTOs);
        assertEquals(previousCount+1, bookDTOs.size());
    }

    @Test
    public void updateBookStock() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("The Great Gatsby");
        bookDTO.setAuthor("F. Scott Fitzgerald");
        bookDTO.setDescription("A novel about the decadence and excess of the Roaring Twenties.");
        bookDTO.setPrice(15.99);
        bookDTO.setStock(10);

        ResponseDTO responseDTO = bookService.createBook(bookDTO);
        BookDTO dto = (BookDTO) responseDTO.getData();
        ResponseDTO updatedResponseDTO =bookService.updateBookStock(dto.getId(),20);
        BookDTO updatedDto = (BookDTO) updatedResponseDTO.getData();
        assertEquals("Book updated successfully", updatedResponseDTO.getMessage());
        assertEquals("200", updatedResponseDTO.getStatus());
        int newStock=updatedDto.getStock()==null? 0:updatedDto.getStock();
        assertEquals(20, newStock);
    }
    @Test
    public void updateNonExistingBookStock() throws Exception {
        boolean result = bookRepository.existsById(-1l);
        Assert.assertFalse(result);
        try {
            bookService.updateBookStock(-1l,20);
        }catch (Exception e){
            assertEquals(e.getMessage(),"Book not found with id: -1");
        }
    }



}
