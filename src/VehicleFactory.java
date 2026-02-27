public class VehicleFactory {

  public Volvo240 createVolvo(double x, double y) {
    Volvo240 v = new Volvo240();
    v.setX(x);
    v.setY(y);
    v.turnRight();
    return v;
  }

  public Saab95 createSaab(double x, double y) {
    Saab95 s = new Saab95();
    s.setX(x);
    s.setY(y);
    s.turnRight();
    return s;
  }

  public Scania createScania(double x, double y) {
    Scania sc = new Scania();
    sc.setX(x);
    sc.setY(y);
    sc.turnRight();
    return sc;
  }
}