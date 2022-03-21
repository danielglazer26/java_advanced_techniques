package app.server.connection.geoClient;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeoClientConnection {

    private final String geoURL = "https://wft-geo-db.p.rapidapi.com/v1/geo/countries/";

    GeoClientConnection() {
        try {
            createCountryJson(createHttp(configureRequest()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GeoClientConnection();
    }

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

    private String configureRequest() {
        Scanner scanner = new Scanner(System.in);
        return geoURL + scanner.nextLine();
    }

    private void createCountryJson(String jsonBody) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Country> list = new ArrayList<>();

        JsonReaderFactory readerFactory = Json.createReaderFactory(Collections.emptyMap());
        JsonReader jsonReader = readerFactory.createReader(new ByteArrayInputStream(jsonBody.getBytes()));
        var json = jsonReader.readObject().getJsonObject("data");

        Country country = null;
        if (json != null) {
            country = objectMapper.readValue(json.toString(), Country.class);
            System.out.println(country.toString());
        }
        createCountryJson(createHttp(configureRequest()));

    }


}

