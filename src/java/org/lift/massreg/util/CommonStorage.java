package org.lift.massreg.util;

import org.lift.massreg.dto.CurrentUserDTO;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class CommonStorage {

    public static CurrentUserDTO getCurrentUser() {
        /// TODO: change this to null
        CurrentUserDTO returnValue = new CurrentUserDTO();
        
        /// TODO: initialize this to actual value 
        returnValue.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
        
        return returnValue;
    }
}
