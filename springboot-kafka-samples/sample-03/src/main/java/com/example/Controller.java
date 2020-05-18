package com.example;

import com.common.Foo1;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * topic2接受消息，向topic3发送消息，等待控制台输入信息模拟事物的提交
 *
 * curl http://localhost:8080/send/foos/ok -d ""
 *
 * @author xinchen
 * @version 1.0
 * @date 18/05/2020 16:13
 */
@RestController
public class Controller {

    @Resource
    private KafkaTemplate<Object, Object> template;

    @PostMapping(path = "/send/foos/{what}")
    public void sendFoo(@PathVariable String what) {
        // 执行事物
        this.template.executeInTransaction(kafkaTemplate -> {
            StringUtils.commaDelimitedListToSet(what).stream()
                    .map(s -> new Foo1(s))
                    .forEach(foo -> kafkaTemplate.send("topic2", foo));
            return null;
        });
    }

}
