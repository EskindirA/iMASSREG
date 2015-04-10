package org.lift.massreg.dto;

import java.util.ArrayList;
import org.lift.massreg.entity.*;
import org.lift.massreg.util.CommonStorage;

/**
 *
 * @author yoseph
 */
public class ParcelPublicDisplayDTO {
    private String upi;
    private int parcelId;
    private String mapSheetNo;
    private boolean hasDispute;
    private double area;
    private byte holding;
    private ArrayList<String> coHolders;
    private ArrayList<String> guardians;

    public ParcelPublicDisplayDTO() {
        coHolders = new ArrayList<>();
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

    public ArrayList<String> getOtherHolders() {
        if(coHolders.isEmpty()){
            coHolders.add(CommonStorage.getText("dose_not_exist"));
        }
        return coHolders;
    }

    public ArrayList<String> getGuardians() {
        if(guardians.isEmpty()){
            guardians.add(CommonStorage.getText("dose_not_exist"));
        }
        return guardians;
    }


    public void addHolder(String holder){
        coHolders.add(holder);
    }
    public void addGuardian(String guardian){
        guardians.add(guardian);
    }
}
