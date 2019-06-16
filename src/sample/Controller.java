package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    Label label_on_board;
    @FXML
    Label label_in_the_air;
    @FXML
    Label label_waiting_for_landing;
    @FXML
    Label label_waiting_for_take_off;
    @FXML
    Button Start;
    @FXML
    Slider NumOfPlanes;
    @FXML
    Slider Speed;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    CheckBox LandingFirst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Start.setOnAction((event) -> {
            int N = 15;
            int M = (int) NumOfPlanes.getValue();
            int flights_to_fly = 15;
            int time = 10000 / (int) (Speed.getValue());
            int K;
            if (LandingFirst.isSelected()) {
                K = 1;
                System.out.println("Landing first");
            } else {
                K = 16;
                System.out.println("Taking off first");
            }

            NumOfPlanes.setDisable(true);
            Speed.setDisable(true);
            LandingFirst.setDisable(true);
            Start.setDisable(true);
            Carrier carier = new Carrier(N, M, K, time, label_on_board, label_waiting_for_take_off, label_in_the_air, label_waiting_for_landing);
            Aircraft[] aircraft = new Aircraft[M];

            for (int i = 0; i < M; i++) aircraft[i] = new Aircraft(carier, i, flights_to_fly, anchorRoot, time);
            for (int i = 0; i < M; i++) aircraft[i].start();

            //join makes all the graphic to wait till end od of "threads"
        });
    }
}


