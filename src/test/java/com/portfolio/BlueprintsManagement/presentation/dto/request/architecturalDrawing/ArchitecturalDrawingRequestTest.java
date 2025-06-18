package com.portfolio.BlueprintsManagement.presentation.dto.request.architecturalDrawing;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArchitecturalDrawingRequestTest {

    private ArchitecturalDrawingRequest architecturalDrawingRequest;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    public void before() {
        String blueprintId = "10000000-0000-1000-8000-000000000001";
        String createdAt = "2025-01-01";
        byte[] dummyImageBytes = new byte[100];
        MockMultipartFile mockImage = new MockMultipartFile("imageFile", "image.png", "image/png", dummyImageBytes);
        architecturalDrawingRequest = new ArchitecturalDrawingRequest(blueprintId, createdAt, mockImage);
    }

    @Nested
    class ValidBlueprintIdTest {

        @ParameterizedTest
        @ValueSource(strings = {"00000000-0000-1000-8000-000000000000", "ffffffff-ffff-5fff-bfff-ffffffffffff"})
        void 図面idがUUID形式に適している場合正常に処理が実行されること(String blueprintId) {
            architecturalDrawingRequest.setBlueprintId(blueprintId);
            Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);

            assertEquals(0, violations.size());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "1", "abc", ""})
        void 図面idがUUID形式に適していない場合バリデーションチェックされること(String blueprintId) {
            architecturalDrawingRequest.setBlueprintId(blueprintId);
            Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("idの形式が正しくありません", actual);
        }
    }

    @Nested
    class ValidCreatedAtTest {

        @ParameterizedTest
        @ValueSource(strings = {"0000-01-01", "9999-12-31"})
        void 作成日が適切な文字列の場合にオブジェクトが作成されること(String createdAt) {
            architecturalDrawingRequest.setCreatedAt(createdAt);
            Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);

            assertEquals(0, violations.size());
        }

        @ParameterizedTest
        @ValueSource(strings = {"000-01-01", "9999-12-311"})
        void 作成日が適切ではない文字列の場合にバリデーションチェックがかかること(String createdAt) {
            architecturalDrawingRequest.setCreatedAt(createdAt);
            Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力された日付の形式が正しくありません", actual);
        }

        @Test
        void 作成日が空文字の場合にバリデーションチェックがかかること() {
            String createdAt = "";
            architecturalDrawingRequest.setCreatedAt(createdAt);
            Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }

        @Test
        void 作成日がnullの場合にバリデーションチェックがかかること() {
            String createdAt = null;
            architecturalDrawingRequest.setCreatedAt(createdAt);
            Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }
    }

    @Nested
    class ValidFileTest {

        @Test
        void ファイルサイズが5MBを超える場合バリデーションチェックがかかること() {
            byte[] dummyImageBytes = new byte[6 * 1024 * 1024];
            MockMultipartFile mockImage = new MockMultipartFile("imageFile", "image.png", "image/png", dummyImageBytes);
            architecturalDrawingRequest.setImageFile(mockImage);
            Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("ファイルサイズは5MBまでにしてください", actual);
        }

        @Test
        void ファイル形式が異なる場合バリデーションチェックがかかること() {
            byte[] dummyFileBytes = new byte[100];
            MockMultipartFile mockFile = new MockMultipartFile("imageFile", "text.txt", "text/plain", dummyFileBytes);
            architecturalDrawingRequest.setImageFile(mockFile);
            Set<ConstraintViolation<ArchitecturalDrawingRequest>> violations = validator.validate(architecturalDrawingRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("画像ファイルを選択してください", actual);
        }
    }

}
