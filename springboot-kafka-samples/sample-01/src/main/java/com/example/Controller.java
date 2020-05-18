package com.example;

import com.common.Foo1;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * curl http://localhost:8080/send/foo/ok -d ""
 * curl http://localhost:8080/send/foo/fail -d ""
 *
 *
 * @author xinchen
 * @version 1.0
 * @date 18/05/2020 15:38
 */
@RestController
public class Controller {

    @Resource
    private KafkaTemplate<Object, Object> template;

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        this.template.send("topic1", new Foo1(what));
    }
}
