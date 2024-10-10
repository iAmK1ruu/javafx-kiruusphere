package com.kiruu.kiruusphere;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RequestWeatherData {
    public static String lat, longt, loc_name;
    public static String[] getResponse(String urlParam) {


        String[] noResponse = {"NO_RESPONSE"};
        return noResponse;
    }

    public static String readSavedLocation() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/kiruu/kiruusphere/data/pinned.dat"));
            lat = reader.readLine();
            longt = reader.readLine();
            loc_name = reader.readLine();
            System.out.println(loc_name);
            return loc_name;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
