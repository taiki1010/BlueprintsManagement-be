package com.portfolio.BlueprintsManagement.presentation.controller;

import com.portfolio.BlueprintsManagement.application.service.ArchitecturalDrawingService;
import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.FailedToPutObjectException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/architecturalDrawings")
public class ArchitecturalDrawingController {

    private final ArchitecturalDrawingService architecturalDrawingService;

    @PostMapping
    public ArchitecturalDrawing addArchitecturalDrawing(@ModelAttribute @Valid ArchitecturalDrawingRequest architecturalDrawingRequest) throws IOException, FailedToPutObjectException {
        return architecturalDrawingService.addArchitecturalDrawing(architecturalDrawingRequest);
    }
}
