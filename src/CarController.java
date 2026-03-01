import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

// Vyn ska prata med intercfacet ICarController, inte direkt med en konkret klass
public class CarController implements ICarController {
    private final Random random= new Random();
    private final int delay = 50;
    private final Timer timer = new Timer(delay, new TimerListener()); //decides how often the screen will be repainted

    CarView frame;

    // Lista med objekt som kan ritas upp i DrawPanel
    private final ArrayList<Drawable> drawableCars = new ArrayList<>();
    // Workshopen jobbar mot Car, inte en specifik bilklass
    private final VehicleFactory factory= new VehicleFactory();             // skapar ett beroende till en factory ist för varje biltyp enskilt
    private final CarModel model= new CarModel();
    private static final int MAX_CARS=10;

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

        //creating 3 vehicles
        cc.addVehicle(cc.factory.createVolvo(0,0));
        cc.addVehicle(cc.factory.createSaab(0,100));            //refererar till factory och inte enskilda instanserna av bilar
        cc.addVehicle(cc.factory.createScania(0, 200));


        cc.frame = new CarView("CarSim 1.0", cc); //creates GUI och gives carview a reference to controller
        cc.frame.drawPanel.setCars(cc.drawableCars); //drawpanel draws only drawable vehichles

        cc.timer.start();
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            ArrayList<Vehicle> snapshot = new ArrayList<>(model.getVehicles()); //copy of the list

            for (Vehicle car : snapshot) {
                car.move();

                // collision
                handleWallBounce(car);

                // WorkshopEligible styr vilka fordon som får hanteras av workshop
                if (car instanceof WorkshopEligible && car instanceof Car) {
                    Car workshopCar = (Car) car;
                    if (hitsWorkshop(car)) {
                        boolean loaded = model.getWorkshop().addCar(workshopCar);
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
        model.getVehicles().add(vehicle);
        // Bara drawable läggs in i rit-listan
        if (vehicle instanceof Drawable) {
            drawableCars.add((Drawable) vehicle);
        }
    }

    private void removeVehicle(Vehicle vehicle) {
        model.getVehicles().remove(vehicle);
        // Håll rit-listan synkad med fordonslistan
        if (vehicle instanceof Drawable) {
            drawableCars.remove((Drawable) vehicle);
        }
    }

    public void gas(int amount) {
        double gas = amount / 100.0;
        for (Vehicle car : model.getVehicles()) car.gas(gas);
    }

    public void brake(int amount) {
        double brake = amount / 100.0;
        for (Vehicle car : model.getVehicles()) car.brake(brake);
    }

    public void startAllCars() {
        for (Vehicle car : model.getVehicles()) car.startEngine();
    }

    public void stopAllCars() {
        for (Vehicle car : model.getVehicles()) car.stopEngine();
    }

    public void turboOn() {
        // Turbo styrs via TurboCar-interface
        for (Vehicle car : model.getVehicles()) {
            if (car instanceof TurboCar) {
                TurboCar turboCar = (TurboCar) car;
                turboCar.setTurboOn();
            }
        }
    }

    public void turboOff() {
        // Turbo styrs via TurboCar-interface
        for (Vehicle car : model.getVehicles()) {
            if (car instanceof TurboCar) {
                TurboCar turboCar = (TurboCar) car;
                turboCar.setTurboOff();
            }
        }
    }

    public void liftBed() {
        // Flaket styrs via TruckBed-interface
        for (Vehicle car : model.getVehicles()) {
            if (car instanceof TruckBed) {
                TruckBed truckBed = (TruckBed) car;
                truckBed.raiseTruckBed(10);
            }
        }
    }

    public void lowerBed() {
        // Flaket styrs via TruckBed-interface
        for (Vehicle car : model.getVehicles()) {
            if (car instanceof TruckBed) {
                TruckBed truckBed = (TruckBed) car;
                truckBed.lowerTruckBed(10);
            }
        }
    }
    @Override                                   //skriver över de nya metoderna från interfacet ICarController
    public void addRandomCar(){
        if (model.getVehicles().size()>=MAX_CARS) return;
        int y= model.getVehicles().size()*100;
        int type=  random.nextInt(3);   //randomiserar vilken bil
        Vehicle v;

        switch(type){
            case 0 -> v= factory.createSaab(0, y);
            case 1 -> v= factory.createScania(0, y);
            default -> v= factory.createVolvo(0, y);

        }
        addVehicle(v);}

    @Override
    public void removeLastCar(){
        if (model.getVehicles().isEmpty()) return;
        Vehicle last = model.getVehicles().get(model.getVehicles().size()-1);
        removeVehicle(last);
    }

    

}

  




