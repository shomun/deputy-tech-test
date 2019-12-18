package com.monirul.deputy;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DataReader {

    private Gson gson = new Gson();

    public <T> List<T> readData(String fileName, Type typeOfT) throws Exception {
        try {
            String data =  getDataFromFile(fileName);
            T[] dataArray = gson.fromJson(data, typeOfT);
            return (List<T>) Arrays.asList(dataArray);
        } catch (IOException | URISyntaxException e) {
            throw new Exception(e);
        }


    }

    private String getDataFromFile(String fileName) throws URISyntaxException, IOException {
        URI filePath = getClass().getClassLoader().getResource(fileName).toURI();
        byte[] data =Files.readAllBytes(Paths.get(filePath));
        return new String(data);

    }
}
