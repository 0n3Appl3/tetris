import java.awt.*;

public class I extends Shape {
    Color color = new Color(222, 44, 44);

    @Override
    public Block[] draw() {
        blocks[0] = new Block(4, 0, color);
        blocks[1] = new Block(4, 1, color);
        blocks[2] = new Block(4, 2, color);
        blocks[3] = new Block(4, 3, color);
        return blocks;
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(-1, 1); 
                blocks[2].setRelativePosition(1, 1);
                blocks[3].setRelativePosition(2, -2);
                break;
            case 1:
                blocks[0].setRelativePosition(1, -1);
                blocks[2].setRelativePosition(-1, -1);
                blocks[3].setRelativePosition(-2, 2);
                break;
            default:
                break;
        }
        setNextRotation(2);
        return blocks;
    }
    
}
