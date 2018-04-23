/**
 * @author:Leo
 * @create 2018/4/17
 * @desc
 */
package com.leo.girl.controller;

import com.leo.girl.domain.Girl;
import com.leo.girl.domain.Result;
import com.leo.girl.responsitory.GirlRespository;
import com.leo.girl.service.GirlService;
import com.leo.girl.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GirlController {
    @Autowired
    private GirlRespository girlRespository;

    @Autowired
    private GirlService girlService;

    private final static Logger LOGGER = LoggerFactory.getLogger(GirlController.class);

    /**
     * 列出所有数据
     * @return
     */
    @GetMapping("/girls")
    public List<Girl> girlList(){
        LOGGER.info("get all girls");
        return girlRespository.findAll();
    }

    /**
     * 新增一条数据
     * @return
     */
    @PostMapping("/girls")
    public Result<Girl> addGirl(@Valid Girl girl, BindingResult bindingResult) {
        Result<Girl> result = new Result<>();
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        girl.setCupSize(girl.getCupSize());
        girl.setAge(girl.getAge());

        return ResultUtil.success(girlRespository.save(girl));
    }

    /**
     * 根据id获得一条数据
     * @param id
     * @return
     */
    @GetMapping("/girls/{id}")
    public Girl getGirl(@PathVariable("id") Integer id){
        LOGGER.info("get Girl By id");
        return girlRespository.findOne(id);
    }

    /**
     * 根据id更新数据
     * @param id
     * @param cupSize
     * @param age
     * @return
     */
    @PutMapping("/girls/{id}")
    public Girl updateGirl(@PathVariable("id") Integer id,
                           @RequestParam("cupSize") String cupSize,
                           @RequestParam("age") Integer age) {
        Girl girl = new Girl();
        girl.setId(id);
        girl.setAge(age);
        girl.setCupSize(cupSize);
        return girlRespository.save(girl);
    }

    /**
     * 根据id删除数据
     * @param id
     */
    @DeleteMapping("/girls/{id}")
    public void deleteOne(@PathVariable("id") Integer id) {
        girlRespository.delete(id);
    }

    /**
     * 根据年龄查询
     * @param age
     * @return
     */
    @GetMapping("/girls/age/{age}")
    public List<Girl> getByAge(@PathVariable("age") Integer age) {
       return girlRespository.findByAge(age);
    }

    @PostMapping("/girls/two")
    public void insertTwo() {
        girlService.insertTwo();
    }

    @GetMapping("/girls/getAge/{id}")
    public void getAge(@PathVariable("id") Integer id) throws Exception{
        girlService.getAge(id);
    }
}
