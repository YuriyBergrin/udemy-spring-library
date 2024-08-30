package com.gmail.bergrin.dao;

import java.util.List;
import java.util.Optional;

import com.gmail.bergrin.model.Book;
import com.gmail.bergrin.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookDao {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public BookDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Book> index() {
    return jdbcTemplate.query("SELECT * FROM book ORDER BY id", new BeanPropertyRowMapper<>(Book.class));
  }

  public Book show(int id) {
    return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new BeanPropertyRowMapper<>(Book.class), id)
        .stream().findAny().orElse(null);
  }

  public Optional<Book> show(String name) {
    return jdbcTemplate.query("SELECT * FROM book WHERE name = ?", new BeanPropertyRowMapper<>(Book.class), name)
        .stream().findAny();
  }

  public void create(Book book) {
    jdbcTemplate.update("INSERT INTO book(name, publish_year) VALUES(?,?)",
        book.getName(),
        book.getPublishYear());
  }

  public void update(int id, Book updatedBook) {
    jdbcTemplate.update("UPDATE book SET name = ?, publish_year = ? WHERE id = ?",
        updatedBook.getName(),
        updatedBook.getPublishYear(),
        id);
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM book where id = ?", id);
  }

  public void assign(int id, Person person) {
    jdbcTemplate.update("UPDATE book set person_id = ? WHERE id = ?", person.getId(), id);
  }

  public void release(int id) {
    jdbcTemplate.update("UPDATE book set person_id = null WHERE id = ?", id);
  }

  public Optional<Person> getBookOwner(int id) {
    return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.id " +
            "WHERE book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
        .stream().findAny();
  }
}
