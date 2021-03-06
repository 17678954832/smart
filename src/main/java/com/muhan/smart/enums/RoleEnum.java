package com.muhan.smart.enums;

import lombok.Getter;

/**
 * 角色：0-管理员，1-普通用户
 */
@Getter
public enum RoleEnum {
    ADMIN(0),

    CUDTOMER(1),
    ;

    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
