package org.lift.massreg.dto;

/**
 *
 * @author Yoseph
 */
public class Change {

    private final String oldValue;
    private final String newValue;
    private final String field;

    public Change(String field,String oldValue, String newValue) {
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }
    public String getField() {
        return field;
    }
}
