package org.lift.massreg.entity;

import org.lift.massreg.util.Constants;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class User implements Entity {

    private String username;
    private long userId;
    private String firstName;
    private String fathersName;
    private String grandFathersName;
    private String phoneNumber;
    private String status;

    private Constants.ROLE role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String getRoleShortText() {
        String returnValue = "(";
        switch (role) {
            case ADMINISTRATOR:
                returnValue += "Admin";
                break;
            case FIRST_ENTRY_OPERATOR:
                returnValue += "FEO";
                break;
            case SECOND_ENTRY_OPERATOR:
                returnValue += "SEO";
                break;
            case SUPERVISOR:
                returnValue += "Supervisor";
                break;
        }
        returnValue += ")";
        return returnValue;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getFathersName() + " " + getGrandFathersName();
    }

    public String getNameWithRole() {
        return this.getFirstName() + " " + this.getFathersName() + getRoleShortText();
    }

    @Override
    public boolean validateForSave() {
            return true;
    }

    @Override
    public boolean validateForUpdate() {
        return true;
    }
}
