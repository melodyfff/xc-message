# xc-message
JMS(Java Message Service) / AMQP(Advanced Message Queuing Protocol) 


## 环境准备

```bash
# 本地rabbitMQ,对应的端口
#-   4369 (epmd), 25672 (Erlang distribution)
#-   5672, 5671 (AMQP 0-9-1 without and with TLS)
#-   15672 (if management plugin is enabled)
#-   61613, 61614 (if STOMP is enabled)
#-   1883, 8883 (if MQTT is enabled)
docker run -d --name rabbit -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```