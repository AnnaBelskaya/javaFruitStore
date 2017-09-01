package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import company.Company;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import utils.BtnAction;

import java.io.IOException;

import static utils.FileManager.freshtxt;
import static utils.FileManager.spoiledtxt;

public class CompanyUI {
    private Pane pane;
    private Company company;
    private TableView table;
    private JFXTextField[] tf = new JFXTextField[3];
    public JFXButton allGoods, spoiled, fresh, remove;
    protected TextArea text = new TextArea();
    private Region r1 = new Region();
    private HBox box = new HBox();
    private VBox vbox = new VBox();
    private HBox tbox = new HBox();

    public CompanyUI(Company company, Pane pane, TableView table) {
        this.company = company;
        this.pane = pane;
        this.table = table;
        setElements();
        setTextFields();
    }

    private void setElements(){
        allGoods = new JFXButton("All");
        allGoods.setOnAction(action -> {
            try {
                text.setText(BtnAction.all(table, company.getFruitList()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        spoiled = new JFXButton("Spoiled");
        spoiled.setOnAction(action -> {
            try {
                text.setText(BtnAction.getList(table, company.getSpoiled(), spoiledtxt));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        fresh = new JFXButton("Fresh");
        fresh.setOnAction(action -> {
            try {
                text.setText(BtnAction.getList(table, company.getFresh(), freshtxt));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        remove = new JFXButton("Remove spoiled");
        remove.setOnAction(action -> {
            try {
                BtnAction.all(table, company.removeSpoiled());
                text.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JFXButton[] buttons = new JFXButton[]{allGoods, spoiled, fresh, remove};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].getStyleClass().add("button-company");
            buttons[i].setPrefSize(100, 40);
        }
        remove.setPrefSize(140, 40);

        r1 = new Region();
        r1.setPrefSize(500, 50);
        r1.setStyle("-fx-background-color: white; -fx-background-radius: 10 10 10 10");

        text.setEditable(false);
        text.setPrefHeight(415);

        box.getChildren().addAll(buttons);
        box.setSpacing(20);

        vbox.getChildren().addAll(box, text, r1);
        vbox.setTranslateX(470);
        vbox.setTranslateY(100);
        vbox.setSpacing(20);
    }

    public void setTextFields(){
        for(int i = 0; i < tf.length; i++){
            tf[i] = new JFXTextField();
            tf[i].setPromptText("Store " + (i+1) + " name");
            tf[i].setFocusColor(Paint.valueOf("#828282"));
            tf[i].setUnFocusColor(Paint.valueOf("#B5B5B5"));
            tf[i].setLabelFloat(true);
            JFXButton b = MenuUI.btnz[i];
            tf[i].textProperty().addListener((observable, oldValue, newValue) ->
                    b.setText(newValue));
        }

        tbox.getChildren().addAll(tf);
        tbox.setSpacing(30);
        tbox.setTranslateX(490);
        tbox.setTranslateY(610);
    }

    public void addElements(){
        if (!containsBox()) {
            pane.getChildren().addAll(vbox, tbox);
        }
    }

    public void removeElements(){
        pane.getChildren().removeAll(vbox, tbox);
    }

    public boolean containsBox(){
        return pane.getChildren().contains(vbox);
    }
}
