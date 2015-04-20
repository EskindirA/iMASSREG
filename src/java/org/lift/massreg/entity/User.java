package org.lift.massreg.entity;

import javax.servlet.http.HttpServletRequest;
import org.lift.massreg.dao.MasterRepository;
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
    private String password;
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
            case POSTPDCOORDINATOR:
                returnValue = "PDC";
                break;
            case MINOR_CORRECTION_OFFICER:
                returnValue = "MCO";
                break;
            case CORRECTION_FIRST_ENTRY_OPERATOR:
                returnValue = "CFEO";
                break;
            case CORRECTION_SECOND_ENTRY_OPERATOR:
                returnValue = "CSEO";
                break;
            case CORRECTION_SUPERVISOR:
                returnValue = "CSUPER";
                break;

        }
        returnValue += ")";
        return returnValue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getFathersName() + " " + getGrandFathersName();
    }

    public String getNameWithRole() {
        return this.getFirstName() + " " + this.getFathersName() + getRoleShortText();
    }

    public String getRoleText() {
        String returnValue = "";
        switch (getRole()) {
            case FIRST_ENTRY_OPERATOR:
                returnValue = "FEO";
                break;
            case SECOND_ENTRY_OPERATOR:
                returnValue = "SEO";
                break;
            case SUPERVISOR:
                returnValue = "SUPERVISOR";
                break;
            case ADMINISTRATOR:
                returnValue = "ADMINISTRATOR";
                break;
            case POSTPDCOORDINATOR:
                returnValue = "PDC";
                break;
            case MINOR_CORRECTION_OFFICER:
                returnValue = "MCO";
                break;
            case CORRECTION_FIRST_ENTRY_OPERATOR:
                returnValue = "CFEO";
                break;
            case CORRECTION_SECOND_ENTRY_OPERATOR:
                returnValue = "CSEO";
                break;
            case CORRECTION_SUPERVISOR:
                returnValue = "CSUPER";
                break;

        }
        return returnValue;
    }

    public boolean save(HttpServletRequest request) {
        return MasterRepository.getInstance().save(request, this);
    }

    @Override
    public boolean validateForSave() {
        return true;
    }

    public static boolean delete(HttpServletRequest request, long userId) {
        return MasterRepository.getInstance().deleteUser(request, userId);
    }

    @Override
    public boolean validateForUpdate() {
        return true;
    }
}
