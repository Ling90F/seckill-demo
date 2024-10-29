package com.seckill.goods.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author http://www.itheima.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "undo_log")
public class UndoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;//

    @Column(name = "branch_id")
    private Long branchId;//

    @Column(name = "xid")
    private String xid;//

    @Column(name = "context")
    private String context;//

    @Column(name = "rollback_info")
    private String rollbackInfo;//

    @Column(name = "log_status")
    private Integer logStatus;//

    @Column(name = "log_created")
    private Date logCreated;//

    @Column(name = "log_modified")
    private Date logModified;//

    @Column(name = "ext")
    private String ext;//

}
