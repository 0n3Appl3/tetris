import javax.imageio.ImageIO;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public abstract class Shape {
    protected Block[] blocks = new Block[4];
    protected Block[] previewBlocks = null;
    protected Color color = null;
    protected int[] x = null;
    protected int[] y = null;
    protected int index = 0;

    public abstract Block[] rotate();

    public Block[] draw() {
        blocks[0] = new Block(x[0], y[0], color);
        blocks[1] = new Block(x[1], y[1], color);
        blocks[2] = new Block(x[2], y[2], color);
        blocks[3] = new Block(x[3], y[3], color);
        return blocks;
    }

    public void previewShape(Graphics g) {
        int size = 25;
        int xPoint = 0;
        int yPoint = 0;

        g.setColor(color);
        for (int i = 0; i < x.length; i++) {
            xPoint = 50 + ((x[i] - 4) * size);
            yPoint = 50 + (y[i] * size);
            g.fillRect(xPoint, yPoint, size, size);
            try {
                BufferedImage image = ImageIO.read(new File("textures/block.png"));
                g.drawImage(image, xPoint, yPoint, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNextRotation(int combinations) {
        index++;
        if (index % combinations == 0)
            index = 0;
    }
    
    public void saveState(Block[] b) {
        blocks = b;
    }
}
