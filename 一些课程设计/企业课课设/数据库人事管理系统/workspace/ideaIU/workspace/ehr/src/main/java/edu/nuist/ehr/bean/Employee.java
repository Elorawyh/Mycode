package edu.nuist.ehr.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工信息对象
 */
@Data  //此注解自动创建get和set方法以及覆盖toString方法
@AllArgsConstructor //创建有参构造函数
@NoArgsConstructor
public class Employee {

    private String name;
    private String gender;
    private Integer age;
    private String position;
}
