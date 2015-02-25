package org.lift.massreg.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.Change;
import org.lift.massreg.util.Option;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class IndividualHolder implements Entity {

    private String id;
    private String upi;
    private byte stage;
    private long registeredBy;
    private User registeredByUser;
    private Timestamp registeredOn;
    private String firstName;
    private String fathersName;
    private String grandFathersName;
    private String sex;
    private String dateOfBirth;
    private byte familyRole;
    private String familyRoleText;
    private boolean physicalImpairment;
    private boolean orphan;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public byte getStage() {
        return stage;
    }

    public void setStage(byte stage) {
        this.stage = stage;
    }

    public long getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(long registeredBy) {
        this.registeredBy = registeredBy;
    }

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
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

    public String getSex() {
        return sex.toLowerCase();
    }

    public String getSexText() {
        String returnValue = "";
        if (sex.equalsIgnoreCase("m")) {
            returnValue = "Male";
        } else {
            returnValue = "Female";
        }
        return returnValue;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = "";
        }
        return dateOfBirth.trim();
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public byte getFamilyRole() {
        return familyRole;
    }

    public void setFamilyRole(byte familyRole) {
        this.familyRole = familyRole;
    }

    private void fillInTextValues() {
        Option[] allFamilyRoles = MasterRepository.getInstance().getAllFamilyRoles();
        for (Option allFamilyRole : allFamilyRoles) {
            if (allFamilyRole.getKey().equalsIgnoreCase(familyRole + "")) {
                familyRoleText = allFamilyRole.getValue();
            }
        }
    }

    public String getFamilyRoleText() {
        if (familyRoleText == null) {
            fillInTextValues();
        }
        return familyRoleText;
    }

    public String getFullName() {
        return getFirstName() + " " + getFathersName() + " " + getGrandFathersName();
    }

    public void setFamilyRoleText(String familyRoleText) {
        this.familyRoleText = familyRoleText;
    }

    public boolean hasPhysicalImpairment() {
        return physicalImpairment;
    }

    public String hasPhysicalImpairmentText() {
        return hasPhysicalImpairment() ? "Yes" : "No";
    }

    public void hasPhysicalImpairment(boolean physicalImpairment) {
        this.physicalImpairment = physicalImpairment;
    }

    public boolean isOrphan() {
        return orphan;
    }

    public String isOrphanText() {
        return isOrphan() ? "Yes" : "No";
    }

    public void isOrphan(boolean orphan) {
        this.orphan = orphan;
    }

    public boolean save() {
        return MasterRepository.getInstance().save(this);
    }

    @Override
    public boolean validateForSave() {
        return true;
    }

    public boolean remove(HttpServletRequest request) {
        return delete(request, id, registeredOn, upi, stage);
    }

    public static boolean delete(HttpServletRequest request, String holderId, Timestamp registeredOn, String upi, byte stage) {

        return MasterRepository.getInstance().deleteIndividualHolder(request, holderId, registeredOn, upi, stage);
    }

    public boolean equalsIndividualHolder(IndividualHolder obj) {
        boolean returnValue = true;
        if (this.getFamilyRole() != obj.getFamilyRole()) {
            returnValue = false;
        }
        if (this.hasPhysicalImpairment() != obj.hasPhysicalImpairment()) {
            returnValue = false;
        }
        if (!this.getDateOfBirth().trim().equalsIgnoreCase(obj.getDateOfBirth().trim())) {
            returnValue = false;
        }
        if (!this.getFathersName().trim().equalsIgnoreCase(obj.getFathersName().trim())) {
            returnValue = false;
        }
        if (!this.getFirstName().trim().equalsIgnoreCase(obj.getFirstName().trim())) {
            returnValue = false;
        }
        if (!this.getGrandFathersName().trim().equalsIgnoreCase(obj.getGrandFathersName().trim())) {
            returnValue = false;
        }

        if (!this.getId().trim().equalsIgnoreCase(obj.getId().trim())) {
            returnValue = false;
        }
        if (!this.getSex().trim().equalsIgnoreCase(obj.getSex().trim())) {
            returnValue = false;
        }
        if (this.isOrphan() != obj.isOrphan()) {
            returnValue = false;
        }
        return returnValue;
    }

    @Override
    public boolean validateForUpdate() {
        return true;
    }

    public boolean commit() {
        return MasterRepository.getInstance().commit(this);
    }

    public boolean submitForCorrection() {
        return MasterRepository.getInstance().submitForCorrection(this);
    }

    public ArrayList<Change> getDifferenceForChangeLog(IndividualHolder newIndividualHolder) {
        ArrayList<Change> returnValue = new ArrayList<>();

        if (!this.getId().equalsIgnoreCase(newIndividualHolder.getId())) {
            returnValue.add(new Change("holderid", this.getId() + "", newIndividualHolder.getId() + ""));
        }
        if (!this.getFirstName().equalsIgnoreCase(newIndividualHolder.getFirstName())) {
            returnValue.add(new Change("firstname", this.getFirstName() + "", newIndividualHolder.getFirstName() + ""));
        }
        if (!this.getFathersName().equalsIgnoreCase(newIndividualHolder.getFathersName())) {
            returnValue.add(new Change("fathersname", this.getFirstName() + "", newIndividualHolder.getFathersName() + ""));
        }
        if (!this.getGrandFathersName().equalsIgnoreCase(newIndividualHolder.getGrandFathersName())) {
            returnValue.add(new Change("grandfathersname", this.getGrandFathersName() + "", newIndividualHolder.getGrandFathersName() + ""));
        }
        if (!this.getSex().equalsIgnoreCase(newIndividualHolder.getSex())) {
            returnValue.add(new Change("sex", this.getSex() + "", newIndividualHolder.getSex() + ""));
        }
        if (!this.getDateOfBirth().equalsIgnoreCase(newIndividualHolder.getDateOfBirth())) {
            returnValue.add(new Change("dateofbirth", this.getDateOfBirth() + "", newIndividualHolder.getDateOfBirth() + ""));
        }
        if (this.getFamilyRole() != newIndividualHolder.getFamilyRole()) {
            returnValue.add(new Change("familyrole", this.getFamilyRole() + "", newIndividualHolder.getFamilyRole() + ""));
        }
        if (this.hasPhysicalImpairment() != newIndividualHolder.hasPhysicalImpairment()) {
            returnValue.add(new Change("physicalimpairment", this.hasPhysicalImpairment() + "", newIndividualHolder.hasPhysicalImpairment() + ""));
        }
        if (this.isOrphan() != newIndividualHolder.isOrphan()) {
            returnValue.add(new Change("isorphan", this.isOrphan() + "", newIndividualHolder.isOrphan() + ""));
        }
        return returnValue;
    }
}
