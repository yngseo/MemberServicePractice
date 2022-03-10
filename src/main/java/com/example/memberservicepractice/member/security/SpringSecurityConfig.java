package com.example.memberservicepractice.member.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/css/**", "/js/**", "/image/**").permitAll() // 로그인 권한은 누구나, resources파일도 모든권한
                // 접근 허용
                .antMatchers("/password/**", "/myInfo", "/list").hasAnyRole("ADMIN","EMP","CLIENT","CLIENTEMP")
                .antMatchers("/list/**").hasAnyRole("ADMIN","EMP")
                .antMatchers("/create").hasAnyRole("ADMIN","CLIENT") // 접근 체크 필요
                .antMatchers("/main", "/myInfo", "/list", "/list/**", "/create").access("@authorizationChecker.check(authentication)") // 비밀번호 상태가 'I'일 시 접근 제한
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/login")
                /*.loginProcessingUrl("/login_proc")*/
                .defaultSuccessUrl("/main")
                /*.failureUrl("/login?error=true")*/ // 인증에 실패했을 때 보여주는 화면 url, 로그인 form으로 파라미터값 error=true로 보낸다.
                .successHandler(
                        new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                clearAuthenticationAttributes(request); // 로그인 실패 이력 세션에서 제거
                                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                                if (userDetails.getMember().getPasswordState() == 'I') {
                                    response.sendRedirect("/password/" + userDetails.getUsername());
                                } else {
                                    response.sendRedirect("/main");
                                }
                            }
                            private void clearAuthenticationAttributes(HttpServletRequest request) {
                                HttpSession session = request.getSession(false);
                                if(session != null) {
                                    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                                }
                            }
                        }
                )
                .failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

                                String id = request.getParameter("username");
                                String errorMsg = null;

                                if(exception instanceof BadCredentialsException) {
                                    errorMsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
                                    request.setAttribute("errorMsg", errorMsg);
                                    request.getRequestDispatcher("/login?error=true").forward(request, response);
                                } else if(exception instanceof LockedException) {
                                    errorMsg = "계정 승인 처리 중입니다. 승인 후 로그인이 가능합니다.";
                                    request.setAttribute("errorMsg", errorMsg);
                                    request.getRequestDispatcher("/login?error=true").forward(request, response);
                                } /*else if(exception instanceof CredentialsExpiredException) {
                                    response.sendRedirect("/password/"+id);
                                    System.out.println("비밀번호 오류");
                                }*/
                            }
                        }
                )
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);
    }

    /**
     * 로그인 인증 처리 메소드
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}