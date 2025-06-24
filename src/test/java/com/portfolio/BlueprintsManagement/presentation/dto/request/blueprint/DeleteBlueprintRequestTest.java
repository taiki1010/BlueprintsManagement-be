package com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DeleteBlueprintRequestTest {

    private DeleteBlueprintRequest deleteBlueprintRequest;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    public void before() {
        String id = "00000000-0000-1000-8000-000000000001";
        String blueprintId = "10000000-0000-1000-8000-000000000001";
        String createdAt = "2025-01-01";
        String filePath = blueprintId + "/hoge.png";
        deleteBlueprintRequest = new DeleteBlueprintRequest(id, blueprintId, createdAt, filePath);
    }

    @Nested
    class ValidIdTest {

        @ParameterizedTest
        @ValueSource(strings = {"00000000-0000-1000-8000-000000000000",
                "ffffffff-ffff-5fff-bfff-ffffffffffff"})
        void idがUUID形式に適している場合正常に処理が実行されること(String id) {
            deleteBlueprintRequest.setId(id);
            Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(
                    deleteBlueprintRequest);

            assertEquals(0, violations.size());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "1", "abc", ""})
        void idがUUID形式に適していない場合バリデーションチェックされること(String id) {
            deleteBlueprintRequest.setId(id);
            Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(
                    deleteBlueprintRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("idの形式が正しくありません", actual);
        }
    }

    @Nested
    class ValidBlueprintIdTest {

        @ParameterizedTest
        @ValueSource(strings = {"00000000-0000-1000-8000-000000000000",
                "ffffffff-ffff-5fff-bfff-ffffffffffff"})
        void idがUUID形式に適している場合正常に処理が実行されること(String id) {
            deleteBlueprintRequest.setBlueprintId(id);
            Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(
                    deleteBlueprintRequest);

            assertEquals(0, violations.size());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "1", "abc", ""})
        void idがUUID形式に適していない場合バリデーションチェックされること(String id) {
            deleteBlueprintRequest.setBlueprintId(id);
            Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(
                    deleteBlueprintRequest);
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
            deleteBlueprintRequest.setCreatedAt(createdAt);
            Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(
                    deleteBlueprintRequest);

            assertEquals(0, violations.size());
        }

        @ParameterizedTest
        @ValueSource(strings = {"000-01-01", "9999-12-311"})
        void 作成日が適切ではない文字列の場合にバリデーションチェックがかかること(
                String createdAt) {
            deleteBlueprintRequest.setCreatedAt(createdAt);
            Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(
                    deleteBlueprintRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力された日付の形式が正しくありません", actual);
        }

        @Test
        void 作成日が空文字の場合にバリデーションチェックがかかること() {
            String createdAt = "";
            deleteBlueprintRequest.setCreatedAt(createdAt);
            Set<ConstraintViolation<DeleteBlueprintRequest>> violations = validator.validate(
                    deleteBlueprintRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }
    }


}
