package com.example.parsebooksandcheckforerrors.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
  private String kategori;
  private Integer antall;
  private List<Book> bøker;
}
