package org.lift.massreg.dto;

import java.sql.Timestamp;
import org.lift.massreg.util.Constants;

/**
 * A DTO Class to hold information about the currently logged in user
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class CurrentUserDTO {

    private String username;
    private Timestamp loginTime;
    private String clientInfo;
    private long userId;
    private String firstName;
    private String fathersName;
    private String grandFathersName;
    private String phoneNumber;

    private Constants.ROLE role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Constants.ROLE getRole() {
        return role;
    }

    public void setRole(Constants.ROLE role) {
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getGrandFathersName() {
        return grandFathersName;
    }

    public void setGrandFathersName(String grandFathersName) {
        this.grandFathersName = grandFathersName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getFathersName() + " " + getGrandFathersName();
    }
}
