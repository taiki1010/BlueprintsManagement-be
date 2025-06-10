package com.portfolio.BlueprintsManagement.presentation.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    COMPLETE_UPDATE_SITE("現場情報が更新されました"),
    COMPLETE_DELETE_SITE("現場が削除されました"),
    COMPLETE_UPDATE_BLUEPRINT_NAME("図面名の更新が完了しました");

    private final String message;
}
