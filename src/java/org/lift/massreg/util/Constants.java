package org.lift.massreg.util;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public interface Constants {

    public static final String ACTION_WELCOME = "WELCOME";
    public static final String ACTION_FIND_PARCEL = "FIND_PARCEL";
    public static final String ACTION_ERROR = "ERROR";
    // pages
    public static final int INDEX_ERROR = 0;
    public static final int INDEX_FIRST_ENTRY_WELCOME = 1;
    public enum ROLE {

        FIRST_ENTRY_OPERATOR,
        SECOND_ENTRY_OPERATOR,
        SUPERVISOR
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
