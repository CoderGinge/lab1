

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {

    // every vehicle that we will draw
    private List<Vehicle> cars = new ArrayList<>();

    // pictures
    private BufferedImage volvoImage;
    private BufferedImage saabImage;
    private BufferedImage scaniaImage;
    private BufferedImage volvoWorkshopImage;

    // Workshop-position
    private final Point volvoWorkshopPoint = new Point(500, 20);

    //drawing the panel
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true); //better animation
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        //loads pictures to window
        try {
            volvoImage = ImageIO.read(requireResource("/pics/Volvo240.jpg"));
            saabImage = ImageIO.read(requireResource("/pics/Saab95.jpg"));
            scaniaImage = ImageIO.read(requireResource("/pics/Scania.jpg"));
            volvoWorkshopImage = ImageIO.read(requireResource("/pics/VolvoBrand.jpg"));
        } catch (IOException e) {
            throw new RuntimeException("Could not load picture... (IOException): " + e.getMessage(), e);

        } //clear error message also
    }

    // Controller calls this once, when created
    public void setCars(List<Vehicle> cars) {
        this.cars = cars;
    }

    public Point getWorkshopPoint() {
        return volvoWorkshopPoint;
    }

    // Help method: gives error if resource is missing
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
        super.paintComponent(g); //clears earlier window and then draws the background

        // draw workshop image
        if (volvoWorkshopImage != null) {
            g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);
        }

        // draw all cars
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
        // correct picture. If nothing, volvoImage
        return volvoImage;
    }
}

