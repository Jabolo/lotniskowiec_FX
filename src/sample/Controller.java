package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane anchorRoot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int N = 15;
        int M = 5;
        int K = 16; //who has right
        int flights_to_fly = 2;

        Carrier carier = new Carrier(N, M, K);
        Aircraft[] aircraft = new Aircraft[M];

        for (int i = 0; i < M; i++) aircraft[i] = new Aircraft(carier, i, flights_to_fly, anchorRoot);
        for (int i = 0; i < M; i++) aircraft[i].start();

        //join makes all the graphic to wait till end od of "threads"

    }
}
