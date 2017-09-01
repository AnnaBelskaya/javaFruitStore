package utils;

import company.Client;
import goods.Fruit;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class FileManager {
    public static String database = "files/database.txt";
    public static String clientstxt = "files/clients.txt";
    public static String freshtxt = "files/fresh.txt";
    public static String spoiledtxt = "files/spoiled.txt";
    public static String supplytxt = "files/supply.txt";
    private static ObjectMapper mapper;
    private FileManager() { }

    private static void mapperConfigure(){
        mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        mapper.setDateFormat(df);
    }
    public static void save(List list, String file) throws IOException {
        mapperConfigure();
        String jString = getString(list);
        FileWriter writer = new FileWriter(file);
        writer.write(jString + "\n");
        writer.flush();
        writer.close();
    }

    public static List<Fruit> load(String file) throws IOException {
        return mapper.readValue(new File(file), List.class);
    }

    public static List<Client> loadCLient(String file) throws IOException {
        return mapper.readValue(new File(file), new TypeReference<List<Client>>(){});
    }

    public static void clear(String file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

    public static String getString(List list) throws IOException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
    }
}
