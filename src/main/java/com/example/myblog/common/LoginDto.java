package com.example.myblog.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: quest
 * @Description:
 * @FileName: LoginDto
 */
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
