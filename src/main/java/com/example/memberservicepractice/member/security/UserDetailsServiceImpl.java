package com.example.memberservicepractice.member.security;

import com.example.memberservicepractice.member.dto.MemberDto;
import com.example.memberservicepractice.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    MemberRepository memberMapper;

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
