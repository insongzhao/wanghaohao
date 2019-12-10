package cn.insozhao.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.insozhao"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
