package com.wanli.collect.model.vo;

import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hu
 * @date 2018/12/25 14:55
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private Long userId;

    private Long fatherId;

    private String userUsername;

    private String userPassword;

    private UserStatusType userStatus;

    private String applicationFlag;

    private String token;

    private Application application;
}
