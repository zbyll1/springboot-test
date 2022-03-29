package com.oracle.springboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2021/4/9 15:45
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVo<T> {
    //200正常  404 用户找不到资源  500 程序出错
    private String status;
    //数据
    private T t;

    private String message;



}
