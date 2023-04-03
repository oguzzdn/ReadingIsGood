package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.BookEntity;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseDTO createBook(BookDTO bookDTO) throws Exception {
        if (!bookDTO.isValid())
            return new ResponseDTO("Invalid book details!","500",null);
        BookEntity bookEntity = convertToEntity(bookDTO);
        bookRepository.save(bookEntity);
        return new ResponseDTO("Book Successfully Created", "200",convertToDTO(bookEntity));
    }



    private BookEntity convertToEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookDTO.getId());
        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setDescription(bookDTO.getDescription());
        bookEntity.setPrice(bookDTO.getPrice());
        bookEntity.setStock(bookDTO.getStock());
        bookEntity = bookRepository.save(bookEntity);
        return bookEntity;
    }

    public List<BookDTO> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) throws Exception {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new Exception("Book not found with id: " + id));
        return convertToDTO(bookEntity);
    }


    public ResponseDTO updateBookStock(Long id, Integer quantity) throws Exception {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new Exception("Book not found with id: " + id));
        if (quantity < 0) {
            throw new Exception("Invalid quantity " + quantity);
        }
        bookEntity.setStock(quantity);
        bookRepository.save(bookEntity);
        return new ResponseDTO("Book updated successfully","200",convertToDTO(bookEntity));
    }

    private BookDTO convertToDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setAuthor(bookEntity.getAuthor());
        bookDTO.setDescription(bookEntity.getDescription());
        bookDTO.setPrice(bookEntity.getPrice());
        bookDTO.setStock(bookEntity.getStock());
        return bookDTO;
    }
}
