package com.wanli.collect.model.dto;

import com.wanli.collect.model.constants.UserStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hu
 * @date 2018/12/25 10:21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;

    private Long fatherId;

    private String userUsername;

    private String userPassword;

    private UserStatusType userStatus;

    private String applicationFlag;

}
