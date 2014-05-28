package checksites;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * @author anosv
 */
public class XmlSites {

    protected static final Logger log = Logger.getRootLogger();
    private static String hostMail;
    private static String nameSender;
    private ArrayList<Site> sites;

    public XmlSites(String xml) {
        parseXmlSites(xml);
    }

    private void parseXmlSites(String xml) {
        log.info("Парсинг xml файла настроек...");
        try {
            File fXmlFile = new File(xml);
            log.info("Расположение файла: " + fXmlFile.getAbsolutePath());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element eMailSettings = (Element) doc.getElementsByTagName("mailsettings").item(0);
            hostMail = getTagValue("host", eMailSettings);
            nameSender = getTagValue("sendername", eMailSettings);
            log.info("Имя хоста eMail сервера - " + hostMail + ". Имя отправителя сообщений - " + nameSender);

            ArrayList<Site> sitesList = new ArrayList<Site>();
            ArrayList<Patterns> patternList;

            log.info("Получение данных по тегам sites...");
            NodeList nList = doc.getElementsByTagName("site");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String urlSites = getTagValue("url", eElement);
                    String description = getTagValue("description", eElement);
                    int periodCheck = Integer.parseInt(getTagValue("timecheck", eElement));
                    patternList = fillingPatternList("pattern", eElement);
                    String[] emailList = getTagValues("mail", eElement);
                    

                    sitesList.add(new Site(urlSites, periodCheck, patternList, emailList, description));
                }
            }

            sites = sitesList;

        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) {
            log.error("Ошибка при чтении xml файла настроек. ", e);
        }
    }

    private ArrayList<Patterns> fillingPatternList(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag);
        ArrayList<Patterns> tempStorage = new ArrayList<Patterns>();
        for (int i = 0; i < nlList.getLength(); i++) {
            String setType = eElement.getElementsByTagName(sTag).item(i).getAttributes().item(0).getNodeValue();
            String setValue = eElement.getElementsByTagName(sTag).item(i).getChildNodes().item(0).getNodeValue();
            tempStorage.add(new Patterns(setType, setValue));
        }
        return tempStorage;
    }

    private String[] getTagValues(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag);
        String[] tempStorage = new String[nlList.getLength()];
        for (int i = 0; i < nlList.getLength(); i++) {
            Node nValue = (Node) eElement.getElementsByTagName(sTag).item(i).getChildNodes().item(0);
            tempStorage[i] = nValue.getNodeValue();
        }
        return tempStorage;
    }

    private String getTagValue(String sTag, Element eElement) {
        Node nValue = (Node) eElement.getElementsByTagName(sTag).item(0).getChildNodes().item(0);
        return nValue.getNodeValue();
    }

    public static String getHostMail() {
        return hostMail;
    }

    public static String getNameSender() {
        return nameSender;
    }

    public ArrayList<Site> getSites() {
        return sites;
    }

    public void setSites(ArrayList<Site> sites) {
        this.sites = sites;
    }
}
