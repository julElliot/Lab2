package by.bsuir.parser;

import by.bsuir.bean.Client;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientXmlParser implements XmlParser<Client> {
    private static final Logger logger = Logger.getLogger(ClientXmlParser.class);
    private static final String CLIENT = "client";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String DISCOUNT = "discount";

    private DocumentBuilder documentBuilder;
    private String sourceFilePath;
    private String xsdFilePath;

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public String getXsdFilePath() {
        return xsdFilePath;
    }

    public ClientXmlParser() {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.info(e.getMessage());
        }
    }

    public ClientXmlParser(String sourceFilePath, String xsdFilePath) {
        this();
        this.sourceFilePath = sourceFilePath;
        this.xsdFilePath = xsdFilePath;
    }

    @Override
    public List<Client> getData() {
        var sourceFile = new File(sourceFilePath);
        var xsdFile = new File(xsdFilePath);

        if (!checkFiles(sourceFile, xsdFile)) {
            return null;
        }

        List<Client> clients = new ArrayList<>();
        Document document;

        try {
            document = documentBuilder.parse(sourceFile);
        } catch (SAXException | IOException e) {
            logger.info(e.getMessage());

            return null;
        }

        var element = document.getDocumentElement();
        var nodeClients = element.getElementsByTagName(CLIENT);

        for (var i = 0; i < nodeClients.getLength(); i++) {
            if (nodeClients.item(i).getNodeType() == Node.ELEMENT_NODE) {
                clients.add(getClientElement((Element) nodeClients.item(i)));
            }
        }

        return clients;
    }

    private Client getClientElement(Element element) {
        Client client;

        try {
            client = new Client(Integer.parseInt(getElementTextContent(element, ID)));
        } catch (NumberFormatException ex) {
            logger.info("'" + ID + "'" + "incorrect");
            return null;
        }

        try {
            client.setDiscount(Integer.parseInt(getElementTextContent(element, DISCOUNT)));
        } catch (NumberFormatException ex) {
            logger.info("'" + DISCOUNT + "'" + "incorrect");
            return null;
        }

        client.setLogin(getElementTextContent(element, LOGIN));
        client.setPass(getElementTextContent(element, PASSWORD));
        client.setName(getElementTextContent(element, NAME));
        client.setSurname(getElementTextContent(element, SURNAME));

        return client;
    }

    private boolean checkFiles(File sourceFile, File xsdFile) {
        if (!sourceFile.exists()) {
            logger.info(sourceFilePath + ": file not exists.");
        }

        if (!xsdFile.exists()) {
            logger.info(xsdFilePath + ": file not exists.");
        }

        return correctXMLByXSD(sourceFile, xsdFile);
    }

    private boolean correctXMLByXSD(File xml, File xsd) {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(xsd)
                    .newValidator()
                    .validate(new StreamSource(xml));
        } catch (SAXException | IOException e) {
            logger.info(e.getMessage());
            return false;
        }

        return true;
    }

    private static String getElementTextContent(Element element, String elementName) {
        var nList = element.getElementsByTagName(elementName);
        var node = nList.item(0);

        return node.getTextContent();
    }
}
