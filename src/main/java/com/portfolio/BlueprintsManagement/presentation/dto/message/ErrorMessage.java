package com.portfolio.BlueprintsManagement.presentation.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    NOT_FOUND_SITES("現場が追加されていません"),
    NOT_FOUND_SITE_BY_ID("idに該当する現場が存在しません"),
    NOT_FOUND_BLUEPRINT_BY_SITE_ID("現場idに該当する図面情報が存在しません"),
    NOT_FOUND_BLUEPRINT_BY_ID("idに該当する図面情報が存在しません"),
    ID_MUST_BE_UUID("idの形式が正しくありません"),
    CHAR_COUNT_SITE_NAME_TOO_LONG("現場名は50文字以内で入力してください"),
    CHAR_COUNT_ADDRESS_TOO_LONG("住所は161文字以内で入力してください"),
    CHAR_COUNT_REMARK_TOO_LONG("備考欄は200文字以内で入力してください"),
    CHAR_COUNT_BLUEPRINT_NAME_TOO_LONG("図面名は20文字以内で入力してください"),
    CREATED_AT_MUST_MATCH_FORMAT("入力された日付の形式が正しくありません"),
    NOT_FILE_SIZE_ACCEPTABLE("ファイルサイズは5MBまでにしてください"),
    NOT_FILE_TYPE_ACCEPTABLE("画像ファイルを選択してください"),
    INPUT_FIELD_IS_BLANK("入力欄が空です");

    private final String message;
}
