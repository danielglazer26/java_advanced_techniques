package app.server.connection.geoClient;

import java.util.ArrayList;

class Country {
    private String capital;
    private String code;
    private String callingCode;
    private ArrayList<String> currencyCodes;
    private String flagImageUri;
    private String name;
    private int numRegions;
    private String wikiDataId;

    @Override
    public String toString() {
        return "Country{" +
                "capital='" + capital + '\'' +
                ", code='" + code + '\'' +
                ", callingCode='" + callingCode + '\'' +
                ", currencyCodes=" + currencyCodes +
                ", flagImageUri='" + flagImageUri + '\'' +
                ", name='" + name + '\'' +
                ", numRegions=" + numRegions +
                ", wikiDataId='" + wikiDataId + '\'' +
                '}';
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public ArrayList<String> getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCurrencyCodes(ArrayList<String> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }

    public String getFlagImageUri() {
        return flagImageUri;
    }

    public void setFlagImageUri(String flagImageUri) {
        this.flagImageUri = flagImageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumRegions() {
        return numRegions;
    }

    public void setNumRegions(int numRegions) {
        this.numRegions = numRegions;
    }

    public String getWikiDataId() {
        return wikiDataId;
    }

    public void setWikiDataId(String wikiDataId) {
        this.wikiDataId = wikiDataId;
    }
}


