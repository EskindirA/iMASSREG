package org.lift.massreg.dto;

import java.util.ArrayList;
import org.lift.massreg.util.CommonStorage;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0.6
 * @since 2.0.6
 *
 */
public class HoldingParcelPublicDisplayDTO {

    private boolean hasMissingValue = false;
    private String upi;
    private int parcelId;
    private String mapSheetNo;
    private boolean hasDispute;
    private double area;
    private byte holding;
    private ArrayList<String> guardians;

    public HoldingParcelPublicDisplayDTO() {
        guardians = new ArrayList<>();
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }

    public String getMapSheetNo() {
        return mapSheetNo;
    }

    public void setMapSheetNo(String mapSheetNo) {
        this.mapSheetNo = mapSheetNo;
    }

    public boolean hasDispute() {
        return hasDispute;
    }

    public String hasDisputeText() {
        return hasDispute ? CommonStorage.getText("yes") : CommonStorage.getText("no");
    }

    public void hasDispute(boolean hasDispute) {
        this.hasDispute = hasDispute;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public byte getHolding() {
        return holding;
    }

    public void setHolding(byte holding) {
        this.holding = holding;
    }

    public ArrayList<String> getGuardians() {
        if (guardians.isEmpty()) {
            guardians.add(CommonStorage.getText("does_not_exist"));
        }
        return guardians;
    }

    public void addGuardian(String guardian) {
        guardians.add(guardian);
    }

    public boolean hasMissingValue() {
        return hasMissingValue;
    }

    public void hasMissingValue(boolean hasMissingValue) {
        this.hasMissingValue = hasMissingValue;
    }

    public String hasMissingValueText() {
        return hasMissingValue ? CommonStorage.getText("yes") : CommonStorage.getText("no");
    }

    public String getGuardiansText() {
        String returnValue = "Does not exist";
        if (getGuardians().size() > 0) {
            returnValue = "";
        }
        returnValue = guardians.stream().map((guardian) -> guardian + ",").reduce(returnValue, String::concat);

        if (returnValue.trim().length() > 0) {
            returnValue = returnValue.substring(0, returnValue.length());
        }

        return returnValue;
    }
}
