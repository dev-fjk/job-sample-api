package com.gw.job.sample.dao;

import com.gw.job.sample.entity.Resume;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface ResumeDao {

    /**
     * ユーザーIDの一致するレジュメを返却する
     *
     * @param userId ユーザーID
     * @return レジュメ
     */
    @Select
    Resume findByUserId(long userId);
}
