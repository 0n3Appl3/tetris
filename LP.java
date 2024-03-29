import java.awt.*;

public class LP extends Shape {
    public LP() {
        color = new Color(176, 33, 95);
        x = new int[] { 4, 4, 4, 4, 5 };
        y = new int[] { 0, 1, 2, 3, 3 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(2, 2); 
                blocks[1].setRelativePosition(1, 1);
                blocks[3].setRelativePosition(-1, -1);
                blocks[4].setRelativePosition(-2, 0);
                break;
            case 1:
                blocks[0].setRelativePosition(-2, 2);
                blocks[1].setRelativePosition(-1, 1);
                blocks[3].setRelativePosition(1, -1);
                blocks[4].setRelativePosition(0, -2);
                break;
            case 2:
                blocks[0].setRelativePosition(-2, -2); 
                blocks[1].setRelativePosition(-1, -1);
                blocks[3].setRelativePosition(1, 1);
                blocks[4].setRelativePosition(2, 0);
                break;
            case 3:
                blocks[0].setRelativePosition(2, -2); 
                blocks[1].setRelativePosition(1, -1);
                blocks[3].setRelativePosition(-1, 1);
                blocks[4].setRelativePosition(0, 2);
                break;
            default:
                break;
        }
        setNextRotation(4);
        return blocks;
    }
}
