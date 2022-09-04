import java.awt.*;

public class S implements Shape {
    Color color = new Color(47, 67, 168);

    @Override
    public Block[] draw() {
        blocks[0] = new Block(4, 0, color);
        blocks[1] = new Block(5, 0, color);
        blocks[2] = new Block(4, 1, color);
        blocks[3] = new Block(3, 1, color);
        return blocks;
    }

    @Override
    public void rotate() {

    }
    
}
