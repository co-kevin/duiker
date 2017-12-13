package com.zdan91.duiker.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 从 request 的 header 中获取用户的基本信息
 */
public class HttpUtils {
    public static final String HEADER_USER_ID = "X-user-id";
    public static final String HEADER_ACCOUNT = "X-user-account";
    public static final String HEADER_NICKNAME = "X-user-nickname";
    public static final String HEADER_PHOTO = "X-user-photo";
    public static final String HEADER_PHONE = "X-user-phone";

    static {
        getServletRequestAttributes();
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes;
    }

    /**
     * 获取用户 id
     *
     * @return
     */
    public static Integer getHeaderUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getIntHeader(HEADER_USER_ID);
    }

    /**
     * 获取用户基本信息,包括 id,account,昵称,头像,手机号码
     * @return
     */
    public static BaseUserInfo getBaseUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        int userId = request.getIntHeader(HEADER_USER_ID);
        String phone = request.getHeader(HEADER_PHONE);
        String photo = request.getHeader(HEADER_PHOTO);
        String nickname = request.getHeader(HEADER_NICKNAME);
        String account = request.getHeader(HEADER_ACCOUNT);
        return new BaseUserInfo(photo, phone, nickname, account, userId);
    }


    /**
     * 封装用户的基本信息,存放于 header 中
     */
    public static class BaseUserInfo {
        private String photo;
        private String phone;
        private String nickname;
        private String account;
        private Integer id;

        public BaseUserInfo() {
        }

        public BaseUserInfo(String photo, String phone, String nickname, String account, Integer id) {
            this.photo = photo;
            this.phone = phone;
            this.nickname = nickname;
            this.account = account;
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
