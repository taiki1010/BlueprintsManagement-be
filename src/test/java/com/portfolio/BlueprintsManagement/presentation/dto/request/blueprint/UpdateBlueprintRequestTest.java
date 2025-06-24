package com.portfolio.BlueprintsManagement.presentation.dto.request.blueprint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UpdateBlueprintRequestTest {

    private UpdateBlueprintRequest updateBlueprintRequest;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    public void before() {
        String id = "10000000-0000-1000-8000-000000000001";
        String name = "平面図";
        updateBlueprintRequest = new UpdateBlueprintRequest(id, name);
    }

    @Nested
    class ValidIdTest {

        @ParameterizedTest
        @ValueSource(strings = {"00000000-0000-1000-8000-000000000000",
                "ffffffff-ffff-5fff-bfff-ffffffffffff"})
        void idがUUID形式に適している場合正常に処理が実行されること(String id) {
            updateBlueprintRequest.setId(id);
            Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(
                    updateBlueprintRequest);

            assertEquals(0, violations.size());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "1", "abc", ""})
        void idがUUID形式に適していない場合バリデーションチェックされること(String id) {
            updateBlueprintRequest.setId(id);
            Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(
                    updateBlueprintRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("idの形式が正しくありません", actual);
        }
    }

    @Nested
    class ValidNameTest {

        @ParameterizedTest
        @ValueSource(strings = {"あ", "ああああああああああああああああああああ"})
        void 図面名が1文字以上20文字以内の場合にオブジェクトが作成されること(String name) {
            updateBlueprintRequest.setName(name);
            Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(
                    updateBlueprintRequest);
            int length = updateBlueprintRequest.getName().length();
            boolean isBool = 1 <= length & length <= 20;

            assertTrue(isBool);
            assertEquals(0, violations.size());
        }

        @Test
        void 図面名が20文字を超えた場合にバリデーションエラーになること() {
            String name = "あああああああああああああああああああああ";
            updateBlueprintRequest.setName(name);
            Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(
                    updateBlueprintRequest);
            int length = updateBlueprintRequest.getName().length();
            boolean isBool = 20 < length;
            String actual = violations.iterator().next().getMessage();

            assertTrue(isBool);
            assertEquals(1, violations.size());
            assertEquals("図面名は20文字以内で入力してください", actual);
        }

        @Test
        void 図面名が空文字だった場合にバリデーションエラーになること() {
            String name = "";
            updateBlueprintRequest.setName(name);
            Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(
                    updateBlueprintRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }

        @Test
        void 図面名がnullだった場合にバリデーションエラーになること() {
            String name = null;
            updateBlueprintRequest.setName(name);
            Set<ConstraintViolation<UpdateBlueprintRequest>> violations = validator.validate(
                    updateBlueprintRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }
    }
}
