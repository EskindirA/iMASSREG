package org.lift.massreg.util;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import javax.servlet.http.HttpServletRequest;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class CommonStorage {

    private static Connection connection = null;
    private static Logger logger = new Logger();

    public static CurrentUserDTO getCurrentUser(HttpServletRequest request) {
        CurrentUserDTO returnValue = MasterRepository.getInstance().getCurrentUserDTO(request.getRemoteUser());
        return returnValue;
    }

    public static Logger getLogger() {
        return logger;
    }

    static {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("jdbc/newmassreg");
            connection = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            getLogger().logError(ex.toString());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
