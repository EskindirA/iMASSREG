package org.lift.massreg.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.CurrentUserDTO;
import org.lift.massreg.util.Option;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class Parcel implements Entity {

    private String upi;
    private byte stage;
    private String stageText;
    private long registeredBy;
    private User registeredByUser;
    private Timestamp registeredOn;
    private double area;
    private int parcelId;
    private String certificateNumber;
    private String holdingNumber;
    private byte otherEvidence;
    private String otherEvidenceText;
    private byte currentLandUse;
    private String currentLandUseText;
    private byte soilFertility;
    private String soilFertilityText;
    private byte holding;
    private String holdingText;
    private byte meansOfAcquisition;
    private String meansOfAcquisitionText;
    private int acquisitionYear;
    private String surveyDate;
    private String mapSheetNo;
    private String status;
    private byte encumbrance;
    private String encumbranceText;
    private boolean hasDispute;
    private OrganizationHolder organaizationHolder;
    private ArrayList<IndividualHolder> individualHolders;
    private ArrayList<PersonWithInterest> personsWithInterest;
    private ArrayList<Dispute> disputes;

    private synchronized void fillInTextValues() {
        Option[] allStages = MasterRepository.getInstance().getAllStages();
        for (Option allStage : allStages) {
            if (allStage.getKey().equalsIgnoreCase(stage + "")) {
                stageText = allStage.getValue();
            }
        }
        Option[] allOtherEvidences = MasterRepository.getInstance().getAllOtherEvidenceTypes();
        for (Option allOtherEvidence : allOtherEvidences) {
            if (allOtherEvidence.getKey().equalsIgnoreCase(otherEvidence + "")) {
                otherEvidenceText = allOtherEvidence.getValue();
            }
        }

        Option[] allLandUseTypes = MasterRepository.getInstance().getAllCurrentLandUseTypes();
        for (Option allLandUseType : allLandUseTypes) {
            if (allLandUseType.getKey().equalsIgnoreCase(currentLandUse + "")) {
                currentLandUseText = allLandUseType.getValue();
            }
        }

        Option[] allSoilFertilityTypes = MasterRepository.getInstance().getAllSoilFertilityTypes();
        for (Option allSoilFertilityType : allSoilFertilityTypes) {
            if (allSoilFertilityType.getKey().equalsIgnoreCase(soilFertility + "")) {
                soilFertilityText = allSoilFertilityType.getValue();
            }
        }
        Option[] allHoldingTypes = MasterRepository.getInstance().getAllHoldingTypes();
        for (Option allHoldingType : allHoldingTypes) {
            if (allHoldingType.getKey().equalsIgnoreCase(holding + "")) {
                holdingText = allHoldingType.getValue();
            }
        }
        Option[] allMeansOfAcquisitionTypes = MasterRepository.getInstance().getAllMeansOfAcquisitionTypes();
        for (Option allMeansOfAcquisitionType : allMeansOfAcquisitionTypes) {
            if (allMeansOfAcquisitionType.getKey().equalsIgnoreCase(meansOfAcquisition + "")) {
                meansOfAcquisitionText = allMeansOfAcquisitionType.getValue();
            }
        }
        Option[] allEncumbranceTypes = MasterRepository.getInstance().getAllEncumbranceTypes();
        for (Option allEncumbranceType : allEncumbranceTypes) {
            if (allEncumbranceType.getKey().equalsIgnoreCase(encumbrance + "")) {
                encumbranceText = allEncumbranceType.getValue();
            }
        }
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

    public long getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(long registeredBy) {
        this.registeredBy = registeredBy;
    }

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getHoldingNumber() {
        return holdingNumber;
    }

    public void setHoldingNumber(String holdingNumber) {
        this.holdingNumber = holdingNumber;
    }

    public byte getOtherEvidence() {
        return otherEvidence;
    }

    public void setOtherEvidence(byte otherEvidence) {
        this.otherEvidence = otherEvidence;
    }

    public byte getCurrentLandUse() {
        return currentLandUse;
    }

    public void setCurrentLandUse(byte currentLandUse) {
        this.currentLandUse = currentLandUse;
    }

    public byte getSoilFertility() {
        return soilFertility;
    }

    public void setSoilFertility(byte soilFertility) {
        this.soilFertility = soilFertility;
    }

    public byte getHolding() {
        return holding;
    }

    public void setHolding(byte holding) {
        this.holding = holding;
    }

    public byte getMeansOfAcquisition() {
        return meansOfAcquisition;
    }

    public void setAcquisition(byte meansOfAcquisition) {
        this.meansOfAcquisition = meansOfAcquisition;
    }

    public int getAcquisitionYear() {
        return acquisitionYear;
    }

    public void setAcquisitionYear(int acquisitionYear) {
        this.acquisitionYear = acquisitionYear;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getMapSheetNo() {
        return mapSheetNo;
    }

    public void setMapSheetNo(String mapSheetNo) {
        this.mapSheetNo = mapSheetNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getEncumbrance() {
        return encumbrance;
    }

    public void setEncumbrance(byte encumbrance) {
        this.encumbrance = encumbrance;
    }

    public String getStageText() {
        if (stageText == null) {
            fillInTextValues();
        }
        return stageText;
    }

    public String getOtherEvidenceText() {
        if (otherEvidenceText == null) {
            fillInTextValues();
        }
        return otherEvidenceText;
    }

    public String getCurrentLandUseText() {
        if (currentLandUseText == null) {
            fillInTextValues();
        }
        return currentLandUseText;
    }

    public String getSoilFertilityText() {
        if (soilFertilityText == null) {
            fillInTextValues();
        }
        return soilFertilityText;
    }

    public String getHoldingText() {
        if (holdingText == null) {
            fillInTextValues();
        }
        return holdingText;
    }

    public String getMeansOfAcquisitionText() {
        if (meansOfAcquisitionText == null) {
            fillInTextValues();
        }
        return meansOfAcquisitionText;
    }

    public String getStatus() {
        return status;
    }

    public String getEncumbranceText() {
        if (encumbranceText == null) {
            fillInTextValues();
        }
        return encumbranceText;
    }

    public User getRegisteredByUser() {
        if (registeredByUser == null) {
            registeredByUser = MasterRepository.getInstance().getUser(this.getRegisteredBy());
        }
        return registeredByUser;
    }

    public OrganizationHolder getOrganizationHolder() {
        return organaizationHolder;
    }

    public ArrayList<IndividualHolder> getIndividualHolders() {
        if (individualHolders == null) {
            individualHolders = MasterRepository.getInstance().getAllIndividualHolders(upi, stage);
        }
        return individualHolders;
    }

    public ArrayList<PersonWithInterest> getPersonsWithInterest() {
        if (personsWithInterest == null) {
            personsWithInterest = MasterRepository.getInstance().getAllPersonsWithInterest(upi, stage);
        }
        return personsWithInterest;
    }

    public void addIndividualHolder(IndividualHolder individualHolder) {
        individualHolders.add(individualHolder);
    }

    public void addPersonWithInterest(PersonWithInterest personWithInterest) {
        personsWithInterest.add(personWithInterest);
    }

    public void removeIndividualHolder(IndividualHolder individualHolder) {
        individualHolders.remove(individualHolder);
    }

    public ArrayList<Dispute> getDisputes() {
        if ((disputes == null) && (hasDispute)) {
                disputes = MasterRepository.getInstance().getAllDisputes(upi, stage);
        }
        return disputes;
    }

    public void addDispute(Dispute dispute) {
        disputes.add(dispute);
    }

    public void removeDispute(Dispute dispute) {
        disputes.remove(dispute);
    }

    public boolean saveAll() {
        boolean returnValue = true;
        ///TODO:saveParcelOnly()
        ///TODO:saveParcelHolders()
        return returnValue;
    }

    public boolean saveParcelOnly() {
        return MasterRepository.getInstance().save(this);
    }

    public boolean saveParcelDisputes() {
        boolean returnValue = true;
        ///TODO:
        return returnValue;
    }

    public boolean hasDispute() {
        return hasDispute;
    }

    public void hasDispute(boolean hasDispute) {
        this.hasDispute = hasDispute;
    }

    public OrganizationHolder getOrganaizationHolder() {
        return organaizationHolder;
    }

    public void setOrganaizationHolder(OrganizationHolder organaizationHolder) {
        this.organaizationHolder = organaizationHolder;
    }

    @Override
    public boolean validateForSave() {
        ///TODO:
        return true;
    }

    public void setIndividualHolders(ArrayList<IndividualHolder> individualHolders) {
        this.individualHolders = individualHolders;
    }

    public void setPersonsWithInterest(ArrayList<PersonWithInterest> personWithInterest) {
        this.personsWithInterest = personsWithInterest;
    }

    public void setDisputes(ArrayList<Dispute> disputes) {
        this.disputes = disputes;
    }

    public int getDisputeCount() {
        int count = 0;
        if (this.getDisputes() != null && hasDispute) {
            count = disputes.size();
        }
        return count;
    }

    public int getHolderCount() {
        int count = 0;
        if (this.getHolding() == 1) {
            if (individualHolders != null) {
                count = individualHolders.size();
            }
        } else if (organaizationHolder != null) {
            count = 1;
        }
        return count;
    }

    public boolean canEdit(CurrentUserDTO cudto) {
        boolean returnValue = true;
        boolean firstentry = MasterRepository.getInstance().parcelExists(upi, (byte) 1);
        boolean secondentry = MasterRepository.getInstance().parcelExists(upi, (byte) 2);
        boolean supervisor = MasterRepository.getInstance().parcelExists(upi, (byte) 3);

        switch (cudto.getRole()) {
            case FIRST_ENTRY_OPERATOR:
                if (secondentry || supervisor) {
                    returnValue = false;
                }
                break;
            case SECOND_ENTRY_OPERATOR:
                if (supervisor) {
                    returnValue = false;
                }
                break;
            case SUPERVISOR:
                ///TODO
                break;
        }
        return returnValue;
    }

    public Dispute getDispute(byte stage, Timestamp registeredOn) {
        Dispute returnValue = new Dispute();
        if (stage == this.getStage()) {
            ArrayList<Dispute> allDisputes = getDisputes();
            for (int i = 0; i < allDisputes.size(); i++) {
                if (allDisputes.get(i).getRegisteredOn().equals(registeredOn)) {
                    returnValue = allDisputes.get(i);
                    break;
                }
            }
        }
        return returnValue;
    }

    public IndividualHolder getIndividualHolder(String holderId, byte stage, Timestamp registeredOn) {
        IndividualHolder returnValue = new IndividualHolder();
        if (stage == this.getStage()) {
            ArrayList<IndividualHolder> allIndividualHolders = getIndividualHolders();
            for (int i = 0; i < allIndividualHolders.size(); i++) {
                if (allIndividualHolders.get(i).getRegisteredOn().equals(registeredOn)
                        && allIndividualHolders.get(i).getId().equalsIgnoreCase(holderId)) {
                    returnValue = allIndividualHolders.get(i);
                    break;
                }
            }
        }
        return returnValue;
    }

    public PersonWithInterest getPersonWithInterest(byte stage, Timestamp registeredOn) {
        PersonWithInterest returnValue = new PersonWithInterest();
        if (stage == this.getStage()) {
            ArrayList<PersonWithInterest> allPersonsWithInterest = getPersonsWithInterest();
            for (int i = 0; i < allPersonsWithInterest.size(); i++) {
                if (allPersonsWithInterest.get(i).getRegisteredOn().equals(registeredOn)) {
                    returnValue = allPersonsWithInterest.get(i);
                    break;
                }
            }
        }
        return returnValue;
    }

    public boolean remove() {
        return delete(registeredOn, upi, stage);
    }

    public static boolean delete(Timestamp registeredOn, String upi, byte stage) {
        return MasterRepository.getInstance().deleteParcel(registeredOn, upi, stage);
    }

    public void complete() {
        MasterRepository.getInstance().completeParcel(getUpi(), getStage(), getRegisteredOn());
    }

    @Override
    public boolean validateForUpdate() {
        return true;
    }
}
