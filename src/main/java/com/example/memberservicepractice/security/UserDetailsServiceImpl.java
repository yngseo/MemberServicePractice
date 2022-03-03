package com.example.memberservicepractice.security;

import com.example.memberservicepractice.dto.MemberDto;
import com.example.memberservicepractice.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    MemberMapper memberMapper;

/*    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        MemberDto memberDto = memberMapper.getMemberById(id);
        if (memberDto == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        return (UserDetails) memberDto;
    }*/

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return new UserDetailsImpl(loadUserByUsernameProvider(id));
    }

    public MemberDto loadUserByUsernameProvider(String id) throws UsernameNotFoundException {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(id);
        MemberDto member = memberMapper.getMemberById(id);
        return member;
    }
}
