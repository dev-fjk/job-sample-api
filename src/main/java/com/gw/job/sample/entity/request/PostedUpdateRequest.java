package com.gw.job.sample.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.gw.job.sample.validation.constraint.PostedUpdateConstraint;
import com.gw.job.sample.validation.group.PostedUpdateToAcceptedGroup;
import com.gw.job.sample.validation.group.PostedUpdateToOtherStatusGroup;

@Data
@PostedUpdateConstraint
@Schema(description = "応募状況更新リクエスト")
public class PostedUpdateRequest {

    @NotNull
    @Range(min = 1, max = 4)
    @Schema(description = "選考状況", example = "1")
    private Integer status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(groups = PostedUpdateToAcceptedGroup.class)
    @Null(groups = PostedUpdateToOtherStatusGroup.class)
    @Schema(description = "入社日 status:3の場合必須, status:3以外の場合は設定出来ない", example = "2022-07-15")
    private LocalDate entryDate;
}
