import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CarController {

    private final int delay = 50;
    private final Timer timer = new Timer(delay, new TimerListener());

    CarView frame;

    private final ArrayList<Vehicle> cars = new ArrayList<>();
    private final Workshop<Volvo240> volvoWorkshop = new Workshop<>(1);

    private static final int CAR_IMG_W = 100;
    private static final int CAR_IMG_H = 60;

    public static void main(String[] args) {
        CarController cc = new CarController();

        Volvo240 v = new Volvo240();
        v.setX(0); v.setY(0);

        Saab95 s = new Saab95();
        s.setX(0); s.setY(100);

        Scania sc = new Scania();
        sc.setX(0); sc.setY(200);

        // kör åt höger
        v.turnRight();
        s.turnRight();
        sc.turnRight();

        cc.cars.add(v);
        cc.cars.add(s);
        cc.cars.add(sc);

        cc.frame = new CarView("CarSim 1.0", cc);
        cc.frame.drawPanel.setCars(cc.cars);

        cc.timer.start();
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            ArrayList<Vehicle> snapshot = new ArrayList<>(cars);

            for (Vehicle car : snapshot) {
                car.move();

                // väggkollision
                handleWallBounce(car);

                // workshop: bara Volvo lastas
                if (car instanceof Volvo240 volvo) {
                    if (hitsWorkshop(volvo)) {
                        boolean loaded = volvoWorkshop.addCar(volvo); // byt namn om din metod heter annat
                        if (loaded) {
                            cars.remove(volvo);
                        } else {
                            volvo.stopEngine();
                            volvo.turnLeft();
                            volvo.turnLeft();
                            volvo.startEngine();
                        }
                    }
                }
            }

            frame.drawPanel.repaint();
        }
    }

    private void handleWallBounce(Vehicle car) {
        int w = frame.drawPanel.getWidth();
        int h = frame.drawPanel.getHeight();

        int x = (int) Math.round(car.getX());
        int y = (int) Math.round(car.getY());

        int maxX = w - CAR_IMG_W;
        int maxY = h - CAR_IMG_H;

        boolean hit = false;

        if (x < 0) { car.setX(0); hit = true; }
        else if (x > maxX) { car.setX(maxX); hit = true; }

        if (y < 0) { car.setY(0); hit = true; }
        else if (y > maxY) { car.setY(maxY); hit = true; }

        if (hit) {
            car.stopEngine();
            car.turnLeft();
            car.turnLeft();
            car.startEngine();
        }
    }

    private boolean hitsWorkshop(Volvo240 car) {
        Point p = frame.drawPanel.getWorkshopPoint();
        int x = (int) Math.round(car.getX());
        int y = (int) Math.round(car.getY());

        int hit = 50;
        return Math.abs(x - p.x) < hit && Math.abs(y - p.y) < hit;
    }

    void gas(int amount) {
        double gas = amount / 100.0;
        for (Vehicle car : cars) car.gas(gas);
    }

    void brake(int amount) {
        double brake = amount / 100.0;
        for (Vehicle car : cars) car.brake(brake);
    }

    void startAllCars() {
        for (Vehicle car : cars) car.startEngine();
    }

    void stopAllCars() {
        for (Vehicle car : cars) car.stopEngine();
    }

    void turboOn() {
        for (Vehicle car : cars) car.perform(Vehicle.Action.TURBO_ON);
    }

    void turboOff() {
        for (Vehicle car : cars) car.perform(Vehicle.Action.TURBO_OFF);
    }

    void liftBed() {
        for (Vehicle car : cars) car.perform(Vehicle.Action.LIFT_BED);
    }

    void lowerBed() {
        for (Vehicle car : cars) car.perform(Vehicle.Action.LOWER_BED);
    }

  

}


