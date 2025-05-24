package Portfolio.BlueprintsManagement.presentation.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    NOT_FOUND_SITES("現場が追加されていません"),
    NOT_FOUND_SITE_BY_ID("idに該当する現場が存在しません"),
    ID_MUST_BE_UUID("idの形式が正しくありません"),
    CHAR_COUNT_SITE_NAME_TOO_LONG("現場名は50文字以内で入力してください"),
    CHAR_COUNT_ADDRESS_TOO_LONG("住所は161文字以内で入力してください"),
    CHAR_COUNT_REMARK_TOO_LONG("備考欄は200文字以内で入力してください"),
    INPUT_FIELD_IS_BLANK("入力欄が空です");

    private final String message;
}
