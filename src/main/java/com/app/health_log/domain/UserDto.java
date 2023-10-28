package com.app.health_log.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class UserDto {
    private String id;
    private String pwd;
    private String user_id;

   }
