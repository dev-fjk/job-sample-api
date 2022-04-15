package com.elite.miko.quiz.infrastructure.dao;

import com.elite.miko.quiz.infrastructure.model.entity.Quiz;
import java.util.List;
import java.util.Set;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

@Dao
@ConfigAutowireable
public interface QuizDao {

    /**
     * クイズの問題をランダムで指定件数文取得する
     *
     * @param count : 取得件数
     * @return クイズ一覧
     */
    @Select(queryTimeout = 10)
    List<Quiz> fetchQuizRandom(int count, String status);

    /**
     * クイズ一覧の取得を行う
     * quizStatusが設定されている場合はstatusを抽出条件に追加する
     *
     * @param options    : 検索オプション
     * @param quizStatus : クイズのステータス
     * @return 取得したクイズ一覧
     */
    @Select(queryTimeout = 5)
    List<Quiz> fetchByIfPresentStatus(SelectOptions options, String quizStatus);

    /**
     * クイズIDの一覧を指定して悲観ロックの上クイズの取得を行う
     *
     * @param quizIdSet : クイズIDのリスト
     * @return クイズ一覧
     */
    @Select(queryTimeout = 5)
    List<Quiz> fetchByQuizIdSetForUpdate(Set<Long> quizIdSet);

    /**
     * クイズの追加を行う
     *
     * @param quiz : 追加するQuiz情報
     * @return : 追加件数
     */
    @Insert(excludeNull = true, queryTimeout = 5)
    int insert(Quiz quiz);

    /**
     * クイズの更新を行う
     *
     * @param quiz : 更新するQuiz情報
     * @return : 更新件数
     */
    @Update(queryTimeout = 5)
    int update(Quiz quiz);

    /**
     * クイズの削除を行う
     *
     * @param quizIdList : 削除対象のクイズIDリスト
     * @return 削除件数
     */
    @Delete(sqlFile = true, queryTimeout = 5)
    int deleteByQuizIdList(List<Long> quizIdList);
}
