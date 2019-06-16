package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Aircraft extends Thread {
    private final int numer;
    private final int flights_to_fly;
    private Carrier carrier;
    Circle plane = new Circle(15);
    AnchorPane anchorRoot;
    int time;

//    @FXML
//    private AnchorPane anchorRoot;

    public Aircraft(Carrier carier, int numer, int flights_to_fly, AnchorPane anchorRoot, int time) {
        super("Aircraft no." + numer);
        this.carrier = carier;
        this.numer = numer;
        this.flights_to_fly = flights_to_fly;
        this.anchorRoot = anchorRoot;
        plane.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        anchorRoot.getChildren().add(plane);
        plane.setCenterX(500);
        plane.setCenterY(75);
        this.time = time;
    }

    public void run() {
        for (int i = 0; i < flights_to_fly; i++) {


            //wait for start
            try {
                Thread.sleep((int) (Math.random() * 3) + time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                carrier.take_off(plane);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < (int) (Math.random() * 5 + 2); j++) {
                try {
                    carrier.animation_flying(plane, time);
                    sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //landing
            try {
                carrier.landing(plane);
                sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
