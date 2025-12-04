package by.alex.spring.utils;

import by.alex.spring.dao.BookDAO;
import by.alex.spring.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        bookDAO.bookByName(book.getBookName()).ifPresent(existing -> {
            if (!Objects.equals(existing.getId(), book.getId())) {
                errors.rejectValue("bookName", "", "Book's name is already exist");
            }
        });
    }
}
