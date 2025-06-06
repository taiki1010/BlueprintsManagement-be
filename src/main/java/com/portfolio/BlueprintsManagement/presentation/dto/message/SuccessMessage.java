package com.portfolio.BlueprintsManagement.presentation.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    COMPLETE_UPDATE_SITE("現場情報が更新されました"),
    COMPLETE_DELETE_SITE("現場が削除されました");

    private final String message;
}
