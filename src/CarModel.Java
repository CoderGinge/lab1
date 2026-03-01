import java.util.ArrayList;
import java.util.List;

public class CarModel{
    // håller listorna ist för carController
    private final List<Vehicle> vehicles= new ArrayList<>();
    private final Workshop<Car> workshop= new Workshop<>(1);

    public void addVehicle(Vehicle v){
        vehicles.add(v);
    }

    public void removeVehicle(Vehicle v){
        vehicles.remove(v);
    }

    public List<Vehicle> getVehicles(){
        return vehicles;
    }

    public Workshop<Car> getWorkshop(){
        return workshop;
    }

}