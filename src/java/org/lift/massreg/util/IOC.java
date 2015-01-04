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

        pages.put(Constants.INDEX_VIEW_PROFILE, "viewProfile.jsp");
        pages.put(Constants.INDEX_EDIT_PROFILE, "editProfile.jsp");
    }
    

    public static String getPage(int id) {
        return "/private/forms/" + pages.get(id);
    }
}
