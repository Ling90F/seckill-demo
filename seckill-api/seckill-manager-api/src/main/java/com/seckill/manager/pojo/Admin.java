package com.seckill.manager.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tb_admin")
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//

    @Column(name = "login_name")
    private String loginName;//用户名

    @Column(name = "password")
    private String password;//密码

    @Column(name = "status")
    private String status;//状态

}
