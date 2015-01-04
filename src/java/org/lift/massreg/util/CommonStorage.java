package org.lift.massreg.util;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import javax.servlet.http.HttpServletRequest;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.*;
import org.lift.massreg.entity.User;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class CommonStorage {

    private static Connection connection = null;
    private static Logger logger = new Logger();

    public static int getCurrentWoredaId() {
        int returnValue = 4;
        ///TODO: set woreda id from glassfish
        return returnValue;
    }

    public static String getCurrentWoredaIdForUPI() {
        String returnValue = "1/2/3/4";
        ///TODO: set woreda id from glassfish
        return returnValue;
    }

    public static String getCurrentWoredaName() {
        String returnValue = "Meskan";
        ///TODO: set woreda Name from glassfish
        return returnValue;
    }

    public static CurrentUserDTO getCurrentUser(HttpServletRequest request) {
        CurrentUserDTO returnValue = MasterRepository.getInstance().getCurrentUserDTO(request.getRemoteUser());
        return returnValue;
    }

    public static User getCurrentUserDetails(HttpServletRequest request) {
        CurrentUserDTO userDTO = getCurrentUser(request);
        User returnValue = MasterRepository.getInstance().getUser(userDTO.getUserId());
        return returnValue;
    }

    public static String getCurrentLanguage() {
        ///TODO
        return "textValue";
    }

    public static byte getFEStage() {
        return 1;
    }

    public static byte getSEStage() {
        return 2;
    }

    public static byte getCorrectionStage() {
        return 3;
    }

    public static byte getCommitedStage() {
        return 4;
    }

    public static Logger getLogger() {
        return logger;
    }

    public synchronized static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (Connection.class) {
                    if (connection == null || connection.isClosed()) {
                        try {
                            DataSource ds = (DataSource) new InitialContext().lookup("jdbc/newmassreg");
                            connection = ds.getConnection();
                        } catch (NamingException | SQLException ex) {
                            getLogger().logError(ex.toString());
                        }
                    }
                }
            }
        }catch(Exception e){
            getLogger().logError(e.toString());
        }
        return connection;
    }
}
