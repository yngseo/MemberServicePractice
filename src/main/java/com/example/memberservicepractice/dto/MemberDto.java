package com.example.memberservicepractice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class MemberDto implements UserDetails {

    private Integer seq;
    @NotEmpty
    @Length(min = 5, max = 20)
    private String id;
    private String password;
    @NotEmpty
    @Length(min = 2, max = 10)
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotNull
    private Integer levelSeq;
    private String applicant;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date modifyDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date approvalDate;
    private Character accountState;
    private Character passwordState;
    private String phone;
    private String remark;
    private String ceo;
    private String client;
    private String department;
    private String jobGrade;
    private String userRole;

    public MemberDto(Integer seq, String id, String password, String name, String email, Integer levelSeq, String applicant, Date startDate, Date modifyDate, Date approvalDate, Character accountState, Character passwordState, String phone, String remark, String ceo, String client, String department, String jobGrade, String userRole) {
        this.seq = seq;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.levelSeq = levelSeq;
        this.applicant = applicant;
        this.startDate = startDate;
        this.modifyDate = modifyDate;
        this.approvalDate = approvalDate;
        this.accountState = accountState;
        this.passwordState = passwordState;
        this.phone = phone;
        this.remark = remark;
        this.ceo = ceo;
        this.client = client;
        this.department = department;
        this.jobGrade = jobGrade;
        this.userRole = userRole;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevelSeq() {
        return levelSeq;
    }

    public void setLevelSeq(Integer levelSeq) {
        this.levelSeq = levelSeq;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Character getAccountState() {
        return accountState;
    }

    public void setAccountState(Character accountState) {
        this.accountState = accountState;
    }

    public Character getPasswordState() {
        return passwordState;
    }

    public void setPasswordState(Character passwordState) {
        this.passwordState = passwordState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(String jobGrade) {
        this.jobGrade = jobGrade;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.userRole));
    }

    // 시큐리티의 userName
    // -> 따라서 얘는 인증할 때 id를 봄
    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
