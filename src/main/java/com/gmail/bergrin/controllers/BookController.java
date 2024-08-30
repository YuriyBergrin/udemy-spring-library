package com.gmail.bergrin.controllers;

import java.util.Optional;

import com.gmail.bergrin.dao.BookDao;
import com.gmail.bergrin.dao.PersonDao;
import com.gmail.bergrin.model.Book;
import com.gmail.bergrin.model.Person;
import com.gmail.bergrin.validator.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

  private final BookDao bookDao;
  private final PersonDao personDao;
  private final BookValidator bookValidator;

  @Autowired
  public BookController(BookDao bookDao, PersonDao personDao, BookValidator bookValidator) {
    this.bookDao = bookDao;
    this.personDao = personDao;
    this.bookValidator = bookValidator;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("books", bookDao.index());
    return "/books/index";
  }

  @GetMapping("/new")
  public String add(@ModelAttribute("book") Book book) {
    return "books/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
    bookValidator.validate(book, bindingResult);
    if (bindingResult.hasErrors()) {
      return "books/new";
    }
    bookDao.create(book);
    return "redirect:/books";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
    model.addAttribute("book", bookDao.show(id));

    Optional<Person> bookOwner = bookDao.getBookOwner(id);

    if (bookOwner.isPresent())
      model.addAttribute("owner", bookOwner.get());
    else
      model.addAttribute("people", personDao.index());

    return "books/show";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable("id") int id, Model model) {
    model.addAttribute("book", bookDao.show(id));
    return "books/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                       @PathVariable("id") int id) {
    bookValidator.validate(book, bindingResult);
    if (bindingResult.hasErrors()) {
      return "books/edit";
    }
    bookDao.update(id, book);
    return "redirect:/books";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    bookDao.delete(id);
    return "redirect:/books";
  }

  @PatchMapping("/{id}/assign")
  public String makePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
    bookDao.assign(id, person);
    return "redirect:/books/" + id;
  }

  @PatchMapping("/{id}/release")
  public String release(@PathVariable("id") int id) {
    bookDao.release(id);
    return "redirect:/books/" + id;
  }
}
