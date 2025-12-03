package by.alex.spring.controllers;

import by.alex.spring.dao.BookDAO;
import by.alex.spring.models.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String allBooks(Model model) {
        model.addAttribute("books", bookDAO.allBook());
        return "books/all";
    }

    @GetMapping("/{id}")
    public String bookById(@PathVariable("id") int id, Model model) {
        model.addAttribute("bookById", bookDAO.bookById(id));
        return "books/bookById";
    }

    @GetMapping("/new")
    public String newFormBook(@ModelAttribute("newBook") Book book) {
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("newBook") @Valid Book book,
                         BindingResult bindingResult) {
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
}
