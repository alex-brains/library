package by.alex.spring.controllers;

import by.alex.spring.dao.PersonDAO;
import by.alex.spring.models.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String allPeople(Model model) {
        model.addAttribute("people", personDAO.allPeople());
        return "people/all";
    }

    @GetMapping("/{id}")
    public String personById(@PathVariable("id") int id, Model model) {
        model.addAttribute("personById", personDAO.personById(id));
        return "people/personById";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("newPerson") Person person) {
        return "people/new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("newPerson") @Valid Person person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("oldPerson", personDAO.personById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("oldPerson") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
