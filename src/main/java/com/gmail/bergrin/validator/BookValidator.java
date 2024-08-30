package com.gmail.bergrin.validator;

import java.util.Optional;

import com.gmail.bergrin.dao.BookDao;
import com.gmail.bergrin.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

  private final BookDao bookDao;

  @Autowired
  public BookValidator(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return Book.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Book targetBook = (Book) target;
    Optional<Book> bookFromDB = bookDao.show(targetBook.getName());
    if (bookFromDB.isPresent() && targetBook.getId() != bookFromDB.get().getId()) {
      errors.rejectValue("name", "", "Book with this name is already exists");
    }
  }
}
