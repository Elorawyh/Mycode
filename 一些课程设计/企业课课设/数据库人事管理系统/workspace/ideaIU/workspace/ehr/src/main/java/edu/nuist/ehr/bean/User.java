package edu.nuist.ehr.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User extends BaseBean {
    private String username;
    private String password;
    private String name;
    private String phone;
    private Date createTime;

    private List<Role> roleList;
}
