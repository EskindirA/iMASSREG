package org.lift.massreg.util;

import java.util.ArrayList;
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
    private static final HashMap<Integer, String> titles;

    static {
        pages = new HashMap<>();
        titles = new HashMap<>();

        pages.put(Constants.INDEX_ERROR, "error.jsp");
        pages.put(Constants.INDEX_FIRST_ENTRY_WELCOME, "welcomedeo.jsp");
        pages.put(Constants.INDEX_SECOND_ENTRY_WELCOME, "welcomedeo.jsp");
        pages.put(Constants.INDEX_SUPERVISOR_WELCOME, "welcomesupervisor.jsp");
        pages.put(Constants.INDEX_MESSAGE, "message.jsp");
        pages.put(Constants.INDEX_ADD_PARCEL_FEO, "addParcelFEO.jsp");
        pages.put(Constants.INDEX_ADD_PARCEL_SEO, "addParcelSEO.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_FEO, "viewParcelFEO.jsp");
        pages.put(Constants.INDEX_VIEW_PARCEL_SEO, "viewParcelSEO.jsp");
        pages.put(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_FEO, "individualHoldersListFEO.jsp");
        pages.put(Constants.INDEX_ADD_ORGANIZATION_HOLDERS_FEO, "addOrganizationHolderFEO.jsp");
        pages.put(Constants.INDEX_ADD_ORGANIZATION_HOLDERS_SEO, "addOrganizationHolderSEO.jsp");
        pages.put(Constants.INDEX_VIEW_ORGANIZATION_HOLDERS_FEO, "viewOrganizationHolderFEO.jsp");
        pages.put(Constants.INDEX_VIEW_ORGANIZATION_HOLDERS_SEO, "viewOrganizationHolderSEO.jsp");
        pages.put(Constants.INDEX_DISPUTE_LIST_FEO, "disputeListFEO.jsp");
        pages.put(Constants.INDEX_EDIT_PARCEL_FEO, "editParcelFEO.jsp");
        pages.put(Constants.INDEX_VIEW_DISPUTE_FEO, "viewDisputeFEO.jsp");
        pages.put(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_FEO, "viewIndividualHolderFEO.jsp");
        pages.put(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_FEO, "editIndividualHolderFEO.jsp");
        pages.put(Constants.INDEX_EDIT_DISPUTE_FEO, "editDisputeFEO.jsp");
        pages.put(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_FEO, "editOrganizationHolderFEO.jsp");
        pages.put(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_FEO, "personWithInterestListFEO.jsp");
        pages.put(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_FEO, "viewPersonWithInterestFEO.jsp");
        pages.put(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_SEO, "viewPersonWithInterestSEO.jsp");
        pages.put(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_FEO, "editPersonWithInterestFEO.jsp");
        pages.put(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_SEO, "editPersonWithInterestSEO.jsp");
        
        titles.put(Constants.INDEX_ERROR, "Error");
        titles.put(Constants.INDEX_FIRST_ENTRY_WELCOME, "Welcome");
        titles.put(Constants.INDEX_SECOND_ENTRY_WELCOME, "Welcome");
        titles.put(Constants.INDEX_SUPERVISOR_WELCOME, "Welcome");
        titles.put(Constants.INDEX_ADD_PARCEL_FEO, "Add New Parcel");
        titles.put(Constants.INDEX_ADD_PARCEL_SEO, "Add New Parcel");
    }

    public static String getPage(int id) {
        return "/private/forms/" + pages.get(id);
    }

    public static String getTitle(int id) {
        return titles.get(id);
    }
}
