package com.qf.v1.entity;

import java.io.Serializable;
import java.util.Date;

public class TUser implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.id
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.phone
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.email
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.password
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.username
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.status
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.flag
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private Boolean flag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.create_time
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.update_time
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.create_user
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.update_user
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    private Long updateUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.id
     *
     * @return the value of t_user.id
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.id
     *
     * @param id the value for t_user.id
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.phone
     *
     * @return the value of t_user.phone
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.phone
     *
     * @param phone the value for t_user.phone
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.email
     *
     * @return the value of t_user.email
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.email
     *
     * @param email the value for t_user.email
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.password
     *
     * @return the value of t_user.password
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.password
     *
     * @param password the value for t_user.password
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.username
     *
     * @return the value of t_user.username
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.username
     *
     * @param username the value for t_user.username
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.status
     *
     * @return the value of t_user.status
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.status
     *
     * @param status the value for t_user.status
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.flag
     *
     * @return the value of t_user.flag
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public Boolean getFlag() {
        return flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.flag
     *
     * @param flag the value for t_user.flag
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.create_time
     *
     * @return the value of t_user.create_time
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.create_time
     *
     * @param createTime the value for t_user.create_time
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.update_time
     *
     * @return the value of t_user.update_time
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.update_time
     *
     * @param updateTime the value for t_user.update_time
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.create_user
     *
     * @return the value of t_user.create_user
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.create_user
     *
     * @param createUser the value for t_user.create_user
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.update_user
     *
     * @return the value of t_user.update_user
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.update_user
     *
     * @param updateUser the value for t_user.update_user
     *
     * @mbggenerated Mon Mar 25 21:04:33 CST 2019
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
}