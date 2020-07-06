package com.caishi.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TUser {

  private long id;
  private String username;
  private String password;
  private String phone;
  private String salt;
  private String standby;
  private Date createTime;
  private Date updateTime;
  private Integer idDelete;
  private String nickName;



}
