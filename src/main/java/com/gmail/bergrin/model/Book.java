package com.gmail.bergrin.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Book {

  private int id;
  private Integer personId;
  @NotEmpty(message = "Name should not be empty")
  @Size(min = 1, max = 200, message = "Name should consist of 1 and 200 characters")
  private String name;
  @NotNull(message = "Publish year could not be empty")
  @Min(value = 1900, message = "Publish year could  be > 1900")
  private Integer publishYear;
}
