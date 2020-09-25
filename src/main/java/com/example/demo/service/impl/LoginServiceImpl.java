package com.example.demo.service.impl;

import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.LoginInfor;
import com.example.demo.entity.user.Login;
import com.example.demo.entity.user.SysRole;
import com.example.demo.entity.user.SysUser;
import com.example.demo.repository.user.SysUserRepository;
import com.example.demo.service.LoginService;
import io.netty.util.concurrent.Promise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String getToken(Login login) {
        //String password= passwordEncoder.encode(login.getPassword());
        //System.out.println(password+"pwd");
        SysUser user=sysUserRepository.findByaccount(login.getUserName());
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        if(user==null){
            return "{\"status\":\"400\",\"message\":\"用户名错误\"}";
        }
        else if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())){
            return "{\"status\":\"400\",\"message\":\"密码错误\"}";
        }
        else {
            String role="ROLE_"+user.getRoles().get(0).getRoleName();
            String token =JwtTokenUtils.createToken(login.getUserName(),role,false);
            token='\"'+token+'\"';
            return "{\"status\":\"200\",\"message\":\"登陆成功\",\"token\":"+token+"}";
        }
    }

    @Override
    public LoginInfor getTokenEntity(Login login) {
        SysUser user=sysUserRepository.findByaccount(login.getUserName());
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        LoginInfor loginInfor=new LoginInfor();
        if(user==null){
            loginInfor.setMessage("用户名错误");
            loginInfor.setStatus("400");
            loginInfor.setToken("");
            return loginInfor;
        }
        else if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())){
            loginInfor.setMessage("密码错误");
            loginInfor.setStatus("400");
            loginInfor.setToken("");
            return loginInfor;
        }
        else {
            String role="ROLE_"+user.getRoles().get(0).getRoleName();
            String token =JwtTokenUtils.createToken(login.getUserName(),role,false);
            //token='\"'+token+'\"';
            loginInfor.setMessage("登陆成功");
            loginInfor.setStatus("200");
            loginInfor.setToken("Bearer "+token);
            return loginInfor;
            //return "{\"status\":\"200\",\"message\":\"登陆成功\",\"token\":"+"Bearer "+token+"}";
        }
    }
}
