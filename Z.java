import java.awt.*;

public class Z extends Shape {
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
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(1, 1); 
                blocks[1].setRelativePosition(2, 0);
                blocks[3].setRelativePosition(-1, 1);
                break;
            case 1:
                blocks[0].setRelativePosition(-1, -1);
                blocks[1].setRelativePosition(-2, 0);
                blocks[3].setRelativePosition(1, -1);
                break;
            default:
                break;
        }
        setNextRotation(2);
        return blocks;
    }
    
}
