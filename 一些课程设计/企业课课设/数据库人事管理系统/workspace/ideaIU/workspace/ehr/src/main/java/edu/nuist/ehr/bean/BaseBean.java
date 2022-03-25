package edu.nuist.ehr.bean;

import lombok.Data;

@Data
public class BaseBean {
    protected Long id;
    protected String delFlag; //驼峰
}
