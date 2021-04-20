package me.zhengjie.modules.sms.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* @author wh
* @date 2021-03-08
*/
@Entity
@Data
@Table(name="sms_push_etongnet")
public class SmsPushEtongnet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "spid")
    private String spid;

    @Column(name = "password")
    private String password;

    @Column(name = "sp_name")
    private String spName;

    @Column(name = "sign_id")
    private Integer signId;

    @Column(name = "back_z1")
    private String backZ1;

    @Column(name = "back_z2")
    private String backZ2;

    @Column(name = "back_z3")
    private String backZ3;

    @Column(name = "back_z4")
    private String backZ4;

    @Column(name = "back_z5")
    private String backZ5;

    public void copy(SmsPushEtongnet source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
