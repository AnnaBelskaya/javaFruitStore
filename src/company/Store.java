package company;
import goods.*;
import utils.FileManager;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static utils.FileManager.*;

public class Store {
    private Random r = new Random();
    private ArrayList<Fruit> spoiled;
    private ArrayList<Fruit> fresh;
    private List<Fruit> fruit;
    public int moneyBalance = 0;

    public Store() {
        fruit = new ArrayList<>();
    }

    public List<Fruit> getFruit() {
        return fruit;
    }

    public void addFruits() throws IOException {
        List<Fruit> newFruit = Generator.generate(r.nextInt(20) + 1);
        fruit.addAll(newFruit);
        FileManager.save(newFruit, supplytxt);
    }

    private void sortFruits(Date date) throws IOException {
        spoiled = new ArrayList<>();
        Predicate<Fruit> predicate = f->
                (Math.abs(date.getTime() - f.date.getTime())/ (1000*60*60*24)) <= f.shelfLife;
        spoiled.addAll(fruit);
        spoiled.removeIf(predicate);
        FileManager.save(spoiled, spoiledtxt);


        fresh = new ArrayList<>();
        Predicate<Fruit> predicate2 = f->
                (Math.abs(date.getTime() - f.date.getTime())/ (1000*60*60*24)) > f.shelfLife;
        fresh.addAll(fruit);
        fresh.removeIf(predicate2);
        FileManager.save(fresh, freshtxt);
    }

    public List<Fruit> getSpoiledFruits(Date date) throws IOException {
        sortFruits(date);
        return spoiled;
    }

    public List<Fruit> getAvailableFruits(Date date) throws IOException {
        sortFruits(date);
        return fresh;
    }

    public List<Fruit> getSpoiledFruits(Date date, Type type) throws IOException {
        sortFruits(date);
        spoiled.removeIf(f -> !f.type.equals(type));
        return spoiled;
    }

    public List<Fruit> getAvailableFruits(Date date, Type type) throws IOException {
        sortFruits(date);
        fresh.removeIf(f -> !f.type.equals(type));
        return fresh;
    }

    public List<Fruit> removeSpoiled() throws IOException {
        sortFruits(new Date());
        fruit.removeIf(f -> spoiled.contains(f));
        return fruit;
    }

    public void loadClients() throws IOException {
        List<Client> clients = Generator.generateClient(r.nextInt(3) + 1);
        FileManager.save(clients, clientstxt);
    }

    public String sell() throws IOException {
        String result = "";
        List<Client> clients = FileManager.loadCLient(clientstxt);
        for (int i = 0; i < clients.size(); i++){
            Type t = clients.get(i).type;
            int c = clients.get(i).count;
            Date date = new Date();
            if (getAvailableFruits(date, t).size() >= c){
                result += clients.get(i).name + " bought " + c + " " + t + " for " + deleteSold(c,t) +"$.\n";
            } else {
                result += clients.get(i).name + " didn't buy anything.\n";
            }
        }
        return result;
    }

    private int deleteSold(int size, Type type) {
        int price = 0;
        int i = -1;
        while(size >0){
            if (fruit.get(++i).type.equals(type)){
                price+=fruit.get(i).price;
                fruit.remove(i);
                --size;
            }
        }
        moneyBalance+=price;
        return price;
    }
}