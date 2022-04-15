package com.gw.job.sample.domain.model.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Getter;
import org.seasar.doma.Domain;

@Getter
@Domain(valueType = String.class, factoryMethod = "of")
@Schema(enumAsRef = true, description = "クイズの状態: \n"
        + "* `1` - 有効\n"
        + "* `2` - 追加リクエスト中\n"
        + "* `9` - 無効\n"
)
public enum QuizStatus {

    ENABLED("1", "有効"),
    REQUEST("2", "追加リクエスト中"),
    DISABLED("9", "無効"),
    INVALID("invalid", "異常値");

    private final String value;
    private final String displayName;

    QuizStatus(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * JsonからEnumへの変換に使用されるファクトリメソッド
     *
     * @param value : Json側の値
     * @return 値がValueと一致した場合インスタンス作成し返却
     */
    @JsonCreator
    public static QuizStatus create(String value) {

        if (Objects.isNull(value)) {
            return null;
        }

        return Stream.of(values())
                .filter(v -> value.equals(v.getValue()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("quizStatusが異常な値です")
                );
    }

    /**
     * DBからEntityへの変換に使用されるファクトリメソッド
     *
     * @param value : DB側の値
     * @return 値がValueと一致した場合インスタンス作成し返却
     */
    public static QuizStatus of(String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("quizStatusが設定されていません");
        }

        if (value.equals("invalid")) {
            throw new IllegalArgumentException("quizStatusが異常な値です");
        }

        return Stream.of(values())
                .filter(v -> value.equals(v.getValue()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("quizStatusが異常な値です")
                );
    }
}
