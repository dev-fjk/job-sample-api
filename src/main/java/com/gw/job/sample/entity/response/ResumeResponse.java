package com.gw.job.sample.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "レジュメ情報レスポンス")
public class ResumeResponse {

    @Schema(description = "ユーザーID", example = "1")
    private long userId;

    @Schema(description = "苗字", example = "田中")
    private String lastName;

    @Schema(description = "名前", example = "太郎")
    private String firstName;

    @Schema(description = "苗字のフリガナ", example = "タナカ")
    private String lastNameKana;

    @Schema(description = "名前のフリガナ", example = "タロウ")
    private String firstNameKana;

    @Schema(description = "生年月日 yyyy-MM-ddの形式", type = "string", example = "1990-10-10")
    private LocalDate birthday;

    @Schema(description = "都道府県コード", example = "13")
    private int prefectureCode;

    @Schema(description = "住所　番地・建物名", example = "横浜市神奈川区XX町テストマンション305号")
    private String cityAddress;

    @Schema(description = "卒業した学校種別", example = "1")
    private int schoolType;

    @Schema(description = "学校名", example = "XXX大学")
    private String schoolName;

    @Schema(description = "就業経験", example = "true")
    private boolean working;

    @Schema(description = "職務要約", example = "プログラマーとして働いていました。")
    private String jobDescription;

    private List<Career> careers;
}
