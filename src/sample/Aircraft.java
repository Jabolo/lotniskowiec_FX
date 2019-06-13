package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Aircraft extends Thread {
    private final int numer;
    private final int flights_to_fly;
    private Carrier carrier;
    Circle plane = new Circle(8);
    AnchorPane anchorRoot;

//    @FXML
//    private AnchorPane anchorRoot;

    public Aircraft(Carrier carier, int numer, int flights_to_fly, AnchorPane anchorRoot) {
        super("Aircraft no." + numer);
        this.carrier = carier;
        this.numer = numer;
        this.flights_to_fly = flights_to_fly;
        this.anchorRoot = anchorRoot;
        plane.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        anchorRoot.getChildren().add(plane);

    }

    public void run() {
        for (int i = 0; i < flights_to_fly; i++) {


            //wait 4 start
            try {
                Thread.sleep((long) (Math.random() * 4000) + 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("+++ [" + Thread.currentThread().getName() + "] :: wanna_take_off");
            try {
                carrier.take_off(plane);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //flight
            try {
                Thread.sleep((long) (Math.random() * 10000) + 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--- [" + Thread.currentThread().getName() + "] :: wanna land");

            //landing
            try {
                carrier.landing(plane);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("___ [" + Thread.currentThread().getName() + "] :: has just landed");

        }
    }
}
