package com.csh.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 洪哥哥
 * @since 2022-04-23
 */
@Getter
@Setter

  @ApiModel(value = "Instrument对象", description = "")
public class Instrument implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Date createTime;

    private String avatarUrl;

    private String address;

      @ApiModelProperty("是否可借")
      private Boolean state;

    private Integer borrowerId;


}
