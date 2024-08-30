package com.gmail.bergrin.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Person {

  private int id;
  @NotEmpty(message = "Name should not be empty")
  @Size(min = 1, max = 200, message = "Name should consist of 1 and 200 characters")
  private String name;
  @NotNull(message = "Birth year could not be empty")
  @Min(value = 1900, message = "Birth year could  be > 1900")
  private Integer birthYear;
}
