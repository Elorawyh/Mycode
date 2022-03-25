package edu.nuist.ehr.bean;

import lombok.Data;

import java.util.List;

@Data
public class Role extends BaseBean {
    private String roleName;
    private List<Menu> menuList;
}