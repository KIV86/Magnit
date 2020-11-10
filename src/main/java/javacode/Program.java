package javacode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Program {
    private static Logger logger = LoggerFactory.getLogger(Program.class.getSimpleName());

    public static void main(String[] args) {
        logger.info("Начало работы приложения, время {}", LocalDateTime.now());
        logger.debug("Измерение времени работы приложения");
        long start = System.currentTimeMillis();

        ConnectionDB.connectionDB();

        UpdateDB up = new UpdateDB();
        up.scanNum();
        up.updateDB();

        CreateXML crt = new CreateXML();
        crt.createXML();

        XSLT.xslt();

        SaxParser sp = new SaxParser();
        sp.parser();

        try {
            ConnectionDB.conn.close();
            logger.debug("Connection закрыт");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        long end = System.currentTimeMillis();
        long result = (end - start) / 1000;
        logger.info("Окончание работы приложения, в {}, время работы приложения {} секунд", LocalDateTime.now(), result);
    }

}
