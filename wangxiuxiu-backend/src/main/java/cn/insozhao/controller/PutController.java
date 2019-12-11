package cn.insozhao.controller;

import cn.insozhao.beans.DataVo;
import cn.insozhao.rabbitmq.Producer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rabbit")
public class PutController {
    @Autowired
    Producer producer;
    @RequestMapping(value = "putdata",method = RequestMethod.GET)
    @ResponseBody
    public String putdata(DataVo dataVo){
        Gson gson = new Gson();
        producer.send(gson.toJson(dataVo));
        String returnmessage = "{\"message\":\"成功\"}";
        return gson.toJson(returnmessage);
    }
}
