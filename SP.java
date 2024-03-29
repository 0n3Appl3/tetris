import java.awt.*;

public class SP extends Shape {
    public SP() {
        color = new Color(148, 45, 168);
        x = new int[] { 4, 4, 4, 5, 5 };
        y = new int[] { 0, 1, 2, 2, 3 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(2, 2); 
                blocks[1].setRelativePosition(1, 1);
                blocks[3].setRelativePosition(-1, 1);
                blocks[4].setRelativePosition(-2, 0);
                break;
            case 1:
                blocks[0].setRelativePosition(-2, 2); 
                blocks[1].setRelativePosition(-1, 1);
                blocks[3].setRelativePosition(-1, -1);
                blocks[4].setRelativePosition(0, -2);
                break;
            case 2:
                blocks[0].setRelativePosition(-2, -2); 
                blocks[1].setRelativePosition(-1, -1);
                blocks[3].setRelativePosition(1, -1);
                blocks[4].setRelativePosition(2, 0);
                break;
            case 3:
                blocks[0].setRelativePosition(2, -2); 
                blocks[1].setRelativePosition(1, -1);
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
