

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {

    // every vehicle that we will draw
    private List<Drawable> cars = new ArrayList<>();

    // pictures
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
            volvoWorkshopImage = ImageIO.read(requireResource("/pics/VolvoBrand.jpg"));
        } catch (IOException e) {
            throw new RuntimeException("Could not load picture... (IOException): " + e.getMessage(), e);

        } //clear error message also
    }

    // Controller calls this once, when created
    public void setCars(List<Drawable> cars) {
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


    private BufferedImage getImage(String path) {
        if (path == null || path.isEmpty()) return null;
        try {
            return ImageIO.read(requireResource(path));
        } catch (IOException e) {
            throw new RuntimeException("Could not load picture... (IOException): "+e.getMessage(), e);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //clears earlier window and then draws the background

        // draw workshop image
        if (volvoWorkshopImage != null) {
            g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);
        }

        // draw all cars
        for (Drawable car : cars) {
            BufferedImage img = getImage(car.getImagePath());
            if (img == null) continue;

            int x = (int) Math.round(car.getX());
            int y = (int) Math.round(car.getY());

            g.drawImage(img, x, y, null);
        }
    }

}

