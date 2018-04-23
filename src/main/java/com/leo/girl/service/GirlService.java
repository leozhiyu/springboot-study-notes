/**
 * @author:Leo
 * @create 2018/4/17
 * @desc
 */
package com.leo.girl.service;

import com.leo.girl.enums.ResultEnum;
import com.leo.girl.exception.GirlException;
import com.leo.girl.responsitory.GirlRespository;
import com.leo.girl.domain.Girl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GirlService {
    @Autowired
    private GirlRespository girlRespository;

    public void insertTwo() {
        Girl girl1 = new Girl();
        girl1.setCupSize("G");
        girl1.setAge(22);
        girlRespository.save(girl1);

        Girl girl2 = new Girl();
        girl2.setCupSize("F");
        girl2.setAge(21);
        girlRespository.save(girl2);
    }

    public void getAge(Integer id) throws Exception{
        Girl girl = girlRespository.getOne(id);
        Integer age = girl.getAge();
        if (age <= 10) {
            throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
        } else if (age > 10 && age < 16) {
            throw new GirlException(ResultEnum.MIDDLE_SCHOOL);
        }
    }

    /**
     * 获得一条女生的信息
     * @param id
     */
    public Girl findOne(Integer id) {
        return girlRespository.findOne(id);
    }
}
