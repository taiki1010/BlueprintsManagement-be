package com.portfolio.BlueprintsManagement.presentation.dto.request.site;

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

class SiteRequestTest {

    private SiteRequest siteRequest;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    public void before() {
        String name = "佐藤邸";
        String address = "東京都表参道";
        String remark = "";
        siteRequest = new SiteRequest(name, address, remark);
    }

    @Nested
    class ValidSiteNameTest {

        @ParameterizedTest
        @ValueSource(strings = {"邸",
                "ああああああああああああああああああああああああああああああああああああああああああああああああああ"})
        void 現場名が1文字以上50文字以内の場合にオブジェクトが作成されること(String name) {
            siteRequest.setName(name);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            int length = siteRequest.getName().length();
            boolean isBool = 1 <= length & length <= 50;

            assertTrue(isBool);
            assertEquals(0, violations.size());
        }

        @Test
        void 現場名が50文字を超えた場合にバリデーションエラーになること() {
            String name = "あああああああああああああああああああああああああああああああああああああああああああああああああああ";
            siteRequest.setName(name);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            int length = siteRequest.getName().length();
            boolean isBool = 50 < length;
            String actual = violations.iterator().next().getMessage();

            assertTrue(isBool);
            assertEquals(1, violations.size());
            assertEquals("現場名は50文字以内で入力してください", actual);
        }

        @Test
        void 現場名が空文字だった場合にバリデーションエラーになること() {
            String name = "";
            siteRequest.setName(name);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }

        @Test
        void 現場名がnullだった場合にバリデーションエラーになること() {
            String name = null;
            siteRequest.setName(name);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }

    }

    @Nested
    class ValidAddressTest {

        @ParameterizedTest
        @ValueSource(strings = {"県",
                "あああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ"})
        void 住所名が161文字以内の場合にオブジェジェクトが作成されること(String address) {
            siteRequest.setAddress(address);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            int length = siteRequest.getAddress().length();
            boolean isBool = 1 <= length & length <= 161;

            assertTrue(isBool);
            assertEquals(0, violations.size());
        }

        @Test
        void 住所名が161文字を超えた場合バリデーションチェックがかかること() {
            String address = "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ";
            siteRequest.setAddress(address);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            int length = siteRequest.getAddress().length();
            boolean isBool = 161 < length;
            String actual = violations.iterator().next().getMessage();

            assertTrue(isBool);
            assertEquals(1, violations.size());
            assertEquals("住所は161文字以内で入力してください", actual);
        }

        @Test
        void 現場名が空文字だった場合にバリデーションエラーになること() {
            String address = "";
            siteRequest.setAddress(address);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }

        @Test
        void 現場名がnullだった場合にバリデーションエラーになること() {
            String address = null;
            siteRequest.setAddress(address);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            String actual = violations.iterator().next().getMessage();

            assertEquals(1, violations.size());
            assertEquals("入力欄が空です", actual);
        }
    }

    @Nested
    class ValidRemarkTest {

        @ParameterizedTest
        @ValueSource(strings = {"", "あ",
                "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ"})
        void 備考が200文字以内または空の場合にオブジェジェクトが作成されること(String remark) {
            siteRequest.setRemark(remark);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            int length = siteRequest.getAddress().length();
            boolean isBool = 1 <= length & length <= 200;

            assertTrue(isBool);
            assertEquals(0, violations.size());
        }

        @Test
        void 備考が200文字を超えた場合バリデーションチェックがかかること() {
            String remark = "あああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ";
            siteRequest.setRemark(remark);
            Set<ConstraintViolation<SiteRequest>> violations = validator.validate(siteRequest);
            int length = siteRequest.getRemark().length();
            boolean isBool = 200 < length;
            String actual = violations.iterator().next().getMessage();

            assertTrue(isBool);
            assertEquals(1, violations.size());
            assertEquals("備考欄は200文字以内で入力してください", actual);
        }
    }

}
