import java.util.ArrayList;

public class Workshop<T extends Car> {
    private final int capacity;   //number of cars a workshop can handle
    private final ArrayList<T> inside;    // list of type car, cars in workshop

    public Workshop(int capacity){
        this.capacity=capacity;
        this.inside= new ArrayList<>();
    }

    public void addCar (T car) {
        if (inside.size()<capacity){
            inside.add(car);
        }
        else System.out.println("The workshop is full");
    }

    public T takeCarOut(T car){
        if (inside.contains(car)){
            inside.remove(car);
            return car;
        }
        else System.out.println("The car is not in the workshop");
        return null;

    }

    
    }

