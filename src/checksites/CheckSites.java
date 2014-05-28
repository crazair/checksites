package checksites;

import org.apache.log4j.Logger;

/**
 *
 * @author anosv
 */
public class CheckSites {

    protected static final Logger log = Logger.getRootLogger();
    public static XmlSites sites;

    public static void main(String[] args) {
        try {
            final String XMLPATH = args[0]; //Путь к фалу настроек
            sites = new XmlSites(XMLPATH);

            log.info("Проверка сайтов. Всего сайтов: " + sites.getSites().size());

            for (Site site : sites.getSites()) {
                log.info("Запуск потока проверки сайта " + site);
                CheckSiteThread checkSiteThread = new CheckSiteThread(site);
                checkSiteThread.start();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Необходимо указать файл настроек приложения! ");
        }
    }
}
