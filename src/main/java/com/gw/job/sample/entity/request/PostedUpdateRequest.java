package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Schema(description = "応募状況更新リクエスト")
public class PostedUpdateRequest {

    @NotNull
    @Range(min = 1, max = 4)
    @Schema(description = "選考状況", example = "1")
    private Integer status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "入社日 status:3の場合必須, status:3以外の場合は設定出来ない", example = "2022-07-15")
    private LocalDate entryDate;

    @AssertTrue(message = "status:3の場合はentryDate必須、status:3以外の場合はentryDateは設定できません")
    private boolean isValidStatusAndEntryDate() {
        if(status == 3 && entryDate != null) {
            return true;
        }
        if(status == 3 && entryDate == null) {
            return false;
        }
        return entryDate == null;
    }
}
