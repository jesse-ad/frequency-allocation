import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class NetworkVisualiser {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int PADDING = 80;

    public static void createImage(
            Cell[] cells,
            Map<Cell, List<Cell>> network,
            String filename) {

        BufferedImage image = new BufferedImage(
                WIDTH,
                HEIGHT,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = image.createGraphics();

        // Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Improve drawing quality
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Find coordinate boundaries
        int minEasting = Integer.MAX_VALUE;
        int maxEasting = Integer.MIN_VALUE;
        int minNorthing = Integer.MAX_VALUE;
        int maxNorthing = Integer.MIN_VALUE;

        for (Cell cell : cells) {
            minEasting = Math.min(minEasting, cell.easting);
            maxEasting = Math.max(maxEasting, cell.easting);

            minNorthing = Math.min(minNorthing, cell.northing);
            maxNorthing = Math.max(maxNorthing, cell.northing);
        }

        // Draw network connections first
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(2));

        for (Cell cell : cells) {

            List<Cell> neighbours = network.get(cell);

            if (neighbours == null) {
                continue;
            }

            for (Cell neighbour : neighbours) {

                // Prevent drawing the same connection twice
                if (cell.id.compareTo(neighbour.id) < 0) {

                    int x1 = scaleX(
                            cell.easting,
                            minEasting,
                            maxEasting
                    );

                    int y1 = scaleY(
                            cell.northing,
                            minNorthing,
                            maxNorthing
                    );

                    int x2 = scaleX(
                            neighbour.easting,
                            minEasting,
                            maxEasting
                    );

                    int y2 = scaleY(
                            neighbour.northing,
                            minNorthing,
                            maxNorthing
                    );

                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }

        // Draw cells
        for (Cell cell : cells) {

            int x = scaleX(
                    cell.easting,
                    minEasting,
                    maxEasting
            );

            int y = scaleY(
                    cell.northing,
                    minNorthing,
                    maxNorthing
            );

            g.setColor(getFrequencyColour(cell.frequency));

            // Cell circle
            g.fillOval(x - 12, y - 12, 24, 24);

            g.setColor(Color.BLACK);
            g.drawOval(x - 12, y - 12, 24, 24);

            // Label
            g.drawString(
                    cell.id + " (" + cell.frequency + ")",
                    x + 15,
                    y + 5
            );
        }

        // Title
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(
                "Cellular Frequency Allocation Network",
                30,
                35
        );

        // Legend
        drawLegend(g);

        g.dispose();

        try {
            ImageIO.write(image, "png", new File(filename));
            System.out.println("Network visualisation saved to: " + filename);
        } catch (Exception e) {
            System.out.println("Could not save network visualisation.");
            e.printStackTrace();
        }
    }

    private static int scaleX(
            int easting,
            int minEasting,
            int maxEasting) {

        if (maxEasting == minEasting) {
            return WIDTH / 2;
        }

        return PADDING +
                (int) ((double) (easting - minEasting)
                        / (maxEasting - minEasting)
                        * (WIDTH - 2 * PADDING));
    }

    private static int scaleY(
            int northing,
            int minNorthing,
            int maxNorthing) {

        if (maxNorthing == minNorthing) {
            return HEIGHT / 2;
        }

        // Reverse Y so larger Northing appears higher on image
        return HEIGHT - PADDING -
                (int) ((double) (northing - minNorthing)
                        / (maxNorthing - minNorthing)
                        * (HEIGHT - 2 * PADDING));
    }

    private static Color getFrequencyColour(int frequency) {

        switch (frequency) {
            case 110:
                return Color.BLUE;
            case 111:
                return Color.RED;
            case 112:
                return Color.GREEN;
            case 113:
                return Color.ORANGE;
            case 114:
                return Color.MAGENTA;
            case 115:
                return Color.CYAN;
            default:
                return Color.GRAY;
        }
    }

    private static void drawLegend(Graphics2D g) {

        int x = WIDTH - 180;
        int y = 80;

        g.setFont(new Font("Arial", Font.PLAIN, 14));

        int[] frequencies = {110, 111, 112, 113, 114, 115};

        for (int frequency : frequencies) {

            g.setColor(getFrequencyColour(frequency));
            g.fillOval(x, y - 10, 15, 15);

            g.setColor(Color.BLACK);
            g.drawString(
                    "Frequency " + frequency,
                    x + 25,
                    y + 3
            );

            y += 25;
        }
    }
}
