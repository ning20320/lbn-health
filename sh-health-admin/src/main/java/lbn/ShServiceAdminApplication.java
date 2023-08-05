package lbn;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableDubbo
public class ShServiceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShServiceAdminApplication.class, args);
    }

}
