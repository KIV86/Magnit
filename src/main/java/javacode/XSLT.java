package javacode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLT {
    private static Logger logger = LoggerFactory.getLogger(CreateXML.class.getSimpleName());

    static void xslt() {
        logger.debug("Приеобразование XML документа с помощью XSLT");
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("2.xsl"));
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(xslt);
        } catch (TransformerConfigurationException ex) {
           logger.error("Проблемы с файлом конфигурации XSL",ex.getMessageAndLocation());

        }


        Source xml = new StreamSource(new File("1.xml"));
        try {
            transformer.transform(xml, new StreamResult(new File("2.xml")));
        } catch (TransformerException ex) {
           logger.error( "ошибка записи в файл 2.xml",ex.getMessageAndLocation());

        }
    }
}

