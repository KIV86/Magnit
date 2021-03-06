package javacode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionDB {
    private static Logger logger = LoggerFactory.getLogger(ConnectionDB.class.getSimpleName());
    protected static Connection conn = null;

    public static synchronized Connection connectionDB() {

        logger.debug("вызов метода connectionDB: подключение JDBC драйвера и подключения к базе данных");
        if (conn == null) {

            logger.debug("подключение JDBC драйвера");
//          url,login,password  для подключения к БД нахожятся в файле database.properties

            Properties props = new Properties();
            logger.debug("подключение к файлу database.properties");
            try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
                props.load(in);
            } catch (IOException e) {
                logger.error("не найден файл с настройками для подключения к БД \"database.properties\"", e);
                System.exit(1);
            }
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            logger.debug("Подключение к БД");
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                logger.error("Ошибка подключения к БД, проверте корректность адреса, логина и пароля ", e);
                System.exit(1);
            }
            logger.debug("Подключение к БД прошло успешно");


        }
        return conn;
    }
}