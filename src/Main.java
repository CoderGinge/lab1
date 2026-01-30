public class Main {
    public static void main(String[] args) {
        Saab95 saab = new Saab95();
        saab.startEngine();
        saab.setTurboOn();
        saab.gas(1.0);
        saab.move();
        saab.brake(0.5);
        saab.move();

        System.out.println("Speed: " + saab.getCurrentSpeed());
        System.out.println("Position: x=" + saab.x + ", y=" + saab.y);

    }
}
