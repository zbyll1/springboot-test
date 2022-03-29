package com.oracle.springboot.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author  张滨宇
 * @date  2022/3/28 11:56
 * @version 1.0
 */
/**
    * user
    */
@Data
@AllArgsConstructor
public class User implements Serializable {
    /**
    * id
    */
    private Integer id;

    /**
    * username
    */
    private String username;

    /**
    * password
    */
    private String password;

    /**
    * phone
    */
    private String phone;

    private static final long serialVersionUID = 1L;
}