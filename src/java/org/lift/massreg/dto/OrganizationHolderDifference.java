package org.lift.massreg.dto;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class OrganizationHolderDifference {

    private boolean name;
    private boolean organizationType;

    public boolean isName() {
        return name;
    }

    public void setName(boolean name) {
        this.name = name;
    }

    public boolean isOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(boolean organizationType) {
        this.organizationType = organizationType;
    }
    
    
}
