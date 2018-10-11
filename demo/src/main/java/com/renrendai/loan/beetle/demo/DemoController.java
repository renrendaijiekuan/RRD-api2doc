package com.renrendai.loan.beetle.demo;

import com.google.gson.JsonObject;
import com.renrendai.loan.beetle.commons.api2doc.annotations.Api2Doc;
import com.renrendai.loan.beetle.commons.api2doc.annotations.ApiComment;
import com.renrendai.loan.beetle.commons.api2doc.annotations.ApiParamComments;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wudi on 2018/10/9
 */
@Api2Doc
@RestController
@RequestMapping(name = "Demo", path = "/demo")
public class DemoController {

    @RequestMapping(name = "测试", path = "/test/{id}", method = RequestMethod.POST)
    @ApiComment(value = "测试接口")
    @ApiParamComments({
            @ApiComment(name = "uid", value = "用户表示", sample = "100", seeClass = String.class),
            @ApiComment(name = "url", value = "访问地址", sample = "http://www.renrendai.com", seeClass = String.class)
    })
    public Result<User> test(@RequestParam User body) {
        Result<User> result = new Result<>();
        result.setData(new User());
        return result;
    }

    @RequestMapping(name = "测试Json", path = "/testJson", method = RequestMethod.POST)
    public Result<JsonObject> testJson(@RequestBody JsonObject body) {
        Result<JsonObject> result = new Result<>();
        result.setData(new JsonObject());
        return result;
    }
}
