package org.lift.massreg.entity;

import java.sql.*;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.util.Option;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class Dispute implements Entity {

    private String upi;
    private byte stage;
    private long registeredBy;
    private User registeredByUser;
    private Timestamp registeredOn;
    private String firstName;
    private String fathersName;
    private String grandFathersName;
    private String sex;
    private byte disputeType;
    private String disputeTypeText;
    private byte disputeStatus;
    private String disputeStatusText;

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

    public void setRegisteredByUser(User registeredByUser) {
        this.registeredByUser = registeredByUser;
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

    public String getFullName() {
        return getFirstName() + " " + getFathersName() + " " + getGrandFathersName();
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

    public byte getDisputeType() {
        return disputeType;
    }

    public void setDisputeType(byte disputeType) {
        this.disputeType = disputeType;
    }

    public String getDisputeTypeText() {
        if (disputeTypeText == null) {
            fillInTextValues();
        }
        return disputeTypeText;
    }

    public byte getDisputeStatus() {
        return disputeStatus;
    }

    public void setDisputeStatus(byte disputeStatus) {
        this.disputeStatus = disputeStatus;
    }

    public String getDisputeStatusText() {
        if (disputeStatusText == null) {
            fillInTextValues();
        }
        return disputeStatusText;
    }

    private synchronized void fillInTextValues() {

        Option[] allDisputeStatusTypes = MasterRepository.getInstance().getAllDisputeStatusTypes();
        for (Option disputeStatusType : allDisputeStatusTypes) {
            if (disputeStatusType.getKey().equalsIgnoreCase(disputeStatus + "")) {
                disputeStatusText = disputeStatusType.getValue();
            }
        }
        Option[] allDisputeTypes = MasterRepository.getInstance().getAllDisputeTypes();
        for (Option allDisputeType : allDisputeTypes) {
            if (allDisputeType.getKey().equalsIgnoreCase(disputeType + "")) {
                disputeTypeText = allDisputeType.getValue();
            }
        }
    }

    public boolean save() {
        return MasterRepository.getInstance().save(this);
    }

    @Override
    public boolean validateForSave() {
        //TODO
        return true;
    }

    public boolean remove() {
        return delete(registeredOn, upi, stage);
    }

    public static boolean delete(Timestamp registeredOn, String upi, byte stage) {
        return MasterRepository.getInstance().deleteDispute(registeredOn, upi, stage);
    }

    public boolean equalsDispute(Dispute obj) {
        boolean returnValue = true;
        if(this.getDisputeStatus()!=obj.getDisputeStatus()){
            returnValue = false;
        }
        if(this.getDisputeType()!=obj.getDisputeType()){
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
        if (!this.getSex().trim().equalsIgnoreCase(obj.getSex().trim())) {
            returnValue = false;
        }
        return returnValue;
    }

    @Override
    public boolean validateForUpdate() {
        return true;
    }

    void commit() {
        MasterRepository.getInstance().commit(this);
    }

    public boolean submitForCorrection() {
        return MasterRepository.getInstance().submitForCorrection(this);
    }
}
