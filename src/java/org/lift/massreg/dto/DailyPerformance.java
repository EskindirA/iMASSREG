package org.lift.massreg.dto;

import java.sql.*;
import org.lift.massreg.entity.*;

/**
 * A DTO Class to hold information about the currently logged in user
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class DailyPerformance {
    String user;
    int firstEntry;
    int secondEntry;
    int supervisor;

    public int getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(int supervisor) {
        this.supervisor = supervisor;
    }
    Date date;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getFirstEntry() {
        return firstEntry;
    }

    public void setFirstEntry(int firstEntry) {
        this.firstEntry = firstEntry;
    }

    public int getSecondEntry() {
        return secondEntry;
    }

    public void setSecondEntry(int secondEntry) {
        this.secondEntry = secondEntry;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
