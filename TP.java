import java.awt.*;

public class TP extends Shape {
    public TP() {
        color = new Color(145, 10, 10);
        x = new int[] { 4, 3, 4, 5, 5 };
        y = new int[] { 1, 2, 2, 2, 3 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(1, 1); 
                blocks[1].setRelativePosition(1, -1);
                blocks[3].setRelativePosition(-1, 1);
                blocks[4].setRelativePosition(-2, 0);
                break;
            case 1:
                blocks[0].setRelativePosition(-1, 1);
                blocks[1].setRelativePosition(1, 1);
                blocks[3].setRelativePosition(-1, -1);
                blocks[4].setRelativePosition(0, -2);
                break;
            case 2:
                blocks[0].setRelativePosition(-1, -1); 
                blocks[1].setRelativePosition(-1, 1);
                blocks[3].setRelativePosition(1, -1);
                blocks[4].setRelativePosition(2, 0);
                break;
            case 3:
                blocks[0].setRelativePosition(1, -1); 
                blocks[1].setRelativePosition(-1, -1);
                blocks[3].setRelativePosition(1, 1);
                blocks[4].setRelativePosition(0, 2);
                break;
            default:
                break;
        }
        setNextRotation(4);
        return blocks;
    }
}
