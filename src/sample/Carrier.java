package sample;

import javafx.animation.PathTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Carrier extends Thread {
    private static volatile Object runaway = new Object();
    private static volatile Lock access = new ReentrantLock();

    volatile private int on_board;
    volatile private int on_board_waiting = 0;
    volatile private int in_the_air = 0;
    volatile private int in_the_air_waiting = 0;

    private int capacity; //who is right
    private int num_of_aircrafts;
    private int K; //who is right


    public Carrier(int capacity, int on_board, int K) {
        this.capacity = capacity;
        this.on_board = on_board;
        this.K = K;

        this.num_of_aircrafts = on_board;
    }

    public void take_of(Circle plane, AnchorPane anchorRoot) throws InterruptedException {
        access.lock();
        on_board_waiting++;

        if ((K < capacity) && (in_the_air_waiting > 0)) {
            access.unlock();
            synchronized (runaway) {
                runaway.wait();
            }
            access.lock();
        }

        on_board_waiting--;
        on_board--;
        in_the_air++;


        //pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true);
//        Platform.runLater(() -> {
//            pathTransition.play();
//        });
//
//        pathTransition.setOnFinished((lambda) -> {runaway.notifyAll();});
//        synchronized (runaway) {
//            runaway.notifyAll();
//        }
        int time = (int) (Math.random() * 5000 + 2000);

        Path path = new Path();
        path.getElements().add(new MoveTo((int) (Math.random() * 60), (int) (Math.random() * 60)));
        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setPath(path);
        pathTransition.setNode(plane);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
        sleep(2000);
        access.unlock();


        synchronized (runaway) {
            runaway.notifyAll();
        }
    }

    public void landing() throws InterruptedException {
        access.lock();
        in_the_air_waiting++;
        if ((K > capacity) && (on_board_waiting > 0)) {
            access.unlock();
            synchronized (runaway) {
                runaway.wait();
            }
            access.lock();
        }
        in_the_air_waiting--;
        in_the_air--;
        on_board++;
        synchronized (runaway) {
            runaway.notifyAll();
        }
        access.unlock();
    }
}
