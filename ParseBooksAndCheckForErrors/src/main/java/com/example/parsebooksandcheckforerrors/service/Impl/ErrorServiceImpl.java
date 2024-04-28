package com.example.parsebooksandcheckforerrors.service.Impl;

import com.example.parsebooksandcheckforerrors.model.Error;
import com.example.parsebooksandcheckforerrors.service.ErrorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ErrorServiceImpl implements ErrorService {
  public void addError(Error error) {
    ObjectMapper objectMapper = new ObjectMapper();
    List<String> errors = new ArrayList<>();
    try {
      String errorsJson = objectMapper.writeValueAsString(error);
      errors.add(errorsJson);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(errors);
  }
}
