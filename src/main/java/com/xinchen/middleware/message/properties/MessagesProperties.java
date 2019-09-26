package com.xinchen.middleware.message.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 *
 * 使用{@link ConfigurationProperties} 需要 @EnableConfigurationProperties or mark as @Component
 *
 * 关于自定义ConfigurationProperties  参考: http://www.hellojava.com/a/82613.html
 *
 *
 * {@link Validated} 确保传递给应用程序的配置参数的参数是有效的
 *
 * @author xinchen
 * @version 1.0
 * @date 26/09/2019 12:18
 */
@ConfigurationProperties(prefix = "messages")
@Validated
@Data
public class MessagesProperties {

    @NotNull
    private Rabbit rabbit = new Rabbit();


    public static class Rabbit{
        private boolean wayConfigure = false;

        private boolean wayAnnotation = false;

        public void setWayConfigure(boolean wayConfigure) {
            this.wayConfigure = wayConfigure;
        }

        public boolean isWayConfigure() {
            return wayConfigure;
        }

        public boolean isWayAnnotation() {
            return wayAnnotation;
        }

        public void setWayAnnotation(boolean wayAnnotation) {
            this.wayAnnotation = wayAnnotation;
        }
    }
}
