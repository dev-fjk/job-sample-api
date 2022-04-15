package com.elite.miko.quiz.infrastructure.dao;

import com.elite.miko.quiz.infrastructure.model.entity.Answer;
import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface AnswerDao {

    /**
     * クイズIDの一覧と一致する回答一覧を取得する
     *
     * @param quizIdList : クイズIDのリスト
     * @return 回答一覧
     */
    @Select(queryTimeout = 5)
    List<Answer> fetchByQuizIdList(List<Long> quizIdList);

    /**
     * 回答の追加を行う
     *
     * @param answer : 追加する回答情報
     * @return : 追加件数
     */
    @Insert(excludeNull = true, queryTimeout = 5)
    int insert(Answer answer);

    /**
     * 回答の更新を行う
     *
     * @param answer : 追加する回答情報
     * @return : 追加件数
     */
    @Update(queryTimeout = 5)
    int update(Answer answer);

    /**
     * 回答の削除を行う
     *
     * @param quizIdList : 削除対象のクイズID一覧
     * @return 削除件数
     */
    @Delete(sqlFile = true, queryTimeout = 5)
    int deleteByQuizIdList(List<Long> quizIdList);
}
