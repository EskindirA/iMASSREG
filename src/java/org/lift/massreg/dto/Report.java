package org.lift.massreg.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class Report {

    private ReportType type;
    private Date startDate;
    private Date endDate;
    private Timestamp generatedOn;
    // Kebeles......
    private ArrayList<String> headers = new ArrayList<>();
    private ArrayList<Row> rows = new ArrayList<>();

    public class Row {

        private String rowTitle;
        private HashMap<String, String> cells = new HashMap<>();
    }

    enum ReportType {

        TIMEBOUND, KEBLE
    }
}
