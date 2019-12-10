//package cn.insozhao.rabbitmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class Consumer {
//    @RabbitListener(queues = "test.queue1")
//    @RabbitHandler
//    public void get(String message){
//      log.info(message);
//    }
//}
