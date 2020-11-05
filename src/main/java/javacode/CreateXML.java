package javacode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;


public class CreateXML {
    private static Logger logger = LoggerFactory.getLogger(CreateXML.class.getSimpleName());
    private String fileName = "1.xml";
    private String query = "select FIELD from TEST";

    public String getFileName() {
        return fileName;
    }

    public String getQuery() {
        return query;
    }
    void createXML() {
// В данном методе создается  XML документ с использованием сборки для XML- "Xembly".
// ссылка на Xembly: https://www.xembly.org/
// Maven зависимость :
/*  <dependency>
  <groupId>com.jcabi.incubator</groupId>
  <artifactId>xembly</artifactId>
  <version>0.22</version>
</dependency*/
        logger.debug("Метод createXML(): создание XML документа и добавление в него значений с использованием сборки для XML- \"Xembly\"");
        logger.debug("Подключение к базе данных");
        FileWriter fw = null;

        try (var statement = ConnectionDB.conn.createStatement()) {
            logger.debug("Подключение к базе данныхм прошло успешно");
            var resultSet = statement.executeQuery(getQuery());
            // добавление элементов, атрибутов и их значений в файл
            Directives directives = new Directives().add("entries");
            while (resultSet.next()) {
                directives.add("entry").add("field").set(resultSet.getLong(1)).up().up();
            }
            try {
                fw = new FileWriter(getFileName());
                fw.write(new Xembler(directives).xml());
                fw.close();
            } catch (IOException e) {
                logger.error("Ошибка записи в файл", e);
            }
        } catch (SQLException | ImpossibleModificationException e) {
            logger.error("Ошибка, возникла при подколючении и работе с базой данных ", e);
        }
    }
}







