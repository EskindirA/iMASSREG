package org.lift.massreg.entity;

import java.sql.Timestamp;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.util.Option;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class OrganizationHolder implements Entity {

    private String name;
    private byte organizationType;
    private String organizationTypeText;

    private String upi;
    private byte stage;
    private long registeredby;
    private Timestamp registeredon;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(byte organizationType) {
        this.organizationType = organizationType;
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

    public long getRegisteredby() {
        return registeredby;
    }

    public void setRegisteredby(long registeredby) {
        this.registeredby = registeredby;
    }

    public Timestamp getRegisteredon() {
        return registeredon;
    }

    public void setRegisteredon(Timestamp registeredon) {
        this.registeredon = registeredon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private void fillInTextValues() {
        Option[] allOrganizationTypes = MasterRepository.getInstance().getAllOrganizationTypes();
        for (Option allOrganizationType : allOrganizationTypes) {
            if (allOrganizationType.getKey().equalsIgnoreCase(organizationType + "")) {
                organizationTypeText = allOrganizationType.getValue();
            }
        }
    }

    public String getOrganizationTypeText() {
        if (organizationTypeText == null) {
            fillInTextValues();
        }
        return organizationTypeText;
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
        return delete(upi, stage);
    }
    public static boolean delete(String upi, byte stage) {

        return MasterRepository.getInstance().deleteOrganizationHolder(upi, stage);
    }
}
