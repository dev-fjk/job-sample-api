package com.gw.job.sample.entity.request;

import com.gw.job.sample.annotations.SelectionStatusConstraint;
import com.gw.job.sample.entity.enums.SelectionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.Valid;
import lombok.Data;

@Data
@Schema(description = "応募状況更新リクエスト")
public class PostedUpdateRequest {

    @Valid
    private Set<PostedUserResume> resumes;

    @Data
    @Schema(description = "応募中のレジュメ")
    public static class PostedUserResume {

        private long userId;

        @SelectionStatusConstraint
        private SelectionStatus selectionStatus;
    }
}
