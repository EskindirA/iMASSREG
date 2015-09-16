package org.lift.massreg.dto;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */

public class ParcelApprovalDetailDTO {
   private String upi;
    private double area;
    private boolean missingValue;
    private boolean hasDispute;

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public boolean hasMissingValue() {
        return missingValue;
    }

    public void hasMissingValue(boolean missingValue) {
        this.missingValue = missingValue;
    }

    public boolean hasDispute() {
        return hasDispute;
    }

    public void hasDispute(boolean hasDispute) {
        this.hasDispute = hasDispute;
    }

    
     
}
