/**
 * @author:Leo
 * @create 2018/4/17
 * @desc
 */
package com.leo.girl.controller;

import com.leo.girl.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    private GirlProperties girlProperties;

    @RequestMapping(value = "/say", method = {RequestMethod.GET})
    public String say(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {
        return "id  =" + id;
    }
}
