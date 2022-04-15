package com.elite.miko.quiz.infrastructure.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface AdminAccountDao {

    /**
     * アカウントIDとパスワードで絞り込みを行ったレコードの件数を返却する
     *
     * @param accountId : アカウントID
     * @param password  : パスワード
     * @return 件数
     */
    @Select(queryTimeout = 5)
    int countByAccountIdAndPassword(String accountId, String password);
}
