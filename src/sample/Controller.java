package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane anchorRoot;
    @FXML
    Label label_on_board;
    @FXML
    Label label_in_the_air;
    @FXML
    Label label_waiting_for_landing;
    @FXML
    Label label_waiting_for_take_off;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int N = 15;
        int M = 10;
        int K = 1; //who has right
        int flights_to_fly = 2;

        Carrier carier = new Carrier(N, M, K, label_on_board, label_waiting_for_take_off, label_in_the_air, label_waiting_for_landing);
        Aircraft[] aircraft = new Aircraft[M];

        for (int i = 0; i < M; i++) aircraft[i] = new Aircraft(carier, i, flights_to_fly, anchorRoot);
        for (int i = 0; i < M; i++) aircraft[i].start();

        //carier.getIn_the_air().addListener(new ChangeListener());
        //label_in_the_air.textProperty().bind(new SimpleIntegerProperty(carier.getIn_the_air()).asString());
        //label_in_the_air.textProperty().bind(new SimpleIntegerProperty(carier.in_the_air).asString());
        //join makes all the graphic to wait till end od of "threads"

    }
}
