package company;

import goods.Fruit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Company {
    private int totalIncome = 0;
    public Store[] storeList;
    private List<Fruit> fruit;
    private List<Fruit> spoiled = new ArrayList<>();
    private List<Fruit> fresh = new ArrayList<>();

    public Company() {
        storeList = new Store[]{
                new Store(),
                new Store(),
                new Store()};
    }

    public int getIncome(){
        totalIncome = 0;
        for (int i = 0; i < storeList.length; i++)
            totalIncome += storeList[i].moneyBalance;
        return totalIncome;
    }

    public List<Fruit> getFruitList(){
        fruit = new ArrayList<>();
        for (int i = 0; i < storeList.length; i++)
            fruit.addAll(storeList[i].getFruit());
        return fruit;
    }

    public List<Fruit> getSpoiled() throws IOException {
        spoiled = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < storeList.length; i++)
            spoiled.addAll(storeList[i].getSpoiledFruits(date));
        return spoiled;
    }

    public List<Fruit> getFresh() throws IOException {
        fresh.clear();
        Date date = new Date();
        for (int i = 0; i < storeList.length; i++)
            fresh.addAll(storeList[i].getAvailableFruits(date));
        return fresh;
    }

    public List<Fruit> removeSpoiled() throws IOException {
        spoiled.clear();
        for (int i = 0; i < storeList.length; i++)
            storeList[i].removeSpoiled();
        fruit = getFruitList();
        return fresh;
    }
}
