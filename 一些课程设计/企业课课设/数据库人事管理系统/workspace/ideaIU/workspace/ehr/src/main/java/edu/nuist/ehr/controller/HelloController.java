package edu.nuist.ehr.controller;

import edu.nuist.ehr.bean.Employee;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 接收客户端发送的http请求，处理请求，返回数据
 */
@RestController //springmvc能够通过注解识别到这个类，从而加载类中的代码逻辑
public class HelloController {

    @RequestMapping("/hello")  //映射前台发送的http：//localhost:80/hello请求
    public String hello() {
        return "hello java web";
    }

    /**
     * 返回雇员集合信息
     * @return
     */
    @RequestMapping("/employee")
    public List<Employee> getEmployees(){
        List<Employee> list = new ArrayList<>();
        //ctrl+d快速复制一行，ctrl+p展示函数的传参信息
        list.add(new Employee("张三","男",18,"工人"));
        list.add(new Employee("李四","女",20,"工人"));
        list.add(new Employee("王五","男",31,"经理"));
        return list;
    }

}
