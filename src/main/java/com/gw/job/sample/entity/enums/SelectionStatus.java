package com.gw.job.sample.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.stream.Stream;
import lombok.Getter;
import org.seasar.doma.Domain;

@Getter
@Domain(valueType = String.class, factoryMethod = "of")
@Schema(enumAsRef = true, description = "選考状況: \n"
        + "* `0` - 選考前\n"
        + "* `1` - 選考中\n"
        + "* `2` - 採用\n"
        + "* `9` - 不採用\n"
)
public enum SelectionStatus {

    BEFORE("0", "選考前"),
    NOW("1", "選考中"),
    RECRUITMENT("2", "採用"),
    NOT_RECRUITMENT("9", "不採用");

    /**
     * 選考状況を表す数字
     */
    private final String value;

    /**
     * 表示名
     */
    private final String displayName;

    SelectionStatus(String value, String displayName) {
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
     * jsonの値からEnumへ変換する
     *
     * @param value DB側の値
     * @return {@link SelectionStatus}
     */
    @JsonCreator
    public static SelectionStatus map(String value) {
        return Stream.of(values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("selection status is invalid value"));
    }

    /**
     * DBの値からEnumへ変換する
     *
     * @param value DB側の値
     * @return {@link SelectionStatus}
     */
    public static SelectionStatus of(String value) {
        return Stream.of(values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("selection status is invalid value"));
    }
}
