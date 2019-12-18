package com.monirul.deputy;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        try {
            Path filePath = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
            byte[] data = Files.readAllBytes(filePath);
            return new String(data);
        }catch (Exception e){
            System.out.println("Reading from classpath : " + fileName);
            InputStream dataStream = getClass().getClassLoader().getResourceAsStream(fileName);
            return readFromInputStream(dataStream);

        }



    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
