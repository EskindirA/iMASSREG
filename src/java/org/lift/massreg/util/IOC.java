package org.lift.massreg.util;

import java.util.HashMap;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class IOC {

    private static final HashMap<Integer, String> pages;

    static {
        pages = new HashMap<>();

        pages.put(Constants.INDEX_MESSAGE, "message.jsp");
        pages.put(Constants.INDEX_ERROR, "error.jsp");

        pages.put(Constants.INDEX_WELCOME_FEO, "welcomeDEO.jsp");
        pages.put(Constants.INDEX_WELCOME_SEO, "welcomeDEO.jsp");
        pages.put(Constants.INDEX_WELCOME_SUPERVISOR, "welcomeSupervisor.jsp");

        pages.put(Constants.INDEX_ADD_PARCEL_FEO, "addParcelDEO.jsp");
        pages.put(Constants.INDEX_ADD_PARCEL_SEO, "addParcelDEO.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_FEO, "viewParcelDEO.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_SEO, "viewParcelDEO.jsp");
        pages.put(Constants.INDEX_EDIT_PARCEL_FEO, "editParcelDEO.jsp");
        pages.put(Constants.INDEX_EDIT_PARCEL_SEO, "editParcelDEO.jsp");

        pages.put(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_FEO, "individualHoldersListDEO.jsp");
        pages.put(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_SEO, "individualHoldersListDEO.jsp");
        pages.put(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_FEO, "viewIndividualHolderDEO.jsp");
        pages.put(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_SEO, "viewIndividualHolderDEO.jsp");
        pages.put(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_FEO, "editIndividualHolderDEO.jsp");
        pages.put(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_SEO, "editIndividualHolderDEO.jsp");

        pages.put(Constants.INDEX_ADD_ORGANIZATION_HOLDER_FEO, "addOrganizationHolderDEO.jsp");
        pages.put(Constants.INDEX_ADD_ORGANIZATION_HOLDER_SEO, "addOrganizationHolderDEO.jsp");
        pages.put(Constants.INDEX_VIEW_ORGANIZATION_HOLDER_FEO, "viewOrganizationHolderDEO.jsp");
        pages.put(Constants.INDEX_VIEW_ORGANIZATION_HOLDER_SEO, "viewOrganizationHolderDEO.jsp");
        pages.put(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_FEO, "editOrganizationHolderDEO.jsp");
        pages.put(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_SEO, "editOrganizationHolderDEO.jsp");
        
        pages.put(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_FEO, "personWithInterestListDEO.jsp");
        pages.put(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_SEO, "personWithInterestListDEO.jsp");
        pages.put(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_FEO, "viewPersonWithInterestDEO.jsp");
        pages.put(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_SEO, "viewPersonWithInterestDEO.jsp");
        pages.put(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_FEO, "editPersonWithInterestDEO.jsp");
        pages.put(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_SEO, "editPersonWithInterestDEO.jsp");

        pages.put(Constants.INDEX_DISPUTE_LIST_FEO, "disputeListDEO.jsp");
        pages.put(Constants.INDEX_DISPUTE_LIST_SEO, "disputeListDEO.jsp");
        pages.put(Constants.INDEX_VIEW_DISPUTE_FEO, "viewDisputeDEO.jsp");
        pages.put(Constants.INDEX_VIEW_DISPUTE_SEO, "viewDisputeDEO.jsp");
        pages.put(Constants.INDEX_EDIT_DISPUTE_FEO, "editDisputeDEO.jsp");
        pages.put(Constants.INDEX_EDIT_DISPUTE_SEO, "editDisputeDEO.jsp");
        pages.put(Constants.INDEX_DISCREPANCY_SEO, "discrepancyDEO.jsp");
        pages.put(Constants.INDEX_DISCREPANCY_MCDEO,"discrepancyMCDEO.jsp");
        
        pages.put(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_SUPERVISOR, "editOrganizationHolderSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_ORGANIZATION_HOLDER_SUPERVISOR, "viewOrganizationHolderSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_PARCEL_SUPERVISOR, "editParcelSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_SUPERVISOR, "viewParcelSupervisor.jsp");
        pages.put(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_SUPERVISOR, "individualHoldersListSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_SUPERVISOR, "editIndividualHolderSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_SUPERVISOR, "viewIndividualHolderSupervisor.jsp");

        pages.put(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_SUPERVISOR, "personWithInterestListSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_SUPERVISOR, "editPersonWithInterestSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_SUPERVISOR, "viewPersonWithInterestSupervisor.jsp");
        pages.put(Constants.INDEX_ADD_ORGANIZATION_HOLDER_SUPERVISOR, "addOrganizationHoldeSupervisor.jsp");

        pages.put(Constants.INDEX_DISPUTE_LIST_SUPERVISOR, "disputeListSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_DISPUTE_SUPERVISOR, "editDisputeSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_DISPUTE_SUPERVISOR, "viewDisputeSupervisor.jsp");
        pages.put(Constants.INDEX_GUARDIANS_LIST_SUPERVISOR, "guardianListSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_GUARDIAN_SUPERVISOR, "viewGuardianSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_GUARDIAN_SUPERVISOR, "editGuardianSupervisor.jsp");

        pages.put(Constants.INDEX_VIEW_PROFILE, "viewProfile.jsp");
        pages.put(Constants.INDEX_EDIT_PROFILE, "editProfile.jsp");

        pages.put(Constants.INDEX_WELCOME_ADMINISTRATOR, "welcomeAdministrator.jsp");

        pages.put(Constants.INDEX_VIEW_USER_ADMINISTRATOR, "viewUser.jsp");
        pages.put(Constants.INDEX_EDIT_USER_ADMINISTRATOR, "editUser.jsp");
        pages.put(Constants.INDEX_RELOAD, "reload.jsp");
        pages.put(Constants.INDEX_DOWNLOAD_REPORT, "downloadReport.jsp");
        pages.put(Constants.INDEX_GUARDIANS_LIST_FEO, "guardianListDEO.jsp");
        pages.put(Constants.INDEX_GUARDIANS_LIST_SEO, "guardianListDEO.jsp");
        pages.put(Constants.INDEX_VIEW_GUARDIAN_FEO, "viewGuardianDEO.jsp");
        pages.put(Constants.INDEX_VIEW_GUARDIAN_SEO, "viewGuardianDEO.jsp");
        pages.put(Constants.INDEX_EDIT_GUARDIAN_FEO, "editGuardianDEO.jsp");
        pages.put(Constants.INDEX_EDIT_GUARDIAN_SEO, "editGuardianDEO.jsp");
        pages.put(Constants.INDEX_PUBLIC_DISPLAY_HOLDER, "publicDisplayHolder.jsp");
        pages.put(Constants.INDEX_PUBLIC_DISPLAY_HOLDING, "publicDisplayHolding.jsp");

        pages.put(Constants.INDEX_WELCOME_PDC, "welcomePDC.jsp");
        pages.put(Constants.INDEX_WELCOME_KEBELE_PDC, "welcomeKebelePDC.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_PDC, "viewParcelPDC.jsp");

        pages.put(Constants.INDEX_WELCOME_MCO, "welcomeMCO.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_MCO, "viewParcelMCO.jsp");
        pages.put(Constants.INDEX_EDIT_PARCEL_MCO, "editParcelMCO.jsp");
        pages.put(Constants.INDEX_ADD_ORGANIZATION_HOLDER_MCO, "addOrganizationHolderMCO.jsp");
        pages.put(Constants.INDEX_VIEW_ORGANIZATION_HOLDER_MCO, "viewOrganizationHolderMCO.jsp");
        pages.put(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_MCO, "individualHoldersListMCO.jsp");
        pages.put(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_MCO, "editOrganizationHolderMCO.jsp");
        pages.put(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_MCO, "viewIndividualHolderMCO.jsp");
        pages.put(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_MCO, "editIndividualHolderMCO.jsp");
        pages.put(Constants.INDEX_GUARDIANS_LIST_MCO, "guardianListMCO.jsp");
        pages.put(Constants.INDEX_VIEW_GUARDIAN_MCO,"viewGuardianMCO.jsp");
        pages.put(Constants.INDEX_EDIT_GUARDIAN_MCO,"editGuardianMCO.jsp");
        pages.put(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_MCO, "personWithInterestListMCO.jsp");
        pages.put(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_MCO,"viewPersonWithInterestMCO.jsp");
        pages.put(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_MCO,"editPersonWithInterestMCO.jsp");
        pages.put(Constants.INDEX_DISPUTE_LIST_MCO,"disputeListMCO.jsp");
        pages.put(Constants.INDEX_VIEW_DISPUTE_MCO,"viewDisputeMCO.jsp");
        pages.put(Constants.INDEX_EDIT_DISPUTE_MCO,"editDisputeMCO.jsp");
        
        pages.put(Constants.INDEX_WELCOME_MCDEO,"welcomeMCDEO.jsp");
        pages.put(Constants.INDEX_ADD_PARCEL_MCDEO, "addParcelMCDEO.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_MCDEO, "viewParcelMCDEO.jsp");
        pages.put(Constants.INDEX_EDIT_PARCEL_MCDEO, "editParcelMCDEO.jsp");
        pages.put(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_MCDEO, "individualHoldersListMCDEO.jsp");
        pages.put(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_MCDEO, "viewIndividualHolderMCDEO.jsp");
        pages.put(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_MCDEO, "editIndividualHolderMCDEO.jsp");
        pages.put(Constants.INDEX_ADD_ORGANIZATION_HOLDER_MCDEO, "addOrganizationHolderMCDEO.jsp");
        pages.put(Constants.INDEX_VIEW_ORGANIZATION_HOLDER_MCDEO, "viewOrganizationHolderMCDEO.jsp");
        pages.put(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_MCDEO, "editOrganizationHolderMCDEO.jsp");
        pages.put(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_MCDEO, "personWithInterestListMCDEO.jsp");
        pages.put(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_MCDEO, "viewPersonWithInterestMCDEO.jsp");
        pages.put(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_MCDEO, "editPersonWithInterestMCDEO.jsp");
        pages.put(Constants.INDEX_DISPUTE_LIST_MCDEO, "disputeListMCDEO.jsp");
        pages.put(Constants.INDEX_VIEW_DISPUTE_MCDEO, "viewDisputeMCDEO.jsp");
        pages.put(Constants.INDEX_EDIT_DISPUTE_MCDEO, "editDisputeMCDEO.jsp");
        pages.put(Constants.INDEX_GUARDIANS_LIST_MCDEO, "guardianListMCDEO.jsp");
        pages.put(Constants.INDEX_VIEW_GUARDIAN_MCDEO, "viewGuardianMCDEO.jsp");
        pages.put(Constants.INDEX_EDIT_GUARDIAN_MCDEO, "editGuardianMCDEO.jsp");
        
        pages.put(Constants.INDEX_WELCOME_MCSUPERVISOR,"welcomeMCSupervisor.jsp");

        pages.put(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_MCSUPERVISOR, "editOrganizationHolderMCSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_ORGANIZATION_HOLDER_MCSUPERVISOR, "viewOrganizationHolderMCSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_PARCEL_MCSUPERVISOR, "editParcelMCSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_MCSUPERVISOR, "viewParcelMCSupervisor.jsp");
        pages.put(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_MCSUPERVISOR, "individualHoldersListMCSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_MCSUPERVISOR, "editIndividualHolderMCSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_MCSUPERVISOR, "viewIndividualHolderMCSupervisor.jsp");

        pages.put(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_MCSUPERVISOR, "personWithInterestListMCSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_MCSUPERVISOR, "editPersonWithInterestMCSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_MCSUPERVISOR, "viewPersonWithInterestMCSupervisor.jsp");
        pages.put(Constants.INDEX_ADD_ORGANIZATION_HOLDER_MCSUPERVISOR, "addOrganizationHoldeMCSupervisor.jsp");

        pages.put(Constants.INDEX_DISPUTE_LIST_MCSUPERVISOR, "disputeListMCSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_DISPUTE_MCSUPERVISOR, "editDisputeMCSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_DISPUTE_MCSUPERVISOR, "viewDisputeMCSupervisor.jsp");
        pages.put(Constants.INDEX_GUARDIANS_LIST_MCSUPERVISOR, "guardianListMCSupervisor.jsp");
        pages.put(Constants.INDEX_VIEW_GUARDIAN_MCSUPERVISOR, "viewGuardianMCSupervisor.jsp");
        pages.put(Constants.INDEX_EDIT_GUARDIAN_MCSUPERVISOR, "editGuardianMCSupervisor.jsp");
    
        pages.put(Constants.INDEX_PUBLIC_DISPLAY_CHECKLIST_PDC,"pdCheckList.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_WC,"viewParcelWC.jsp");
        pages.put(Constants.INDEX_WELCOME_WC, "welcomeWC.jsp");
        pages.put(Constants.INDEX_CERTIFICATE_PRINTING_CHECKLIST_WC,"cpCheckList.jsp");
        pages.put(Constants.INDEX_PRINT_CERTIFCATE_WC,"certificateWC.jsp");
        pages.put(Constants.INDEX_DAILY_REPORT_ADMINISTRATOR,"dailyReportAdmin.jsp");
        pages.put(Constants.INDEX_DOWNLOAD_PUBLIC_DISPLAY,"downloadReport.jsp");
        pages.put(Constants.INDEX_PHOTO_PRINTOUT,"downloadPhotoPrintout.jsp");

    }

    public static String getPage(int id) {
        return "/private/forms/" + pages.get(id);
    }
}
