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
public class PersonDao {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PersonDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Person> index() {
    return jdbcTemplate.query("SELECT * FROM person ORDER BY id", new BeanPropertyRowMapper<>(Person.class));
  }

  public Person show(int id) {
    return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id)
        .stream().findAny().orElse(null);
  }

  public Optional<Person> show(String name) {
    return jdbcTemplate.query("SELECT * FROM person WHERE name = ?", new BeanPropertyRowMapper<>(Person.class), name)
        .stream().findAny();
  }

  public void create(Person person) {
    jdbcTemplate.update("INSERT INTO person(name, birth_year) VALUES(?,?)",
        person.getName(),
        person.getBirthYear());
  }

  public void update(int id, Person updatedPerson) {
    jdbcTemplate.update("UPDATE person SET name = ?, birth_year = ? WHERE id = ?",
        updatedPerson.getName(),
        updatedPerson.getBirthYear(),
        id);
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM person where id = ?", id);
  }

  public List<Book> getBooksByPersonId(int id) {
  return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new Object[]{id},
      new BeanPropertyRowMapper<>(Book.class));
  }
}
