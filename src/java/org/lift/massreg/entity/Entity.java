package org.lift.massreg.entity;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 * 
 */
public interface Entity {

    public boolean validateForSave();
    public boolean validateForUpdate();
}
