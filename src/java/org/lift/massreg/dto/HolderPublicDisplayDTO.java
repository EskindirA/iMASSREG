package org.lift.massreg.dto;

import java.util.ArrayList;
import org.lift.massreg.dto.ParcelPublicDisplayDTO;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class HolderPublicDisplayDTO {

    private String id;
    private ArrayList<ParcelPublicDisplayDTO> parcels;
    private String name;
    private String sex;

    public HolderPublicDisplayDTO() {
        parcels = new ArrayList<>();
    }

    public void addParcel(ParcelPublicDisplayDTO parcel) {
        parcels.add(parcel);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ParcelPublicDisplayDTO> getParcels() {
        return parcels;
    }

    public void setParcels(ArrayList<ParcelPublicDisplayDTO> parcels) {
        this.parcels = parcels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
