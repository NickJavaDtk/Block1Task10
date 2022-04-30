package ru.netology;

import com.google.gson.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        //createCsvFile( );
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileNameCsv = "data.csv";
        File jsonFile = new File("data.json");
        File file = new File("data.xml");
        File jsonFile2 = new File("data2.json");
        List<Employee> listCSV = parseCSV(columnMapping, fileNameCsv);
        String json = listToJson(listCSV);
        writeString(json, jsonFile);
        Document.createXmlFile( );
        List<Employee> listXML = Document.parseXML(file);
        String json2 = listToJson(listXML);
        writeString(json2, jsonFile2);
        String json3 = readString("data.json");
        List<Employee> listJson = jsonToList(json3);
        for (Employee employee : listJson) {
            System.out.println(employee);
        }


    }

    /*public static void createCsvFile() {
        File file = new File("data.csv");
        String[] content = new String[]{"1,John,Smith,USA,25", "2,Inav,Petrov,RU,23"};
        for (String s : content) {
            String[] employee = s.split(",");
            try (CSVWriter csvWriter = new CSVWriter(new FileWriter(file, true))) {
                csvWriter.writeNext(employee);
            } catch (IOException e) {
                e.printStackTrace( );
            }
        }

    }*/

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>( );
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build( );
            List<Employee> list = csvToBean.parse( );
            return list;

        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static String listToJson(List<Employee> employees) {
        GsonBuilder builder = new GsonBuilder( );
        Gson gson = builder.create( );
        Type list = new TypeToken<List<Employee>>( ) {
        }.getType( );
        String json = gson.toJson(employees, list);
        return json;
    }

    public static void writeString(String json, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(json);

        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

    public static String readString(String file) {
        JSONParser jsonParser = new JSONParser( );
        try {

            Object obj = jsonParser.parse(new BufferedReader(new FileReader(file)));
            JSONArray jsonArray = (JSONArray) obj;
            String s = String.valueOf(jsonArray);
            return s;

        } catch (ParseException | IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static List<Employee> jsonToList(String jsonFile) {
        List<Employee> listEmployee = new ArrayList<>( );
        try {
            JsonParser parser = new JsonParser( );
            Gson gson = new Gson( );
            JsonArray jsonArray = (JsonArray) parser.parse(jsonFile);
            for (Object o : jsonArray) {
                listEmployee.add(gson.fromJson((JsonElement) o, Employee.class));

            }
//
        } catch (JsonIOException e) {
            e.printStackTrace( );
        }
        return listEmployee;
    }
}