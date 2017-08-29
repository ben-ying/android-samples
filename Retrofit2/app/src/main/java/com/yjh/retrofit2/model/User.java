package com.yjh.retrofit2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements Serializable {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("username")
    private String username;
    @SerializedName("baby_name")
    private String babyName;
    @SerializedName("profile")
    private String profile;
    @SerializedName("gender")
    private int gender;
    @SerializedName("type")
    private int type;
    @SerializedName("is_email_activate")
    private boolean isEmailActivate;
    @SerializedName("is_phone_activate")
    private boolean isPhoneActive;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("region")
    private String region;
    @SerializedName("whats_up")
    private String whatsUp;
    @SerializedName("birth")
    private String birth;
    @SerializedName("zone")
    private String zone;
    @SerializedName("locale")
    private String locale;
    @SerializedName("hobbies")
    private String hobbies;
    @SerializedName("highlighted")
    private String highlighted;
    @SerializedName("token")
    private String token;
    @SerializedName("is_login")
    private boolean isLogin;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isEmailActivate() {
        return isEmailActivate;
    }

    public void setEmailActivate(boolean emailActivate) {
        isEmailActivate = emailActivate;
    }

    public boolean isPhoneActive() {
        return isPhoneActive;
    }

    public void setPhoneActive(boolean phoneActive) {
        isPhoneActive = phoneActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWhatsUp() {
        return whatsUp;
    }

    public void setWhatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(String highlighted) {
        this.highlighted = highlighted;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
