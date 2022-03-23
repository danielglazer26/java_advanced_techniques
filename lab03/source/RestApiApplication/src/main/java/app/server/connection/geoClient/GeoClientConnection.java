package app.server.connection.geoClient;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;

public class GeoClientConnection {

    private String countryCode;
    private int cityNumber;

    /**
     * Generates contry request
     */
    public void generateData(int offset, int population) {
        try {
            createCountryCodeJson(createHttp(createCountryHttp(String.valueOf(offset))), String.valueOf(population));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Formats country http
     */
    public String createCountryHttp(String offset) {
        return "https://wft-geo-db.p.rapidapi.com/v1/geo/countries?offset=" + offset + "&limit=1";
    }

    /**
     * Creates a http for the specified URL
     */
    private String createHttp(String geoRequestURL) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(geoRequestURL))
                .header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
                .header("x-rapidapi-key", "0c1ae71228msh41da9b4a77df447p1d67fbjsnfd9e4cefb8e5")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    /**
     * Creates json country object and generates city request
     */
    private void createCountryCodeJson(String jsonBody, String population) throws IOException, InterruptedException {
        var json = createJson(jsonBody).getJsonArray("data").getJsonObject(0);
        countryCode = json.getString("code");

        Thread.sleep(1500);
        createCityNumberJson(createHttp(createCityHttp(json.getString("wikiDataId"), population)));
    }

    /**
     * Formats city http
     */
    private String createCityHttp(String countryIds, String population) {
        return "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?countryIds=" + countryIds + "&minPopulation=" + population;
    }

    /**
     * Creates json city object
     */
    private void createCityNumberJson(String jsonBody) {
        var citiesJson = createJson(jsonBody).getJsonObject("metadata").getJsonNumber("totalCount");
        cityNumber = citiesJson.intValue();
    }

    /**
     * Creates JsonReader
     */
    private JsonObject createJson(String jsonBody) {
        JsonReaderFactory readerFactory = Json.createReaderFactory(Collections.emptyMap());
        JsonReader jsonReader = readerFactory.createReader(new ByteArrayInputStream(jsonBody.getBytes()));
        return jsonReader.readObject();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getCityNumber() {
        return cityNumber;
    }
}

