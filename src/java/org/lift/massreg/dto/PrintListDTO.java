package org.lift.massreg.dto;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @since 2.1
 */
public class PrintListDTO {
    private String UPI;
    private String holderName;
    private String photoId;

    public String getUPI() {
        return UPI;
    }

    public void setUPI(String UPI) {
        this.UPI = UPI;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
    
    
}
