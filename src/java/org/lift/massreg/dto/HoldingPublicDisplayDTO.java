package org.lift.massreg.dto;

import java.util.ArrayList;
import org.lift.massreg.dto.ParcelPublicDisplayDTO;
import org.lift.massreg.util.CommonStorage;

public class HoldingPublicDisplayDTO {

    private String holdingNumber;
    private ArrayList<HoldingParcelPublicDisplayDTO> parcels;
    private ArrayList<HoldingHolderDTO> holders;

    public HoldingPublicDisplayDTO() {
        parcels = new ArrayList<>();
        holders = new ArrayList<>();
    }

    public String getHoldingNumber() {
        return holdingNumber == null ? "" : holdingNumber;
    }

    public void setHoldingNumber(String holdingNumber) {
        this.holdingNumber = holdingNumber;
    }

    public ArrayList<HoldingParcelPublicDisplayDTO> getParcels() {
        return parcels;
    }

    public void addParcel(HoldingParcelPublicDisplayDTO parcel) {
        parcels.add(parcel);
    }

    public void setParcels(ArrayList<HoldingParcelPublicDisplayDTO> parcels) {
        this.parcels = parcels;
    }

    public ArrayList<HoldingHolderDTO> getHolders() {
        return holders;
    }

    public void addHolder(HoldingHolderDTO holder) {
        holders.add(holder);
    }

    public void setHolders(ArrayList<HoldingHolderDTO> holders) {
        this.holders = holders;
    }

   

}
