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
@Schema(description = "レジュメ追加リクエスト")
public class ResumeAddRequest extends ResumeBaseRequest {

    @Valid
    @Schema(description = "在籍企業一覧 就業経験有りの場合必須, 就業経験無の場合設定不可 最大5件")
    private List<AddCareer> careers = Collections.emptyList();

    @NotNull
    @Schema(description = "作成者")
    private String createdBy;

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = false)
    @Schema(description = "追加する在籍企業")
    public static class AddCareer extends CareerBaseRequest {

        @NotEmpty
        @Schema(description = "経験職種一覧 最大3件")
        private List<AddOccupation> occupations;

        @Data
        @ToString(callSuper = true)
        @EqualsAndHashCode(callSuper = false)
        @Schema(description = "追加する経験職種")
        public static class AddOccupation extends OccupationBaseRequest {

        }
    }
}
