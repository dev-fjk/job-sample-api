package com.gw.job.sample.entity.request;

import com.gw.job.sample.annotations.SelectionStatusConstraint;
import com.gw.job.sample.entity.enums.SelectionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "応募状況更新リクエスト")
public class PostedUpdateRequest {

    @Valid
    private Set<PostedUserResume> resumes;

    @Data
    @Schema(description = "応募中のレジュメ")
    public static class PostedUserResume {

        @Schema(description = "ユーザーID", example = "1")
        private long userId;

        @NotNull(message = "必須項目です")
        @Schema(description = "選考状況ID 0:採用前,1:選考中,2:採用,9:不採用", example = "1")
        @SelectionStatusConstraint
        private SelectionStatus selectionStatus;
    }
}
