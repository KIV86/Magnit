package javacode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxParser {
    private static Logger logger = LoggerFactory.getLogger(CreateXML.class.getSimpleName());
    private  String fileName = "2.xml";
    private  long counter = 0;
    private  String entryName = "entry";
    private  String fieldName = "field";

    public String getFileName() {
        return fileName;
    }

    public String getEntryName() {
        return entryName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getCounter() {
        return counter;
    }

     void parser() {
        logger.debug("Начало парсинга XML документа");
        var defaultHandler = new DefaultHandler() {

            @Override
            //расчет суммы всех значений от 1...n согласно условию.
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equals(getEntryName())) {
                    counter = getCounter() + Integer.parseInt(attributes.getValue(getFieldName()));
                }
            }
        };
        // Парсинг документа 2.xml
        var saxParserFactory = SAXParserFactory.newInstance();
        try {
            var parser = saxParserFactory.newSAXParser();
            parser.parse(getFileName(), defaultHandler);
        } catch (
                ParserConfigurationException | IOException | SAXException e) {
            logger.error("Возника ошибка во время работы SAXParser: ", e);
        }
        logger.debug("Парсинг документа успешно завершен");
        System.out.println("Парсинг XML документа окончен сумма всех значений равна: " + counter);
    }
}
