package com.example.parsebooksandcheckforerrors.service;

import com.example.parsebooksandcheckforerrors.model.Category;
import java.util.List;
import java.util.Map;

public interface BookService {
  void addNewCategory(
      String bookName,
      String authorName,
      String categoryName,
      List<Category> categories,
      Map<String, Category> categoryMap);

  void addBook(String authorName, String bookName, Category existingCategory);

  void checkMultipleAuthorsForSameBook(
      Map<String, List<String>> bookAuthorMap, String authorName, String bookName);

  void checkBookInMultipleCategories(
      Map<String, List<String>> bookCategoryMap,
      String authorName,
      String bookName,
      String categoryName);
}
