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


            //wait 4 start
            try {
                Thread.sleep((long) (Math.random() * 2000) + 200);
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
//            try {
//                carrier.animation_flying(plane, time);
//                //Thread.sleep(time);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            //carrier.animation_flying(plane, time);
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
