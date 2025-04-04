package com.seckill.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tb_address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//

    @Column(name = "username")
    private String username;//用户名

    @Column(name = "provinceid")
    private String provinceid;//省

    @Column(name = "cityid")
    private String cityid;//市

    @Column(name = "areaid")
    private String areaid;//县/区

    @Column(name = "phone")
    private String phone;//电话

    @Column(name = "address")
    private String address;//详细地址

    @Column(name = "contact")
    private String contact;//联系人

    @Column(name = "is_default")
    private String isDefault;//是否是默认 1默认 0否

    @Column(name = "alias")
    private String alias;//别名

}
