package utils;

import company.Store;
import goods.Fruit;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.util.List;
import static ui.StoreUI.fruitList;
import static utils.FileManager.*;

public class BtnAction {
    private BtnAction(){}

    public static String newSupply(TableView table, Store store) throws IOException {
        store.addFruits();
        fruitList = FXCollections.observableArrayList(store.getFruit());
        table.setItems(fruitList);
        FileManager.save(fruitList, database);
        return FileManager.getString(FileManager.load(supplytxt));
    }

    public static String getList(TableView table, List<Fruit> list, String filename) throws IOException {
        fruitList = FXCollections.observableArrayList(list);
        table.setItems(fruitList);
        FileManager.save(fruitList, filename);
        String json =  FileManager.getString(FileManager.load(filename));
        if (json.equals("[ ]"))
            return "";
        else
            return json;
    }

    public static String all(TableView table, List<Fruit> list) throws IOException {
        fruitList = FXCollections.observableArrayList(list);
        table.setItems(fruitList);
        FileManager.save(list,database);
        String json =  FileManager.getString(FileManager.load(database));
        if (json.equals("[ ]"))
            return "";
        else
            return json;
    }

    public static void sale(TextArea textArea, Store store) throws IOException {
        store.loadClients();
        try {
            textArea.setText(FileManager.getString(FileManager.load(clientstxt)));
        } catch (NullPointerException e) { }
    }

    public static void sell(TableView table, TextArea textArea,
                            Store store) throws IOException {
        textArea.setText(store.sell());
        all(table,store.getFruit());
    }
}