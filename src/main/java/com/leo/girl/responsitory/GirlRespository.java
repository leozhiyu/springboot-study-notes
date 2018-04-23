package com.leo.girl.responsitory;

import com.leo.girl.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GirlRespository extends JpaRepository<Girl, Integer> {

    /**
     * 通过年龄查询
     * @return
     */
    List<Girl> findByAge(Integer age);

}
