package edu.nuist.ehr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类，还是项目的配置类
 */
@SpringBootApplication
@MapperScan("edu.nuist.ehr.dao") //用于扫描dao类型，指定dao所在的包名
public class EhrApplication {

    public static void main(String[] args) {
        SpringApplication.run(EhrApplication.class, args);
    }

}
