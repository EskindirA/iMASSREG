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
        pages.put(Constants.INDEX_NOT_KNOWN, "notknownrequests.jsp");
        pages.put(Constants.INDEX_ADD_PARCEL_FEO, "addParcelFEO.jsp");
        pages.put(Constants.INDEX_ADD_PARCEL_SEO, "addParcelSEO.jsp");

        titles.put(Constants.INDEX_ERROR, "Error");
        titles.put(Constants.INDEX_FIRST_ENTRY_WELCOME, "Welcome");
        titles.put(Constants.INDEX_SECOND_ENTRY_WELCOME, "Welcome");
        titles.put(Constants.INDEX_SUPERVISOR_WELCOME, "Welcome");
        titles.put(Constants.INDEX_NOT_KNOWN, "Sorry, You'r request could not be recognized");
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
