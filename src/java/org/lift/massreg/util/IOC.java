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

    private static HashMap<Integer, String> pages;
    private static HashMap<Integer, String> titles;
    
    static {
        pages = new HashMap<>();
        titles = new HashMap<>();
    
        pages.put(Constants.INDEX_ERROR, "error.jsp");
        pages.put(Constants.INDEX_FIRST_ENTRY_WELCOME, "welcomefeo.jsp");

        titles.put(Constants.INDEX_ERROR, "Error");
        titles.put(Constants.INDEX_FIRST_ENTRY_WELCOME, "Welcome");
    }

    public static String getPage(int id) {
        return pages.get(id);
    }

    public static String getTitle(int id) {
        return titles.get(id);
    }
}
