package org.lift.massreg.util;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public interface Constants {

    public static final String[] STATUS = {"active", "removed", "incomplete"};
    public static final String ACTION_WELCOME = "WELCOME";
    public static final String ACTION_WELCOME_PART = "WELCOME_PART";
    public static final String ACTION_FIND_PARCEL_FEO = "FIND_PARCEL_FEO";
    public static final String ACTION_FIND_PARCEL_SEO = "FIND_PARCEL_SEO";
    public static final String ACTION_ERROR = "ERROR";
    public static final String ACTION_LOGOUT = "LOGOUT";
    public static final String ACTION_ADD_PARCEL_FEO = "ADD_PARCEL_FEO";
    public static final String ACTION_ADD_PARCEL_SEO = "ADD_PARCEL_SEO";
    public static final String ACTION_SAVE_PARCEL_FEO = "SAVE_PARCEL_FEO";
    public static final String ACTION_SAVE_PARCEL_SEO = "SAVE_PARCEL_SEO";
    public static final String ACTION_VIEW_PARCEL_FEO = "VIEW_PARCEL_FEO";
    public static final String ACTION_VIEW_PARCEL_SEO = "VIEW_PARCEL_SEO";
    public static final String ACTION_VIEW_HOLDER_FEO = "VIEW_HOLDER_FEO";
    public static final String ACTION_VIEW_HOLDER_SEO = "VIEW_HOLDER_SEO";
    public static final String ACTION_EDIT_PARCEL_FEO = "EDIT_PARCEL_FEO";
    public static final String ACTION_EDIT_PARCEL_SEO = "EDIT_PARCEL_SEO";
    public static final String ACTION_ADD_HOLDER_FEO = "ADD_HOLDER_FEO";
    public static final String ACTION_ADD_HOLDER_SEO = "ADD_HOLDER_SEO";
    public static final String ACTION_SAVE_HOLDER_FEO = "SAVE_HOLDER_FEO";
    public static final String ACTION_SAVE_HOLDER_SEO = "SAVE_HOLDER_SEO";
    public static final String ACTION_DISPUTE_LIST_FEO = "DISPUTE_LIST_FEO";
    public static final String ACTION_DISPUTE_LIST_SEO = "DISPUTE_LIST_SEO";
    public static final String ACTION_SAVE_DISPUTE_FEO = "SAVE_DISPUTE_FEO";
    public static final String ACTION_SAVE_DISPUTE_SEO = "SAVE_DISPUTE_SEO";
    public static final String ACTION_UPDATE_PARCEL_FEO = "UPDATE_PARCEL_FEO";
    public static final String ACTION_UPDATE_PARCEL_SEO = "UPDATE_PARCEL_SEO";
    public static final String ACTION_VIEW_DISPUTE_FEO = "VIEW_DISPUTE_FEO";
    public static final String ACTION_VIEW_DISPUTE_SEO = "VIEW_DISPUTE_SEO";
    public static final String ACTION_VIEW_INDIVIDUAL_HOLDER_FEO = "VIEW_INDIVIDUAL_HOLDER_FEO";
    public static final String ACTION_VIEW_INDIVIDUAL_HOLDER_SEO = "VIEW_INDIVIDUAL_HOLDER_SEO";
    public static final String ACTION_EDIT_INDIVIDUAL_HOLDER_FEO = "EDIT_INDIVIDUAL_HOLDER_FEO";
    public static final String ACTION_EDIT_INDIVIDUAL_HOLDER_SEO = "EDIT_INDIVIDUAL_HOLDER_SEO";

    // pages
    public static final int INDEX_ERROR = 0;
    public static final int INDEX_FIRST_ENTRY_WELCOME = 1;
    public static final int INDEX_SECOND_ENTRY_WELCOME = 2;
    public static final int INDEX_SUPERVISOR_WELCOME = 3;
    public static final int INDEX_MESSAGE = 4;
    public static final int INDEX_ADD_PARCEL_FEO = 5;
    public static final int INDEX_ADD_PARCEL_SEO = 6;
    public static final int INDEX_VIEW_PARCEL_FEO = 7;
    public static final int INDEX_VIEW_PARCEL_SEO = 8;
    public static final int INDEX_INDIVIDUAL_HOLDERS_LIST_FEO = 9;
    public static final int INDEX_INDIVIDUAL_HOLDERS_LIST_SEO = 10;
    public static final int INDEX_ADD_ORGANIZATION_HOLDERS_FEO = 11;
    public static final int INDEX_ADD_ORGANIZATION_HOLDERS_SEO = 12;
    public static final int INDEX_VIEW_ORGANIZATION_HOLDERS_FEO = 13;
    public static final int INDEX_VIEW_ORGANIZATION_HOLDERS_SEO = 14;
    public static final int INDEX_DISPUTE_LIST_FEO = 15;
    public static final int INDEX_DISPUTE_LIST_SEO = 16;
    public static final int INDEX_EDIT_PARCEL_FEO = 17;
    public static final int INDEX_EDIT_PARCEL_SEO = 18;
    public static final int INDEX_VIEW_DISPUTE_FEO = 19;
    public static final int INDEX_VIEW_DISPUTE_SEO = 20;
    public static final int INDEX_VIEW_INDIVIDUAL_HOLDER_FEO = 21;
    public static final int INDEX_VIEW_INDIVIDUAL_HOLDER_SEO = 22;
    public static final int INDEX_EDIT_INDIVIDUAL_HOLDER_FEO = 23;
    public static final int INDEX_EDIT_INDIVIDUAL_HOLDER_SEO = 24;

    public enum ROLE {

        FIRST_ENTRY_OPERATOR,
        SECOND_ENTRY_OPERATOR,
        SUPERVISOR,
        ADMINISTRATOR
    };

    public enum LEVEL {

        FIRST_ENTRY,
        SECOND_ENTRY,
        CORRECTION,
        INTERNAL_DISPLAY,
        PUBLIC_DISPLAY,
        CERTIFICATION_PRINTING
    };

    public enum ACTIONS {

        WELCOME,
        SAVE_PARCEL,
        SAVE_DISPUTE,
        SAVE_HOLDER,
        COMMIT,
        SAVE_CORRECTIONS,
        SEND_FOR_CORRECTION
    };

    public enum PAGES {

        WELCOME,
        NEW_PARCEL,
        NEW_INDIVIDUAL_HOLDER,
        NEW_ORGANIZATION_HOLDER,
        NEW_DISPUTE,
        SAVE_PARCEL,
        SAVE_DISPUTE,
        SAVE_HOLDER,
        HODLER_LIST,
        DISPUTE_LIST,
        COMMIT,
        SEND_FOR_CORRECTION,
        PARCEL_CORRECTION,
        ORGANIZATION_HOLDER_CORRECTION,
        HOLDER_LIST_CORRECTION,
        INDIVIDUAL_HOLDER_CORRECTION,
        DISPUTE_CORRECTION,
    };
}
