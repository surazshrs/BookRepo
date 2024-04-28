package com.example.parsebooksandcheckforerrors;

import com.example.parsebooksandcheckforerrors.model.Category;
import com.example.parsebooksandcheckforerrors.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParseBooksAndCheckForErrorsApplication implements CommandLineRunner {
  private BookService bookService;

  public ParseBooksAndCheckForErrorsApplication(BookService bookService) {
    this.bookService = bookService;
  }

  public static void main(String[] args) {
    SpringApplication.run(ParseBooksAndCheckForErrorsApplication.class, args);
  }

  private List<String> initializeAndPopulateBooksList() {
    List<String> booksList = new ArrayList<>();
    booksList.add("John|Testbook1|Horror");
    booksList.add("Mark|Testbook2|Romance");
    booksList.add("Ankit|Testbook1|Horror");
    booksList.add("Joe|Testbook4|Comedy");
    booksList.add("Sharon|Testbook5|Horror");
    booksList.add("Rita|Testbook5|Horror");
    booksList.add("Soni|Testbook7|Romance");
    booksList.add("Soni|Testbook7|Horror");
    booksList.add("Pawan|Testbook9|Fiction");
    booksList.add("Ram|Testbook9|Horror");
    return booksList;
  }

  @Override
  public void run(String... args) {
    try {
      // Create a list of string items for booksList and populate it
      List<String> booksList = initializeAndPopulateBooksList();

      // Declare categories arrayList we use arrayList since they are dynamic and size can increase
      // on decrease dynamically than arrays
      List<Category> categories = new ArrayList<>();
      Map<String, Category> categoryMap = new HashMap<>();
      Map<String, List<String>> bookCategoryMap = new HashMap<>();
      Map<String, List<String>> bookAuthorMap = new HashMap<>();
      // loop through booksList arrayList and substring
      checkBooksAndAuthors(booksList, categories, categoryMap, bookCategoryMap, bookAuthorMap);
      // use jackson here to covert object to json
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        String jsonOutputFormat = objectMapper.writeValueAsString(categories);
        System.out.println(jsonOutputFormat);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }

    } catch (Exception ex) {
      System.out.println("Throwing an exception here:" + ex);
    }
  }

  private void checkBooksAndAuthors(
      List<String> booksList,
      List<Category> categories,
      Map<String, Category> categoryMap,
      Map<String, List<String>> bookCategoryMap,
      Map<String, List<String>> bookAuthorMap) {
    for (String bookItem : booksList) {
      String[] subString = bookItem.split("\\|");
      // catch any invalid strings in the list to avoid arrayIndexOutOfBounds exceptions
      if (subString.length < 3) {
        System.out.println("Invalid item in the booksList: " + bookItem);
        continue;
      }
      String authorName = subString[0];
      String bookName = subString[1];
      String categoryName = subString[2];

      // Check if the same book is present in multiple categories
      bookService.checkBookInMultipleCategories(
          bookCategoryMap, authorName, bookName, categoryName);

      // Check if the author is mentioned as the author of the same book more than once
      bookService.checkMultipleAuthorsForSameBook(bookAuthorMap, authorName, bookName);

      // Check if the category already exists
      Category existingCategory = categoryMap.get(categoryName);
      // If the category doesn't exist, create a new one
      if (existingCategory == null) {
        bookService.addNewCategory(bookName, authorName, categoryName, categories, categoryMap);
      } else {
        // If the category exists, add the book to its list of books
        bookService.addBook(authorName, bookName, existingCategory);
      }
    }
  }
}
