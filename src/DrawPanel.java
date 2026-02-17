

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {

    // Alla fordon som ska ritas
    private List<Vehicle> cars = new ArrayList<>();

    // Bilder
    private BufferedImage volvoImage;
    private BufferedImage saabImage;
    private BufferedImage scaniaImage;
    private BufferedImage volvoWorkshopImage;

    // Workshop-position (enkel “fast” punkt)
    private final Point volvoWorkshopPoint = new Point(500, 20);

    // Ungefärliga bildmått (kan justeras senare, men behövs för väggkollisionslogik i controller)
    // Här används inte dessa i DrawPanel, men det är bra att ha som “sanning” om ni vill.
    public static final int CAR_IMAGE_WIDTH = 100;
    public static final int CAR_IMAGE_HEIGHT = 60;

    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        try {
            volvoImage = ImageIO.read(requireResource("/pics/Volvo240.jpg"));
            saabImage = ImageIO.read(requireResource("/pics/Saab95.jpg"));
            scaniaImage = ImageIO.read(requireResource("/pics/Scania.jpg"));
            volvoWorkshopImage = ImageIO.read(requireResource("/pics/VolvoBrand.jpg"));
        } catch (IOException e) {
            throw new RuntimeException("Could not load picture... (IOException): " + e.getMessage(), e);

        }
    }

    // Controller anropar denna en gång när den skapat bilarna
    public void setCars(List<Vehicle> cars) {
        this.cars = cars;
    }

    public Point getWorkshopPoint() {
        return volvoWorkshopPoint;
    }

    // Hjälpmetod: ger tydligt fel om resursen saknas
    private static java.io.InputStream requireResource(String path) {
        var stream = DrawPanel.class.getResourceAsStream(path);
        if (stream == null) {
            throw new RuntimeException("Cant find the resource: " + path +
                    ". Control that the pictures are in pics and that pics are in resource root.");
        }
        return stream;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rita workshop
        if (volvoWorkshopImage != null) {
            g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);
        }

        // Rita alla bilar
        for (Vehicle car : cars) {
            BufferedImage img = pickImageFor(car);
            if (img == null) continue;

            int x = (int) Math.round(car.getX());
            int y = (int) Math.round(car.getY());

            g.drawImage(img, x, y, null);
        }
    }

    private BufferedImage pickImageFor(Vehicle car) {
        if (car instanceof Volvo240) return volvoImage;
        if (car instanceof Saab95) return saabImage;
        if (car instanceof Scania) return scaniaImage;
        // okänd biltyp -> rita som volvo eller return null
        return volvoImage;
    }
}

