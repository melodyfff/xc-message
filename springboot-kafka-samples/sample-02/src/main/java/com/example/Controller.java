package com.example;

import com.common.Bar1;
import com.common.Foo1;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * curl http://localhost:8080/send/foo/okok -d ""
 * curl http://localhost:8080/send/bar/okok -d ""
 * curl http://localhost:8080/send/unknown/okok -d ""
 *
 * @author xinchen
 * @version 1.0
 * @date 18/05/2020 15:59
 */
@RestController
public class Controller {

    @Resource
    private KafkaTemplate<Object, Object> template;

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        this.template.send("foos", new Foo1(what));
    }

    @PostMapping(path = "/send/bar/{what}")
    public void sendBar(@PathVariable String what) {
        this.template.send("bars", new Bar1(what));
    }

    @PostMapping(path = "/send/unknown/{what}")
    public void sendUnknown(@PathVariable String what) {
        this.template.send("bars", what);
    }

}