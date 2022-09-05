import java.awt.*;

public class S extends Shape {
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
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(1, 1); 
                blocks[1].setRelativePosition(0, 2);
                blocks[3].setRelativePosition(1, -1);
                break;
            case 1:
                blocks[0].setRelativePosition(-1, -1);
                blocks[1].setRelativePosition(0, -2);
                blocks[3].setRelativePosition(-1, 1);
                break;
            default:
                break;
        }
        setNextRotation(2);
        return blocks;
    }
    
}
