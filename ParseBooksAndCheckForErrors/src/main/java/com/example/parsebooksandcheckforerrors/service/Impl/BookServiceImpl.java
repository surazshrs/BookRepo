package com.example.parsebooksandcheckforerrors.service.Impl;

import com.example.parsebooksandcheckforerrors.model.Book;
import com.example.parsebooksandcheckforerrors.model.Category;
import com.example.parsebooksandcheckforerrors.model.Error;
import com.example.parsebooksandcheckforerrors.service.BookService;
import com.example.parsebooksandcheckforerrors.service.ErrorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  private ErrorService errorService;

  public BookServiceImpl(ErrorService errorService) {
    this.errorService = errorService;
  }

  private Error createError(String errorMessage, Integer errorCode, Integer errorId) {
    return Error.builder().errorMessage(errorMessage).errorCode(errorCode).errorId(errorId).build();
  }

  public void addNewCategory(
      String bookName,
      String authorName,
      String categoryName,
      List<Category> categories,
      Map<String, Category> categoryMap) {
    Book book =
        Book.builder().navn(bookName).forfattere(new ArrayList<>(List.of(authorName))).build();
    Category category =
        Category.builder()
            .kategori(categoryName)
            .antall(1)
            .bøker(new ArrayList<>(List.of(book)))
            .build();
    categories.add(category);
    categoryMap.put(categoryName, category);
  }

  @Override
  public void addBook(String authorName, String bookName, Category existingCategory) {
    existingCategory
        .getBøker()
        .add(
            Book.builder().navn(bookName).forfattere(new ArrayList<>(List.of(authorName))).build());
    existingCategory.setAntall(existingCategory.getAntall() + 1);
  }

  @Override
  public void checkMultipleAuthorsForSameBook(
      Map<String, List<String>> bookAuthorMap, String authorName, String bookName) {
    if (bookAuthorMap.containsKey(bookName)) {
      List<String> authorsForSameBook = bookAuthorMap.get(bookName);
      if (authorsForSameBook.contains(authorName)) {
        String errorMessage =
            String.format(
                "Author %s is mentioned multiple times for the same book: %s",
                authorName, bookName);
        Integer errorCode = 1001;
        Integer errorId = 1;
        errorService.addError(createError(errorMessage, errorCode, errorId));
        System.out.println(errorMessage);
      }
      authorsForSameBook.add(authorName);
    } else {
      bookAuthorMap.put(bookName, new ArrayList<>(List.of(authorName)));
    }
  }

  @Override
  public void checkBookInMultipleCategories(
      Map<String, List<String>> bookCategoryMap,
      String authorName,
      String bookName,
      String categoryName) {
    String authorAndBookNames = authorName + "|" + bookName;
    if (bookCategoryMap.containsKey(authorAndBookNames)) {
      List<String> categoriesWithSameBook = bookCategoryMap.get(authorAndBookNames);
      if (!categoriesWithSameBook.contains(categoryName)) {
        String errorMessage =
            String.format(
                "Book %s by author %s is present in multiple categories: %s, %s",
                bookName, authorName, categoryName, categoriesWithSameBook.get(0));
        Integer errorCode = 1002;
        Integer errorId = 2;
        errorService.addError(createError(errorMessage, errorCode, errorId));
        System.out.println(errorMessage);
      }
      categoriesWithSameBook.add(categoryName);
    } else {
      bookCategoryMap.put(authorAndBookNames, new ArrayList<>(List.of(categoryName)));
    }
  }
}
