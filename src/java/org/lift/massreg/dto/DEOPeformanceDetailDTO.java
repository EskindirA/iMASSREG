package org.lift.massreg.dto;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class DEOPeformanceDetailDTO {

    private String name;
    private long firstEntry;
    private long secondEntry;
    private long correction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFirstEntry() {
        return firstEntry;
    }

    public void setFirstEntry(long firstEntry) {
        this.firstEntry = firstEntry;
    }

    public long getSecondEntry() {
        return secondEntry;
    }

    public void setSecondEntry(long secondEntry) {
        this.secondEntry = secondEntry;
    }

    public long getCorrection() {
        return correction;
    }

    public void setCorrection(long correction) {
        this.correction = correction;
    }

    public long getTotal() {
        return getFirstEntry() + getSecondEntry() + getCorrection();
    }
}
