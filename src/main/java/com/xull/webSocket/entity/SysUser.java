package com.xull.webSocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: xull
 * @date: 2018-09-01 8:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    private String username;
    private String password;

    public SysUser(String username) {
        this.username = username;
    }
}
