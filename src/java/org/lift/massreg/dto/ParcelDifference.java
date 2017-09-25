package org.lift.massreg.dto;

import java.util.ArrayList;
import org.lift.massreg.entity.Guardian;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class ParcelDifference {

    private String upi;
    private boolean certificateNumber;
    private boolean holdingNumber;
    private boolean holdingLotNumber;
    private boolean otherEvidence;
    private boolean currentLandUse;
    private boolean teamNo;
    private boolean soilFertility;
    private boolean holding;
    private boolean meansOfAcquisition;
    private boolean acquisitionYear;
    private boolean surveyDate;
    private boolean mapSheetNo;
    private boolean status;
    private boolean encumbrance;
    private boolean hasDispute;

    private boolean personsWithInterestCount;
    private boolean guardiansCount;
    private boolean disputesCount;
    private boolean holdersCount;
    private boolean individualHolderDetails;
    private boolean personsWithInterestDetails;
    private boolean guardiansDetails;
    private boolean disputesDetails;

    private OrganizationHolderDifference organizationHolderDifference;
    private ArrayList<IndividualHolderDifference> individualHolders;
    private ArrayList<GuardianDifference> guardians;
    private ArrayList<PersonWithInterestDifference> personsWithInterest;
    private ArrayList<DisputeDifference> disputes;

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public boolean isCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(boolean certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public boolean isHoldingNumber() {
        return holdingNumber;
    }

    public void setHoldingNumber(boolean holdingNumber) {
        this.holdingNumber = holdingNumber;
    }
    
    public boolean isHoldingLotNumber() {
        return holdingLotNumber;
    }

    public void setHoldingLotNumber(boolean holdingLotNumber) {
        this.holdingLotNumber = holdingLotNumber;
    }

    public boolean isOtherEvidence() {
        return otherEvidence;
    }

    public void setOtherEvidence(boolean otherEvidence) {
        this.otherEvidence = otherEvidence;
    }

    public boolean isCurrentLandUse() {
        return currentLandUse;
    }

    public void setCurrentLandUse(boolean currentLandUse) {
        this.currentLandUse = currentLandUse;
    }

    public boolean isSoilFertility() {
        return soilFertility;
    }

    public void setSoilFertility(boolean soilFertility) {
        this.soilFertility = soilFertility;
    }

    public boolean isHolding() {
        return holding;
    }

    public void setHolding(boolean holding) {
        this.holding = holding;
    }

    public boolean isMeansOfAcquisition() {
        return meansOfAcquisition;
    }

    public void setMeansOfAcquisition(boolean meansOfAcquisition) {
        this.meansOfAcquisition = meansOfAcquisition;
    }

    public boolean isAcquisitionYear() {
        return acquisitionYear;
    }

    public void setAcquisitionYear(boolean acquisitionYear) {
        this.acquisitionYear = acquisitionYear;
    }

    public boolean isSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(boolean surveyDate) {
        this.surveyDate = surveyDate;
    }

    public boolean isMapSheetNo() {
        return mapSheetNo;
    }

    public void setMapSheetNo(boolean mapSheetNo) {
        this.mapSheetNo = mapSheetNo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isEncumbrance() {
        return encumbrance;
    }

    public void setEncumbrance(boolean encumbrance) {
        this.encumbrance = encumbrance;
    }

    public boolean isHasDispute() {
        return hasDispute;
    }

    public void setHasDispute(boolean hasDispute) {
        this.hasDispute = hasDispute;
    }

    public OrganizationHolderDifference getOrganizationHolderDifference() {
        return organizationHolderDifference;
    }

    public void setOrganizationHolderDifference(OrganizationHolderDifference organizationHolderDifference) {
        this.organizationHolderDifference = organizationHolderDifference;
    }

    public boolean isPersonsWithInterestCount() {
        return personsWithInterestCount;
    }

    public void setPersonsWithInterestCount(boolean personsWithInterestCount) {
        this.personsWithInterestCount = personsWithInterestCount;
    }

    public boolean isDisputesCount() {
        return disputesCount;
    }

    public boolean isTeamNo() {
        return teamNo;
    }

    public void setTeamNo(boolean teamNo) {
        this.teamNo = teamNo;
    }

    public void setDisputesCount(boolean disputesCount) {
        this.disputesCount = disputesCount;
    }

    public boolean isHoldersCount() {
        return holdersCount;
    }

    public void setHoldersCount(boolean holdersCount) {
        this.holdersCount = holdersCount;
    }

    public boolean isIndividualHolderDetails() {
        return individualHolderDetails;
    }

    public void setIndividualHolderDetails(boolean individualHolderDetails) {
        this.individualHolderDetails = individualHolderDetails;
    }

    public boolean isPersonsWithInterestDetails() {
        return personsWithInterestDetails;
    }

    public void setPersonsWithInterestDetails(boolean personsWithInterestDetails) {
        this.personsWithInterestDetails = personsWithInterestDetails;
    }

    public boolean isDisputesDetails() {
        return disputesDetails;
    }

    public void setDisputesDetails(boolean disputesDetails) {
        this.disputesDetails = disputesDetails;
    }

    public boolean isGuardiansCount() {
        return guardiansCount;
    }

    public void setGuardiansCount(boolean guardiansCount) {
        this.guardiansCount = guardiansCount;
    }

    public boolean isGuardiansDetails() {
        return guardiansDetails;
    }

    public void setGuardiansDetails(boolean guardiansDetails) {
        this.guardiansDetails = guardiansDetails;
    }

    
}
