package checksites;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import org.apache.log4j.Logger;
import java.util.regex.*;

public class CheckSiteThread extends Thread {
    
    protected static final Logger log = Logger.getRootLogger();
    private Site site;
    private String mailSubject;
    private String mailText;
    private boolean bStatus = true;
    
    public CheckSiteThread(Site site) {
        this.site = site;
    }
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        do {
            log.info("Очередная проверка сайта: " + site.getUrlSites());
            
            if (!checkSite()) {
                log.warn(mailText + ". В потоке:" + Thread.currentThread().getName());
                if (bStatus) {
                    EMailSender.sendMail(mailSubject, mailText, site.getEmailList());
                    bStatus = false;
                }
            } else {
                log.info("С сайтом " + site.getUrlSites() + " всё ок." + " В потоке:" + Thread.currentThread().getName());
                bStatus = true;
            }
            
            try {
                Thread.currentThread().sleep(convertSecToMilliseconds(site.getPeriodCheck()));
            } catch (InterruptedException e) {
                log.error("CheckSiteThread run()", e);
            }
        } while (true);
    }
    
    public boolean checkSite() {
        mailSubject = ("Сайт " + site.getDescription() + " упал!");
        mailText = ("Сайт " + site.getUrlSites() + " упал: ");
        String answerBody = getAnswerBody();
        
        if (answerBody.isEmpty()) {
            return false;
        } else {
            for (Patterns p : site.getPatternList()) {
                if ("string".equals(p.getType())) {
                    if (answerBody.contains(p.getValue())) {
                        mailText += p.getValue();
                        return false;
                    }
                } else if ("regular".equals(p.getType())) {
                    Pattern patt = Pattern.compile(p.getValue());
                    if (patt.matcher(answerBody).find()) {
                        mailText += ("\n" + answerBody);
                        return false;
                    }
                } else {
                    log.warn("Обнаружен неизвестный тип проверки \"" + p.getType() + "\". Сайт " + site.getUrlSites() + ". В потоке:" + Thread.currentThread().getName());
                }
            }
        }
        
        return true;
    }
    
    private String getAnswerBody() {
        StringBuilder answerBody = new StringBuilder();
        BufferedReader br = null;
        try {
            URL bhv = new URL(site.getUrlSites());
            br = new BufferedReader(new InputStreamReader(bhv.openStream()));
            String line;
            
            while ((line = br.readLine()) != null) {
                answerBody.append(line).append("\n");
            }
            
            if (answerBody.toString().isEmpty()) {
                mailText += "Пустой ответ от сайта";
            }
            
        } catch (MalformedURLException me) {
            mailText += me;
        } catch (IOException ioe) {
            mailText += ioe;
        } catch (Exception e) {
            mailText += e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    log.error("CheckSiteThread getAnswerBody IOException: ", ex);
                }
            }
        }
        
        return answerBody.toString();
    }
    
    public static long convertSecToMilliseconds(int sec) {
        return 1000L * sec;
    }
}
