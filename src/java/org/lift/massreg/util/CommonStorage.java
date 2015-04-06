package org.lift.massreg.util;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Properties;
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
    private static String woredaName = null;
    private static String woredaIDForUPI = null;
    private static long woredaId = -1;
    private static String language = null;

    private static Properties languageBundle = new Properties();
    private static String languageFile = "en_us.properties";
    private static final String settingsFile = "settings.properties";

    private static String keystoreFilePath = null;

    private static String GISDBHost = null;
    private static String GISDBName = null;
    private static String GISDBUserName = null;
    private static String GISDBPassword = null;

    private static final char keyStorePassword[] = "p#$$w0rd".toCharArray();

    public static long getCurrentWoredaId() {
        if (woredaId < 0) {
            readSettings();
        }
        return woredaId;
    }

    static {
        readSettings();
    }

    public static String getCurrentWoredaIdForUPI() {
        if (woredaIDForUPI == null) {
            readSettings();
        }
        return woredaIDForUPI;
    }

    public static String getCurrentWoredaName() {
        if (woredaName == null) {
            readSettings();
        }
        return woredaName;
    }

    static {
        readSettings();
    }

    private static void loadTextValues() {
        try {
            switch (getCurrentLanguage()) {
                case "tigrigna":
                    languageFile = "ti_et.properties";
                    break;
                case "oromiffa":
                    languageFile = "om_et.properties";
                    break;
                case "amharic":
                    languageFile = "am_et.properties";
                    break;
                default:
                    languageFile = "en_us.properties";
                    break;
            }
            languageBundle.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(languageFile), "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());

        }
    }

    private static void readSettings() {
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(settingsFile));
            woredaId = Long.parseLong(prop.getProperty("woredaId"));
            woredaName = prop.getProperty("woredaName").trim();
            woredaIDForUPI = prop.getProperty("woredaIDForUPI");
            language = prop.getProperty("language");
            loadTextValues();
        } catch (Exception ex) {
            ex.printStackTrace(getLogger().getErrorStream());
        }
        try {
            InitialContext ctx = new InitialContext();
            GISDBHost = (String) ctx.lookup("resource/GISDBHost");
            GISDBName = (String) ctx.lookup("resource/GISDBName");
            GISDBUserName = (String) ctx.lookup("resource/GISDBUserName");
            GISDBPassword = (String) ctx.lookup("resource/GISDBPassword");
            keystoreFilePath = (String) ctx.lookup("resource/keystoreFilePath");

        } catch (NamingException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
    }

    public static String getKeystoreFilePath() {
        return keystoreFilePath;
    }

    public static String getText(String key) {
        return languageBundle.getProperty(key, "XXXXXXXXXXXXXXXXXXX");
    }

    public static String getLastReportDate() {
        String returnValue = "";
        ///TODO
        return returnValue;
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(java.util.Date.from(Instant.now()));
    }

    public static char[] getKeyStorePassword() {
        return keyStorePassword;
    }

    public static void setWoreda(long newWoredaId, String newWoredaName, String newWoredaIdForUPI) {
        // Save to Properties (Woreda, langiage)
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(settingsFile));
            prop.setProperty("woredaId", newWoredaId + "");
            prop.setProperty("woredaName", newWoredaName);
            prop.setProperty("woredaIDForUPI", newWoredaIdForUPI);
            URL url = Thread.currentThread().getContextClassLoader().getResource(settingsFile);
            prop.store(new FileOutputStream(new File(url.toURI())), null);
            woredaId = newWoredaId;
            woredaName = newWoredaName;
            woredaIDForUPI = newWoredaIdForUPI;
        } catch (Exception ex) {
            ex.printStackTrace(getLogger().getErrorStream());
        }
    }

    public static void setLanguage(String newLanguage) {
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(settingsFile));
            prop.setProperty("language", newLanguage);
            URL url = Thread.currentThread().getContextClassLoader().getResource(settingsFile);
            prop.store(new FileOutputStream(new File(url.toURI())), null);
            language = newLanguage;
            loadTextValues();
        } catch (Exception ex) {
            ex.printStackTrace(getLogger().getErrorStream());
        }
    }

    public static int[] getTeamNumbers() {
        int[] returnValue = new int[20];
        for (int i = 0; i < 21; i++) {
            returnValue[i] = i + 1;
        }
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
        return language;
    }

    public static String getGISDBName() {
        if (GISDBName == null) {
            readSettings();
        }
        return GISDBName;
    }

    public static String getGISDBHost() {
        if (GISDBHost == null) {
            readSettings();
        }
        return GISDBHost;
    }

    public static String getGISDBUserName() {
        if (GISDBUserName == null) {
            readSettings();
        }
        return GISDBUserName;
    }

    public static String getGISDBPassword() {
        if (GISDBPassword == null) {
            readSettings();
        }
        return GISDBPassword;
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
                            ex.printStackTrace(getLogger().getErrorStream());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(getLogger().getErrorStream());
        }
        return connection;
    }
}
