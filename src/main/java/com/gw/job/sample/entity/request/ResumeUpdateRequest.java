package com.gw.job.sample.entity.request;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "レジュメ更新リクエスト")
public class ResumeUpdateRequest extends ResumeBaseRequest {

    @Valid
    @Schema(description = "在籍企業一覧 就業経験有りの場合必須, 就業経験無の場合設定不可 最大5件")
    private List<UpdateCareer> careers = Collections.emptyList();

    @NotNull
    @Schema(description = "更新者")
    private String updatedBy;

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = false)
    @Schema(description = "追加・更新する 在籍企業")
    public static class UpdateCareer extends CareerBaseRequest {

        @Schema(description = "更新対象の在籍企業ID 新規追加する在籍企業の場合はnullを設定")
        private Long experienceId;

        @NotEmpty
        @Schema(description = "経験職種一覧 最大3件")
        private List<UpdateOccupation> occupations;

        @Data
        @ToString(callSuper = true)
        @EqualsAndHashCode(callSuper = false)
        @Schema(description = "追加・更新する 経験職種")
        public static class UpdateOccupation extends OccupationBaseRequest {

            @Schema(description = "更新対象の経験職種ID 新規追加する在籍企業の場合はnullを設定")
            private Long occupationId;

        }
    }
}
