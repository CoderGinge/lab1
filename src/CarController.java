import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Vyn ska prata med intercfacet ICarController, inte direkt med en konkret klass
public class CarController implements ICarController {

    private final int delay = 50;
    private final Timer timer = new Timer(delay, new TimerListener()); //decides how often the screen will be repainted

    CarView frame;

    private final ArrayList<Vehicle> cars = new ArrayList<>();
    // Lista med objekt som kan ritas upp i DrawPanel
    private final ArrayList<Drawable> drawableCars = new ArrayList<>();
    // Workshopen jobbar mot Car, inte en specifik bilklass
    private final Workshop<Car> workshop = new Workshop<>(1);

    private static final int CAR_IMG_W = 100;
    private static final int CAR_IMG_H = 60;

    // Observer: listeners notified after each update tick
    private final List<ControllerListener> listeners = new ArrayList<>();

    @Override
    public void addListener(ControllerListener l) {
        listeners.add(l);
    }

    private void notifyListeners() {
        for (ControllerListener l : listeners) {
            l.controllerUpdated();
        }
    }


    public static void main(String[] args) {
        CarController cc = new CarController();
        VehicleFactory factory = new VehicleFactory();

        //creating 3 vehicles
        Vehicle v = factory.createVolvo(0, 0);
        Vehicle s = factory.createSaab(0, 100);
        Vehicle sc = factory.createScania(0, 200);



        //adding in the list
        cc.addVehicle(v);
        cc.addVehicle(s);
        cc.addVehicle(sc);

        cc.frame = new CarView("CarSim 1.0", cc); //creates GUI och gives carview a reference to controller
        cc.frame.drawPanel.setCars(cc.drawableCars); //drawpanel draws only drawable vehichles

        cc.timer.start();
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            ArrayList<Vehicle> snapshot = new ArrayList<>(cars); //copy of the list

            for (Vehicle car : snapshot) {
                car.move();

                // collision
                handleWallBounce(car);

                // WorkshopEligible styr vilka fordon som får hanteras av workshop
                if (car instanceof WorkshopEligible && car instanceof Car) {
                    Car workshopCar = (Car) car;
                    if (hitsWorkshop(car)) {
                        boolean loaded = workshop.addCar(workshopCar);
                        if (loaded) {
                            removeVehicle(car);
                        } else {
                            car.stopEngine();
                            car.turnLeft();
                            car.turnLeft();
                            car.startEngine();
                        }
                    }
                }
            }

            notifyListeners();
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

    private boolean hitsWorkshop(Vehicle car) {
        Point p = frame.drawPanel.getWorkshopPoint();
        int x = (int) Math.round(car.getX());
        int y = (int) Math.round(car.getY());

        int hit = 50;
        return Math.abs(x - p.x) < hit && Math.abs(y - p.y) < hit; //target area
    }

    public void addVehicle(Vehicle vehicle) {
        cars.add(vehicle);
        // Bara drawable läggs in i rit-listan
        if (vehicle instanceof Drawable) {
            drawableCars.add((Drawable) vehicle);
        }
    }

    private void removeVehicle(Vehicle vehicle) {
        cars.remove(vehicle);
        // Håll rit-listan synkad med fordonslistan
        if (vehicle instanceof Drawable) {
            drawableCars.remove((Drawable) vehicle);
        }
    }

    public void gas(int amount) {
        double gas = amount / 100.0;
        for (Vehicle car : cars) car.gas(gas);
    }

    public void brake(int amount) {
        double brake = amount / 100.0;
        for (Vehicle car : cars) car.brake(brake);
    }

    public void startAllCars() {
        for (Vehicle car : cars) car.startEngine();
    }

    public void stopAllCars() {
        for (Vehicle car : cars) car.stopEngine();
    }

    public void turboOn() {
        // Turbo styrs via TurboCar-interface
        for (Vehicle car : cars) {
            if (car instanceof TurboCar) {
                TurboCar turboCar = (TurboCar) car;
                turboCar.setTurboOn();
            }
        }
    }

    public void turboOff() {
        // Turbo styrs via TurboCar-interface
        for (Vehicle car : cars) {
            if (car instanceof TurboCar) {
                TurboCar turboCar = (TurboCar) car;
                turboCar.setTurboOff();
            }
        }
    }

    public void liftBed() {
        // Flaket styrs via TruckBed-interface
        for (Vehicle car : cars) {
            if (car instanceof TruckBed) {
                TruckBed truckBed = (TruckBed) car;
                truckBed.raiseTruckBed(10);
            }
        }
    }

    public void lowerBed() {
        // Flaket styrs via TruckBed-interface
        for (Vehicle car : cars) {
            if (car instanceof TruckBed) {
                TruckBed truckBed = (TruckBed) car;
                truckBed.lowerTruckBed(10);
            }
        }
    }

  

}


