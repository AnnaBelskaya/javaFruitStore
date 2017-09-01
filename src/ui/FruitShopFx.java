package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FruitShopFx extends Application {
    public static Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: transparent;");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        scene.setFill(Color.web("#CFCFCF"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setIconified(false);
        stage.show();
        new StoreUI(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
