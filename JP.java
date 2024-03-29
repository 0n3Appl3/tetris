import java.awt.*;

public class JP extends Shape {
    public JP() {
        color = new Color(35, 28, 138);
        x = new int[] { 4, 4, 4, 3, 2 };
        y = new int[] { 0, 1, 2, 2, 2 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(2, 2); 
                blocks[1].setRelativePosition(1, 1);
                blocks[3].setRelativePosition(1, -1);
                blocks[4].setRelativePosition(2, -2);
                break;
            case 1:
                blocks[0].setRelativePosition(-2, 2); 
                blocks[1].setRelativePosition(-1, 1);
                blocks[3].setRelativePosition(1, 1);
                blocks[4].setRelativePosition(2, 2);
                break;
            case 2:
                blocks[0].setRelativePosition(-2, -2); 
                blocks[1].setRelativePosition(-1, -1);
                blocks[3].setRelativePosition(-1, 1);
                blocks[4].setRelativePosition(-2, 2);
                break;
            case 3:
                blocks[0].setRelativePosition(2, -2); 
                blocks[1].setRelativePosition(1, -1);
                blocks[3].setRelativePosition(-1, -1);
                blocks[4].setRelativePosition(-2, -2);
                break;
            default:
                break;
        }
        setNextRotation(4);
        return blocks;
    }
}
