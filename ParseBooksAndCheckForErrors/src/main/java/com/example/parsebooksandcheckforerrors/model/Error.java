package com.example.parsebooksandcheckforerrors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
  private String errorMessage;
  private Integer errorCode;
  private Integer errorId;
}
