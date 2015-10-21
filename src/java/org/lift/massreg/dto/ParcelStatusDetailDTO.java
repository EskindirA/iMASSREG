package org.lift.massreg.dto;

import org.lift.massreg.util.CommonStorage;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class ParcelStatusDetailDTO {

    private String upi;
    private String firstEntry;
    private String secondEntry;
    private boolean correction;
    private boolean committed;

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getFirstEntry() {
        return firstEntry;
    }

    public void setFirstEntry(String firstEntry) {
        this.firstEntry = firstEntry;
    }

    public String getSecondEntry() {
        return secondEntry;
    }

    public void setSecondEntry(String secondEntry) {
        this.secondEntry = secondEntry;
    }

    public boolean isCorrection() {
        return correction;
    }

    public String isCorrectionText() {
        return correction ? CommonStorage.getText("yes") : CommonStorage.getText("no");
    }

    public void isCorrection(boolean correction) {
        this.correction = correction;
    }

    public boolean isCommitted() {
        return committed;
    }
    public String isCommittedText() {
        return correction ? CommonStorage.getText("yes") : CommonStorage.getText("no");
    }

    public void isCommitted(boolean committed) {
        this.committed = committed;
    }

}
