package com.example.demo.config;

import com.example.demo.entity.user.SysRole;
import com.example.demo.entity.user.SysUser;
import com.example.demo.repository.user.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInforDetailService implements UserDetailsService {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser userInfo= sysUserRepository.findByaccount(s);
        if(userInfo==null){
            throw new UsernameNotFoundException("---------------------------not found");
        }
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        for (SysRole role:userInfo.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        User user=new User(userInfo.getAccount(),userInfo.getPassword(),authorities);
        return user;
    }
}
