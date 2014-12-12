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

}
