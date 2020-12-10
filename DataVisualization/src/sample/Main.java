package sample;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;

public class Main extends Application {
 private ObservableList<PieChart.Data> data;

    @Override
    public void start(Stage stage) throws Exception{

//        primaryStage.setTitle("Hello World");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("datavisualization.fxml"));
        Parent root = loader.load();
        Controller myController = loader.getController();
        myController.createChoiceBoxList();
        myController.createPieChart();
        myController.createLineGraph();
        myController.createBarGraph();
        myController.createAreaGraph();
        myController.insertData();
        myController.deleteData();
        myController.updateData();
        myController.showData();
        stage.setScene(new Scene(root, 880, 700));
//        primaryStage.show();


        stage.show();
        }

    public static void main(String[] args) {
        launch(args);
    }
}
