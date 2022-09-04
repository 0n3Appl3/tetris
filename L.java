import java.awt.*;

public class L implements Shape {
    Color color = new Color(212, 212, 212);

    @Override
    public Block[] draw() {
        blocks[0] = new Block(4, 0, color);
        blocks[1] = new Block(4, 1, color);
        blocks[2] = new Block(4, 2, color);
        blocks[3] = new Block(5, 2, color);
        return blocks;
    }

    @Override
    public void rotate() {

    }
    
}
