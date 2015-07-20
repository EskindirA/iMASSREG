package org.lift.massreg.dto;

import org.lift.massreg.util.CommonStorage;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0.6
 * @since 2.0.6
 *
 */
public class HoldingHolderDTO {

    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexText() {
        return sex == null ? "" : (sex.equalsIgnoreCase("f")?CommonStorage.getText("female"):CommonStorage.getText("male"));
    }

}
