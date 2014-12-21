package org.lift.massreg.util;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public interface Constants {

    public static final String[] STATUS = {"active","removed","incomplete"};
    public static final String ACTION_WELCOME = "WELCOME";
    public static final String ACTION_WELCOME_PART = "WELCOME_PART";
    public static final String ACTION_FIND_PARCEL_FEO = "FIND_PARCEL_FEO";
    public static final String ACTION_FIND_PARCEL_SEO = "FIND_PARCEL_SEO";
    public static final String ACTION_ERROR = "ERROR";
    public static final String ACTION_LOGOUT = "LOGOUT";
    public static final String ACTION_ADD_PARCEL_FEO = "ACTION_ADD_PARCEL_FEO";
    public static final String ACTION_ADD_PARCEL_SEO = "ACTION_ADD_PARCEL_SEO";
    public static final String ACTION_SAVE_PARCEL_FEO = "ACTION_SAVE_PARCEL_FEO";
    public static final String ACTION_SAVE_PARCEL_SEO = "ACTION_SAVE_PARCEL_SEO";
    public static final String ACTION_VIEW_PARCEL_FEO = "ACTION_VIEW_PARCEL_FEO";
    public static final String ACTION_VIEW_PARCEL_SEO = "ACTION_VIEW_PARCEL_SEO";
    public static final String ACTION_VIEW_HOLDER_FEO = "ACTION_VIEW_HOLDER_FEO";
    public static final String ACTION_VIEW_HOLDER_SEO = "ACTION_VIEW_HOLDER_SEO";
    public static final String ACTION_EDIT_PARCEL_FEO = "ACTION_EDIT_PARCEL_FEO";
    public static final String ACTION_EDIT_PARCEL_SEO = "ACTION_EDIT_PARCEL_SEO";
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
