package org.lift.massreg.dao;

import org.lift.massreg.dto.ParcelUpdateDTO;
import org.lift.massreg.entity.Parcel;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class MasterRepository {

    private static final MasterRepository instance = new MasterRepository();

    private MasterRepository() {
    }

    public static MasterRepository getInstance() {
        return instance;
    }

    public boolean save(Parcel parcel) {
        boolean returnValue = false;
        
        /// TODO
        return returnValue;
    }
    
    public Parcel get(ParcelUpdateDTO parcelUpdateDTO) {
        Parcel returnValue = null;
        
        /// TODO
        return returnValue;
    }
}
