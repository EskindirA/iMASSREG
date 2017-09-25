package org.lift.massreg.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.Change;
import org.lift.massreg.dto.OrganizationHolderDifference;
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

    public boolean remove(HttpServletRequest request) {
        return delete(request,upi, stage);
    }

    public static boolean delete(HttpServletRequest request,String upi, byte stage) {

        return MasterRepository.getInstance().deleteOrganizationHolder(request, upi, stage);
    }

    @Override
    public boolean validateForUpdate() {
        return true;
    }

    public boolean equalsOrganizationHolder(OrganizationHolder obj) {
        boolean returnValue = true;
        if (this.getName() != null && obj.getName() != null) {
            if (!this.getName().trim().equalsIgnoreCase(obj.getName().trim())) {
                returnValue = false;
            }
        }
        if (this.getOrganizationType() != obj.getOrganizationType()) {
            returnValue = false;
        }
        return returnValue;
    }

    void commit() {
        MasterRepository.getInstance().commit(this);
    }

    public boolean submitForCorrection() {
        return MasterRepository.getInstance().submitForCorrection(this);
    }

    public void submitForPPDCorrection() {
        MasterRepository.getInstance().submitForPPDCorrection(this);
    }
    
    public static OrganizationHolderDifference difference(OrganizationHolder firstHolder, OrganizationHolder secondHolder) {
        OrganizationHolderDifference returnValue = new OrganizationHolderDifference();
        if (firstHolder.getName() != null && secondHolder.getName() != null) {
            if (!firstHolder.getName().trim().equalsIgnoreCase(secondHolder.getName().trim())) {
                returnValue.setName(true);
            }
        }
        if (firstHolder.getOrganizationType() != secondHolder.getOrganizationType()) {
            returnValue.setOrganizationType(true);
        }
        return returnValue;
    }

    public ArrayList<Change> getDifferenceForChangeLog(OrganizationHolder newOrganizationHolder) {
        ArrayList<Change> returnValue = new ArrayList<>();

        if (!this.getName().equalsIgnoreCase(newOrganizationHolder.getName())) {
            returnValue.add(new Change("organizationname", this.getName() + "", newOrganizationHolder.getName() + ""));
        }
        if (this.getOrganizationType() != newOrganizationHolder.getOrganizationType()) {
            returnValue.add(new Change("organizationtype", this.getOrganizationType() + "", newOrganizationHolder.getOrganizationType() + ""));
        }
        return returnValue;
    }
    public boolean submitForMinorCorrection() {
        return MasterRepository.getInstance().submitForMinorCorrection(this);
    }
    public boolean submitForMajorCorrection() {
        return MasterRepository.getInstance().submitForMajorCorrection(this);
    }
    public boolean submitToConfirmed() {
        return MasterRepository.getInstance().submitToConfirmed(this);
    }
    public boolean approve() {
        return MasterRepository.getInstance().approve(this);
    }
}
