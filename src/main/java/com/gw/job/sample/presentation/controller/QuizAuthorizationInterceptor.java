package com.gw.job.sample.presentation.controller;

import com.gw.job.sample.config.TokenConfig;
import com.gw.job.sample.application.common.utility.WebTokenUtil;
import java.nio.file.AccessDeniedException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Controllerの事前の共通認証処理を行うクラス
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QuizAuthorizationInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenConfig tokenConfig;
    private final WebTokenUtil webTokenUtil;

    /**
     * Controllerの事前処理を行う
     *
     * @param request  : リクエスト
     * @param response : レスポンス
     * @param handler  : ハンドラ
     * @return 認証結果
     *
     * @throws AccessDeniedException 認証失敗時にThrow
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws AccessDeniedException {

        // 管理者用ページ以外へのアクセスの場合は認証を無効にする
        if (!request.getRequestURI().contains("admin")) {
            return true;
        }
        // JWTのトークン値の検証
        if (!authorize(request)) {
            throw new AccessDeniedException("認証に失敗しました");
        }
        return true;
    }

    /**
     * ヘッダー部の認証トークンの検証を行う
     *
     * @param request : 受領したリクエスト
     * @return 認証結果
     */
    private boolean authorize(HttpServletRequest request) {

        final String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(authorization)) {
            return false;
        }
        //Bearer tokenの形式であることをチェック
        if (authorization.indexOf("Bearer ") != 0) {
            return false;
        }

        try {
            // Bearer の部分の文字列を除きトークン値だけを取得してJWTの解析を行う
            // 解析の結果 Token生成時に設定した固定値のsubjectが返却されるかをチェックする
            final String token = authorization.substring(7);
            return Objects.equals(webTokenUtil.analysisFromToken(token), tokenConfig.getSubject());
        } catch (RuntimeException runtimeException) {
            log.error("token解析時に失敗しました : {}", runtimeException.getMessage());
            return false;
        }
    }
}
