import java.util.ArrayList;

public class Workshop<T extends Car> {
    private final int capacity;   //number of cars a workshop can handle
    private final ArrayList<T> inside;    // list of type car, cars in workshop

    public Workshop(int capacity){
        this.capacity=Math.max(1, capacity);
        this.inside= new ArrayList<>();
    }

    public boolean addCar (T car) {
        if (car == null) return false;
        if (inside.contains(car)) return false;
        if (inside.size() >= capacity) return false;
        inside.add(car);
        return true;
    }

    public T takeCarOut(T car){
        if (inside.contains(car)){
            inside.remove(car);
            return car;
        }
        else
            return null;

    }

    
    }

