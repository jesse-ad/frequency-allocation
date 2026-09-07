/* Network visualisation class handles generating an image
   for the network to show cell locations, relationships,
   and allocated frequencies.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class NetworkVisualiser {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 900;

    private static final int LEFT_PADDING = 100;
    private static final int RIGHT_PADDING = 250;
    private static final int TOP_PADDING = 100;
    private static final int BOTTOM_PADDING = 100;

    private static final int NODE_RADIUS = 5;

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

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

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

        // Title
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));

        g.drawString(
                "Cellular Frequency Allocation Network",
                40,
                45
        );

        g.setFont(new Font("Arial", Font.PLAIN, 14));

        g.drawString(
                "Edges represent cells within the interference threshold",
                40,
                70
        );

        // Draw network connections
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(1.5f));

        for (Cell cell : cells) {

            List<Cell> neighbours = network.get(cell);

            if (neighbours == null) {
                continue;
            }

            for (Cell neighbour : neighbours) {

                // Draw each connection only once
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

            // Frequency colour
            g.setColor(getFrequencyColour(cell.frequency));

            g.fillOval(
                    x - NODE_RADIUS,
                    y - NODE_RADIUS,
                    NODE_RADIUS * 2,
                    NODE_RADIUS * 2
            );

            // Node outline
            g.setColor(Color.BLACK);

            g.drawOval(
                    x - NODE_RADIUS,
                    y - NODE_RADIUS,
                    NODE_RADIUS * 2,
                    NODE_RADIUS * 2
            );

            // Cell label
            g.setFont(new Font("Arial", Font.BOLD, 13));

            String label = cell.id + " (" + cell.frequency + ")";

            int labelX = x + 10;
            int labelY = y - 8;

            // Move labels for very close cells
            if (cell.id.equals("F")) {
                labelX = x - 50;
                labelY = y - 12;
            }

            if (cell.id.equals("G")) {
                labelX = x + 10;
                labelY = y + 22;
            }

            if (cell.id.equals("D")) {
                labelX = x - 55;
                labelY = y - 10;
            }

            if (cell.id.equals("E")) {
                labelX = x + 12;
                labelY = y - 12;
            }

            if (cell.id.equals("R")) {
                labelX = x - 45;
                labelY = y - 12;
            }

            if (cell.id.equals("S")) {
                labelX = x + 12;
                labelY = y + 22;
            }

            g.setColor(Color.BLACK);
            g.drawString(label, labelX, labelY);
        }

        // Legend
        drawLegend(g);

        g.dispose();

        try {

            ImageIO.write(
                    image,
                    "png",
                    new File(filename)
            );

            System.out.println(
                    "Network visualisation saved to: " + filename
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not save network visualisation."
            );

            e.printStackTrace();
        }
    }

    private static int scaleX(
            int easting,
            int minEasting,
            int maxEasting) {

        int plotWidth = WIDTH - LEFT_PADDING - RIGHT_PADDING;

        if (maxEasting == minEasting) {
            return LEFT_PADDING + plotWidth / 2;
        }

        return LEFT_PADDING
                + (int) (
                ((double) (easting - minEasting)
                        / (maxEasting - minEasting))
                        * plotWidth
        );
    }

    private static int scaleY(
            int northing,
            int minNorthing,
            int maxNorthing) {

        int plotHeight = HEIGHT - TOP_PADDING - BOTTOM_PADDING;

        if (maxNorthing == minNorthing) {
            return TOP_PADDING + plotHeight / 2;
        }

        return TOP_PADDING
                + (int) (
                ((double) (maxNorthing - northing)
                        / (maxNorthing - minNorthing))
                        * plotHeight
        );
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

        int x = WIDTH - 210;
        int y = 150;

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        g.drawString(
                "Frequencies",
                x,
                y
        );

        int[] frequencies = {
                110, 111, 112, 113, 114, 115
        };

        for (int i = 0; i < frequencies.length; i++) {

            int currentY = y + 30 + (i * 35);

            g.setColor(
                    getFrequencyColour(frequencies[i])
            );

            g.fillOval(
                    x,
                    currentY - 7,
                    14,
                    14
            );

            g.setColor(Color.BLACK);

            g.drawOval(
                    x,
                    currentY - 7,
                    14,
                    14
            );

            g.drawString(
                    String.valueOf(frequencies[i]),
                    x + 25,
                    currentY + 5
            );
        }

        int edgeY = y + 250;

        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(2));

        g.drawLine(
                x,
                edgeY,
                x + 20,
                edgeY
        );

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 13));

        g.drawString(
                "Interference connection",
                x + 30,
                edgeY + 5
        );
    }
}
