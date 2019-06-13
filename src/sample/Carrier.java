package sample;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Carrier extends Thread {
    private static volatile Object runaway = new Object();
    private static volatile Lock access = new ReentrantLock();

    volatile private int on_board;
    volatile private int on_board_waiting;
    volatile public int in_the_air;
    volatile private int in_the_air_waiting;

    private int capacity; //who is right
    private int num_of_aircrafts;
    private int K; //who is right;

    int time_each_animation;
    Label lob;
    Label lobw;
    Label lita;
    Label litaw;

    public Carrier(int capacity, int on_board, int K, int time, Label ob, Label obw, Label ita, Label itaw) {
        this.capacity = capacity;
        this.on_board = on_board;
        this.K = K;
        this.num_of_aircrafts = on_board;
        this.lob = ob;
        this.lobw = obw;
        this.lita = ita;
        this.litaw = itaw;
        this.time_each_animation = time;
    }

    public void take_off(Circle plane) throws InterruptedException {
        on_board_waiting++;
        Platform.runLater(() -> {
            lobw.setText("" + on_board_waiting);
        });
        access.lock();
        while (K < capacity && in_the_air_waiting > 0) {
            access.unlock();
            synchronized (runaway) {
                runaway.wait();
            }
            access.lock();
        }

        on_board_waiting--;
        Platform.runLater(() -> {
            lobw.setText("" + on_board_waiting);
        });
        on_board--;
        Platform.runLater(() -> {
            lob.setText("" + on_board);
        });
        in_the_air++;
        Platform.runLater(() -> {
            lita.setText("" + in_the_air);
        });


        System.out.println("In the air:" + in_the_air);

        //pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true);
//
//        pathTransition.setOnFinished((lambda) -> {runaway.notifyAll();});

        animation_take_off(plane, time_each_animation);
        sleep(time_each_animation);

        synchronized (runaway) {
            runaway.notifyAll();
        }
        access.unlock();
    }

    public void landing(Circle plane) throws InterruptedException {
        in_the_air_waiting++;
        Platform.runLater(() -> {
            litaw.setText("" + in_the_air_waiting);
        });
        access.lock();
        while ((K > capacity) && (on_board_waiting > 0)) {
            access.unlock();
            synchronized (runaway) {
                runaway.wait();
            }
            access.lock();
        }
        in_the_air_waiting--;
        Platform.runLater(() -> {
            litaw.setText("" + in_the_air_waiting);
        });
        animation_landing(plane, time_each_animation);
        sleep(time_each_animation);
        in_the_air--;
        Platform.runLater(() -> {
            lita.setText("" + in_the_air);
        });
        on_board++;
        Platform.runLater(() -> {
            lob.setText("" + on_board);
        });
        synchronized (runaway) {
            runaway.notifyAll();
        }
        access.unlock();
    }

    private void animation_take_off(Node plane, int time_take_off) {
        Path path = new Path();
        path.getElements().add(new MoveTo(500, 75));
        path.getElements().add(new LineTo(500, 300));
        path.getElements().add(new CubicCurveTo(500, 475, 410, 475, 250, 475));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(time_take_off));
        pathTransition.setPath(path);
        pathTransition.setNode(plane);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        Platform.runLater(() -> {
            pathTransition.play();
        });

    }

    private void animation_landing(Node plane, int time_landing) {
        Path path = new Path();
        path.getElements().add(new MoveTo(250, 475));
        path.getElements().add(new CubicCurveTo(50, 475, 150, 300, 350, 420));
        path.getElements().add(new CubicCurveTo(450, 475, 500, 475, 500, 300));
        path.getElements().add(new LineTo(500, 75));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(time_landing));
        pathTransition.setPath(path);
        pathTransition.setNode(plane);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        Platform.runLater(() -> {
            pathTransition.play();
        });
    }

    public void animation_flying(Node plane, int time_to_fly) {
        Path path = new Path();
        path.getElements().add(new MoveTo(250, 475));
        path.getElements().add(new QuadCurveTo(50, 475, 50, 300));
        path.getElements().add(new QuadCurveTo(50, 75, 175, 75));
        path.getElements().add(new QuadCurveTo(300, 75, 300, 300));
        path.getElements().add(new QuadCurveTo(300, 475, 250, 475));
        //path.getElements().add(new CubicCurveTo(, 475, 500, 475, 500, 300));
        //path.getElements().add(new LineTo(500, 75));


        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(time_to_fly));
        pathTransition.setPath(path);
        pathTransition.setNode(plane);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        Platform.runLater(() -> {
            pathTransition.play();
        });
    }
}
