package checksites;

import java.util.ArrayList;

/**
 * @author anosv
 */
public class Site {

    private String urlSites;
    private int periodCheck;
    private ArrayList<Patterns> patternList;
    private String[] emailList;
    private String description;

    public Site(String urlSites, int periodCheck, ArrayList<Patterns> patternList, String[] emailList) {
        this.urlSites = urlSites;
        this.periodCheck = periodCheck;
        this.patternList = patternList;
        this.emailList = emailList;
    }

    public Site(String urlSites, int periodCheck, ArrayList<Patterns> patternList, String[] emailList, String description) {
        this.urlSites = urlSites;
        this.periodCheck = periodCheck;
        this.patternList = patternList;
        this.emailList = emailList;
        this.description = description;
    }

    public String getUrlSites() {
        return urlSites;
    }

    public void setUrlSites(String urlSites) {
        this.urlSites = urlSites;
    }

    public int getPeriodCheck() {
        return periodCheck;
    }

    public void setPeriodCheck(int periodCheck) {
        this.periodCheck = periodCheck;
    }

    public ArrayList<Patterns> getPatternList() {
        return patternList;
    }

    public void setPatternList(ArrayList<Patterns> patternList) {
        this.patternList = patternList;
    }

    public String[] getEmailList() {
        return emailList;
    }

    public void setEmailList(String[] emailList) {
        this.emailList = emailList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Site{" + "urlSites=" + urlSites + ", periodCheck=" + periodCheck + ", patternList=" + patternList + ", emailList=" + emailList + ", description=" + description + '}';
    }
}

class Patterns {

    private String type;
    private String value;

    public Patterns(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pattern{" + "type=" + type + ", value=" + value + "}";
    }
}
