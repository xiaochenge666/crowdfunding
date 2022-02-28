package com.crowd.mvc.config.auth;

import com.crowd.mvc.annotaion.RootIgnore;
import com.crowd.utils.CrowdUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


//自定义密码对比器
@Component
@RootIgnore
public class CrowdPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return CrowdUtils.md5(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        /**
         * rawPassword：原始密码 浏览器端给的密码
         * encodedPassword：数据库查出的密码（以进行过Md5盐值加密）
         * */
        return encodedPassword.equals(this.encode(rawPassword));
    }

}
