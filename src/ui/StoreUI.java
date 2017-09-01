package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import goods.Type;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import utils.BtnAction;
import utils.DatePickerUI;
import java.io.IOException;

import static utils.FileManager.*;

public class StoreUI extends MenuUI {
    private JFXButton allGoods, sellBTN, loadSupply, loadSaleBTN,
            showSpoiled,showFresh, deleteSpoiled;
    private DatePickerUI datePicker;
    private JFXComboBox<Type> comboBox = new JFXComboBox<>();

    public StoreUI(Pane pane) {
        super(pane);
        datePicker = new DatePickerUI();
        setButtons();
        setElements();
    }

    private void setButtons() {
        allGoods = new JFXButton("All");
        allGoods.getStyleClass().add("button-company");
        allGoods.setPrefSize(90, 35);
        allGoods.setOnAction(actionEvent -> {
            try {
                text.setText(BtnAction.all(table, currentStore.getFruit()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //----------------------------------------------------------------------

        loadSupply = new JFXButton("New supply");
        loadSupply.setPrefSize(90, 35);
        loadSupply.getStyleClass().add("button-company");
        loadSupply.setOnAction(actionEvent -> {
            try {
                text.setText(BtnAction.newSupply(table, currentStore));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //----------------------------------------------------------------------

        showSpoiled = new JFXButton("All spoiled");
        showSpoiled.getStyleClass().add("button-company");
        showSpoiled.setPrefSize(90, 35);
        showSpoiled.setOnAction(actionEvent -> {
            comboBox.getSelectionModel().select(-1);
            try {
                text.setText(BtnAction.getList(table, currentStore.getSpoiledFruits(datePicker.getDate()),
                            spoiledtxt));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //----------------------------------------------------------------------

        showFresh = new JFXButton("All fresh");
        showFresh.getStyleClass().add("button-company");
        showFresh.setPrefSize(90, 35);
        showFresh.setOnAction(actionEvent -> {
            comboBox.getSelectionModel().select(-1);
            try {
                text.setText(BtnAction.getList(table, currentStore.getAvailableFruits(datePicker.getDate()),
                            freshtxt));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //----------------------------------------------------------------------

        deleteSpoiled = new JFXButton("Delete all spoiled");
        deleteSpoiled.getStyleClass().add("button-company");
        deleteSpoiled.setPrefSize(190, 35);
        deleteSpoiled.setOnAction(actionEvent -> {
            try {
                BtnAction.all(table,currentStore.removeSpoiled());
                text.setText("Spoiled goods were removed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //----------------------------------------------------------------------

        loadSaleBTN = new JFXButton("New sale");
        loadSaleBTN.getStyleClass().add("button-company");
        loadSaleBTN.setPrefSize(90, 35);
        loadSaleBTN.setOnAction(actionEvent -> {
            try {
                BtnAction.sale(text, currentStore);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sellBTN.setDisable(false);
        });

        //----------------------------------------------------------------------

        sellBTN = new JFXButton("Sell");
        sellBTN.getStyleClass().add("button-company");
        sellBTN.setPrefSize(90, 35);
        sellBTN.setDisable(true);
        sellBTN.setOnAction(actionEvent -> {
            try {
                BtnAction.sell(table, text, currentStore);
            } catch (IOException e) {
                e.printStackTrace();
            }
            incomeLBL.setText(currentStore.moneyBalance + "$");
            sellBTN.setDisable(true);
            loadSaleBTN.requestFocus();
        });
    }

    private void setElements(){
        HBox box1 = new HBox(allGoods, loadSupply);
        box1.setSpacing(10);

        Label qualityLabel = new Label("QUALITY");
        qualityLabel.setTextFill(Paint.valueOf("#4F4F4F"));
        qualityLabel.setPrefWidth(190);
        qualityLabel.setAlignment(Pos.CENTER);

        HBox box4 = new HBox(showSpoiled, showFresh);
        box4.setSpacing(10);

        Label dateLBL = new Label("Choose date: ");
        dateLBL.setTextFill(Paint.valueOf("#4F4F4F"));
        dateLBL.setTranslateY(12);

        Label typeLBL = new Label("Choose type: ");
        typeLBL.setTextFill(Paint.valueOf("#4F4F4F"));
        typeLBL.setTranslateY(12);

        comboBox.setPrefWidth(100);
        comboBox.getItems().setAll(Type.values());
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                text.setText(BtnAction.getList(table, currentStore.getSpoiledFruits(datePicker.getDate(),
                        comboBox.getValue()), spoiledtxt));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HBox box3 = new HBox(dateLBL, datePicker.getPicker());
        box3.setSpacing(15);

        HBox box2 = new HBox(typeLBL, comboBox);
        box2.setSpacing(15);

        VBox qualityVBox = new VBox(box2, box3, box4, deleteSpoiled);
        qualityVBox.setSpacing(15);

        //----------------------------------------------------------------------

        Label salesLabel = new Label("SALES");
        salesLabel.setTextFill(Paint.valueOf("#4F4F4F"));
        salesLabel.setPrefWidth(190);
        salesLabel.setAlignment(Pos.CENTER);

        HBox box5 = new HBox(loadSaleBTN, sellBTN);
        box5.setSpacing(10);

        //----------------------------------------------------------------------

        buttonzBox.getChildren().addAll(box1, qualityLabel, qualityVBox,
                salesLabel, box5);
        buttonzBox.setSpacing(20);
        buttonzBox.setTranslateX(780);
        buttonzBox.setTranslateY(100);

        pane.getChildren().addAll(box, buttonzBox);
    }
}