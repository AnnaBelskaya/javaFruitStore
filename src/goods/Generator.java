package goods;

import company.Client;

import java.text.SimpleDateFormat;
import java.util.*;

public class Generator {
    private static Random r = new Random();
    private static String[] names = new String[]{"Harry",
            "Ron",
            "Hermione",
            "Severus",
            "Voldemort",
            "Sirius",
            "Fred"};

    private Generator() {
    }

    public static List<Fruit> generate(int count) {
        List<Fruit> fruitList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Fruit fruit = new Fruit();
            fruit.type = Type.getRandom();
            fruit.shelfLife = r.nextInt(150) + 80;
            fruit.date = generateDate();
            fruit.price = r.nextInt(10) + 3;
            fruitList.add(fruit);
        }
        return fruitList;
    }

    public static Date generateDate() {
        int month = r.nextInt(6) + 1;
        int day = r.nextInt(27) + 1;
        String dateStr = day + "/" + month + "/2017";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static List<Client> generateClient(int count) {
        List<Client> clients = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String n = names[r.nextInt(names.length - 1)];
            Type t = Type.getRandom();
            int c = r.nextInt(10) + 1;
            Client client = new Client(n,t,c);
            clients.add(client);
        }
        return clients;
    }
}