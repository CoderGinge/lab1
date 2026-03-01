// CArView anropar controllern via detta interface
public interface ICarController {

    void gas(int amount);
    void brake(int amount);
    void startAllCars();
    void stopAllCars();
    void turboOn();
    void turboOff();
    void liftBed();
    void lowerBed();
    void addListener(ControllerListener l);

    void addRandomCar();    // metoder för nya knapparna
    void removeLastCar();   
}
