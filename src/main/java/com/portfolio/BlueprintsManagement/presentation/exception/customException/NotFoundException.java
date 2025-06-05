package com.portfolio.BlueprintsManagement.presentation.exception.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends Exception {

    private String message;
}
