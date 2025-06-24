package com.portfolio.BlueprintsManagement.presentation.controller;

import com.portfolio.BlueprintsManagement.application.service.ArchitecturalDrawingService;
import com.portfolio.BlueprintsManagement.domain.model.architecturalDrawing.ArchitecturalDrawing;
import com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing.ArchitecturalDrawingRequest;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.FailedToPutObjectException;
import com.portfolio.BlueprintsManagement.presentation.exception.customException.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/architecturalDrawings")
public class ArchitecturalDrawingController {

    private final ArchitecturalDrawingService architecturalDrawingService;

    @Operation(
            summary = "図面画像追加",
            description = "新規図面画像を追加します。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ArchitecturalDrawing.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"message\": \"ファイルのアップロードに失敗しました\"}"),
                                    schema = @Schema(implementation = NotFoundException.class))
                    )
            }
    )
    @PostMapping
    public ArchitecturalDrawing addArchitecturalDrawing(
            @ModelAttribute @Valid ArchitecturalDrawingRequest architecturalDrawingRequest)
            throws IOException, FailedToPutObjectException {
        return architecturalDrawingService.addArchitecturalDrawing(architecturalDrawingRequest);
    }
}
