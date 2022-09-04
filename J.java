import java.awt.*;

public class J implements Shape {
    Color color = new Color(196, 113, 52);

    @Override
    public Block[] draw() {
        blocks[0] = new Block(4, 0, color);
        blocks[1] = new Block(4, 1, color);
        blocks[2] = new Block(4, 2, color);
        blocks[3] = new Block(3, 2, color);
        return blocks;
    }

    @Override
    public void rotate() {

    }
    
}
