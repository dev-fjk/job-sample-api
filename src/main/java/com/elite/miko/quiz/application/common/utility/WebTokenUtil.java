package com.elite.miko.quiz.application.common.utility;

import com.elite.miko.quiz.application.common.config.TokenConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * WebTokenの生成 解析を行う共通クラス
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebTokenUtil {

    private final TokenConfig tokenConfig;
    private final Clock clock;

    private static final int TOKEN_ENABLED_MINUTES = 30;

    /**
     * JsonWebTokenの生成を行い返却する
     *
     * @return 生成したToken
     */
    public String generateToken() {

        final String subject = tokenConfig.getSubject();
        final String tokenKey = tokenConfig.getTokenKey();

        // Tokenの有効期限を設定
        // 現在日時 + TOKEN_ENABLED_MINUTES分の日時を指定
        LocalDateTime tokenExpirationDateTime = LocalDateTime.now(clock).plusMinutes(TOKEN_ENABLED_MINUTES);
        log.info("トークン有効期限 : {}", tokenExpirationDateTime);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(this.toDate(tokenExpirationDateTime))
                .signWith(Keys.hmacShaKeyFor(tokenKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    /**
     * Tokenの解析を行う
     *
     * @return JWTの生成に使用したSubjectの値
     */
    public String analysisFromToken(final String token) {
        final String tokenKey = tokenConfig.getTokenKey();
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(tokenKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * トークン有効期限のLocalDateTimeをDate型に変換する
     *
     * @param tokenExpirationDateTime : トークン有効期限を持つLocalDateTime
     * @return 変換後のDate
     */
    private Date toDate(LocalDateTime tokenExpirationDateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(tokenExpirationDateTime, clock.getZone());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }
}
