package ui;

import com.jfoenix.controls.JFXButton;
import company.*;
import goods.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import utils.FileManager;

import java.io.FileNotFoundException;
import java.util.Date;

import static utils.FileManager.database;

abstract public class MenuUI {
    private Region r1 = new Region();
    protected Pane pane;
    protected TableView table;
    protected TextArea text = new TextArea();
    protected Store currentStore;
    protected Label incomeLBL = new Label("0.0$");
    protected Company company = new Company();
    protected HBox box = new HBox();
    protected HBox qualityBox = new HBox();
    protected VBox buttonzBox = new VBox();
    protected CompanyUI companyUI;
    protected JFXButton exit, companyBTN;
    public static JFXButton[] btnz;

    public static ObservableList<Fruit> fruitList;

    public MenuUI(Pane pane) {
        this.pane = pane;
        setBackground();
        setButtons();
        setTable();
        setLabel();
        setBox();
        currentStore = company.storeList[0];
        btnz[0].getStyleClass().add("busy");
        companyUI = new CompanyUI(company, pane, table);
    }

    private void setBackground() {
        r1.setTranslateX(0);
        r1.setTranslateY(0);
        r1.setPrefSize(1000, 70);
        r1.setStyle("-fx-background-color: #4F4F4F;");

        pane.getChildren().add(r1);
    }

    private void setLabel(){
        incomeLBL.setId("incomeLBL");
        incomeLBL.setGraphic(new ImageView(new Image("file:money.png")));

        text.setMaxSize(300, 550);
        text.setEditable(false);
    }

    private void setButtons(){
        btnz = new JFXButton[3];

        for (int i = 0; i < 3; i++){
            btnz[i] = new JFXButton("Store " + (i+1));
            btnz[i].setMinWidth(150);
            btnz[i].setMinHeight(70);
            final int n = i;
            company.storeList[i] = new Store();
            btnz[i].setOnAction(event -> setAction(n));
        }

        exit = new JFXButton("",
                new ImageView(new Image("file:exit.png")));
        exit.setOnAction(actionEvent -> {
            try {
                FileManager.clear(database);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            FruitShopFx.stage.close();
        });

        exit.setMinWidth(100);
        exit.setMinHeight(70);

        companyBTN = new JFXButton("Company");
        companyBTN.setMinWidth(150);
        companyBTN.setMinHeight(70);
        companyBTN.setOnAction(actionEvent -> setCompanyBTNAction());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnz);
        hBox.getChildren().addAll(companyBTN, exit);
        hBox.setTranslateX(300);
        hBox.setMinSize(600, 100);

        pane.getChildren().addAll(incomeLBL, hBox);
    }

    private void setTable(){
        table = new TableView();
        table.setMinSize(400, 550);

        TableColumn<Fruit, Type> typeColumn = new TableColumn<>("type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Fruit, Integer> shelflifeColumn = new TableColumn<>("shelfLife");
        shelflifeColumn.setMinWidth(100);
        shelflifeColumn.setCellValueFactory(new PropertyValueFactory<>("shelfLife"));

        TableColumn<Fruit, Date> dateColumn = new TableColumn<>("date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>(("date")));

        TableColumn<Fruit, Type> priceColumn = new TableColumn<>("price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(typeColumn, shelflifeColumn, dateColumn, priceColumn);
    }

    private void setBox(){
        box.getChildren().addAll(table, text);
        box.setSpacing(20);
        box.setTranslateX(20);
        box.setTranslateY(100);
    }

    private void setAction(int i){
        setButtonzFill();
        btnz[i].getStyleClass().add("busy");
        currentStore = company.storeList[i];
        fruitList = FXCollections.observableArrayList(currentStore.getFruit());
        table.setItems(fruitList);
        text.setText("");
        incomeLBL.setText(company.storeList[i].moneyBalance + ".0$");
        if (!box.getChildren().contains(text)) {
            box.getChildren().addAll(text);
            pane.getChildren().add(buttonzBox);
        }
        if (companyUI.containsBox())
            companyUI.removeElements();
    }

    private void setCompanyBTNAction(){
        fruitList = FXCollections.observableArrayList(company.getFruitList());
        table.setItems(fruitList);
        text.setText("");
        incomeLBL.setText(company.getIncome() + ".0$");
        box.getChildren().removeAll(text);
        pane.getChildren().remove(buttonzBox);
        companyUI.addElements();
        companyUI.allGoods.isPressed();
        setButtonzFill();
        companyBTN.getStyleClass().add("busy");
    }

    private void setButtonzFill(){
        for (int i= 0; i < btnz.length; i++)
            btnz[i].getStyleClass().remove("busy");
        companyBTN.getStyleClass().remove("busy");
    }
}