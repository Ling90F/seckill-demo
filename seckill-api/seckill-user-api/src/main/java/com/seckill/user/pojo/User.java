package com.seckill.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tb_user")
public class User implements Serializable {

    @Id
    @Column(name = "username")
    private String username;//用户名

    @Column(name = "password")
    private String password;//密码，加密存储

    @Column(name = "phone")
    private String phone;//注册手机号

    @Column(name = "email")
    private String email;//注册邮箱

    @Column(name = "created")
    private Date created;//创建时间

    @Column(name = "updated")
    private Date updated;//修改时间

    @Column(name = "nick_name")
    private String nickName;//昵称

    @Column(name = "name")
    private String name;//真实姓名

}
