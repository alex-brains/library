package by.alex.spring.controllers;

import by.alex.spring.dao.BookDAO;
import by.alex.spring.dao.PersonDAO;
import by.alex.spring.models.Book;
import by.alex.spring.models.Person;
import by.alex.spring.utils.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator validator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator validator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.validator = validator;
    }

    @GetMapping
    public String allBooks(Model model) {
        model.addAttribute("books", bookDAO.allBook());
        return "books/all";
    }

    @GetMapping("/{id}")
    public String bookById(@PathVariable("id") int id, Model model,
                           @ModelAttribute("person") Person person) {
        model.addAttribute("bookById", bookDAO.bookById(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.allPeople());
        }

        return "books/bookById";
    }

    @GetMapping("/new")
    public String newFormBook(@ModelAttribute("newBook") Book book) {
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("newBook") @Valid Book book,
                         BindingResult bindingResult) {
        validator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editFormBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("oldBook", bookDAO.bookById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("oldBook") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        validator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("person") Person selectedPerson) {
        // У selectedPerson назначено только поле id, остальные поля - null
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
