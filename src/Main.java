public class Main {

    public static void main(String[] args) {

        System.out.println("=== Uppgift 1: Scania ===");
        Scania scania = new Scania();

        // Höj flaket när den står stilla (ska funka)
        scania.raiseTruckBed(30);
        System.out.println("Flakvinkel (ska vara 30): " + scania.getTruckBedAngle());

        // Försök köra med flaket uppe (ska INTE röra sig)
        scania.startEngine();
        scania.gas(0.8);
        scania.move();
        System.out.println("Scania efter försök att köra med flak uppe (ska vara kvar på 0,0): x="
                + scania.x + ", y=" + scania.y);

        // Sänk flaket till 0 så att den får köra
        scania.lowerTruckBed(30);
        System.out.println("Flakvinkel (ska vara 0): " + scania.getTruckBedAngle());

        // Kör nu (ska röra sig)
        scania.gas(0.8);
        scania.move();
        System.out.println("Scania rör sig nu: x=" + scania.x + ", y=" + scania.y);

        // Försök höja flaket när den rör sig (ska INTE funka -> vinkeln ska förbli 0)
        scania.raiseTruckBed(10);
        System.out.println("Flakvinkel under rörelse (ska vara 0): " + scania.getTruckBedAngle());


        System.out.println("\n=== Uppgift 2: Biltransport ===");
        CarTransport transport = new CarTransport(2);
        Volvo240 volvo = new Volvo240();
        Saab95 saab = new Saab95();

        // Placera bilar nära transporten så att lastning tillåts
        volvo.x = transport.x;
        volvo.y = transport.y;
        saab.x = transport.x;
        saab.y = transport.y;

        // Sänk ramp och lasta
        transport.lowerRamp();
        System.out.println("Ramp nere: " + transport.isRampDown());

        System.out.println("Lasta Volvo: " + transport.loadCar(volvo));
        System.out.println("Lasta Saab: " + transport.loadCar(saab));
        System.out.println("Antal lastade bilar (ska vara 2): " + transport.getLoadedCount());

        // Höj ramp och kör iväg
        transport.raiseRamp();
        transport.startEngine();
        transport.gas(1.0);
        transport.move();

        System.out.println("Transport position: (" + transport.x + ", " + transport.y + ")");
        System.out.println("Volvo position (ska matcha): (" + volvo.x + ", " + volvo.y + ")");
        System.out.println("Saab position (ska matcha): (" + saab.x + ", " + saab.y + ")");

        // Stanna helt innan ramp sänks (lowerRamp fungerar bara när stilla)
        while (transport.getCurrentSpeed() > 0) {
            transport.brake(1.0);
        }
        transport.lowerRamp();
        System.out.println("Ramp nere efter stopp: " + transport.isRampDown());

        // Lossa (ska ge Saab först pga FILO)
        Car unloaded = transport.unloadCar();
        if (unloaded != null) {
            System.out.println("Lossad bil (ska vara Saab): " + unloaded.modelName);
        } else {
            System.out.println("Kunde inte lossa bil (ramp uppe / rör sig / tom).");
        }

        System.out.println("Antal lastade bilar efter lossning (ska vara 1): " + transport.getLoadedCount());


        System.out.println("\n=== Uppgift 3: Workshop ===");
        Workshop<Volvo240> volvoWorkshop = new Workshop<>(2);
        Volvo240 v1 = new Volvo240();
        Volvo240 v2 = new Volvo240();

        volvoWorkshop.addCar(v1);
        volvoWorkshop.addCar(v2);

        Volvo240 out = volvoWorkshop.takeCarOut(v1);
        if (out != null) {
            System.out.println("Uthämtad bil från verkstad: " + out.modelName);
        } else {
            System.out.println("Kunde inte hämta ut bilen (fanns inte i verkstaden).");
        }

        // Detta ska INTE kompilera (bra att kunna säga på redovisning):
        // volvoWorkshop.addCar(new Saab95());

        System.out.println("\n=== Demo klar ===");
    }
}
