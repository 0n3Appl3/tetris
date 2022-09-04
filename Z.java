import java.awt.*;

public class Z implements Shape {
    Color color = new Color(86, 204, 57);

    @Override
    public Block[] draw() {
        blocks[0] = new Block(4, 0, color);
        blocks[1] = new Block(3, 0, color);
        blocks[2] = new Block(4, 1, color);
        blocks[3] = new Block(5, 1, color);
        return blocks;
    }

    @Override
    public void rotate() {

    }
    
}
