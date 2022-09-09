import java.awt.*;

public class J extends Shape {
    public J() {
        color = new Color(196, 113, 52);
        x = new int[] { 4, 4, 4, 3 };
        y = new int[] { 0, 1, 2, 2 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(1, 1); 
                blocks[2].setRelativePosition(-1, -1);
                blocks[3].setRelativePosition(0, -2);
                break;
            case 1:
                blocks[0].setRelativePosition(-1, 1);
                blocks[2].setRelativePosition(1, -1);
                blocks[3].setRelativePosition(2, 0);
                break;
            case 2:
                blocks[0].setRelativePosition(-1, -1); 
                blocks[2].setRelativePosition(1, 1);
                blocks[3].setRelativePosition(0, 2);
                break;
            case 3:
                blocks[0].setRelativePosition(1, -1); 
                blocks[2].setRelativePosition(-1, 1);
                blocks[3].setRelativePosition(-2, 0);
                break;
            default:
                break;
        }
        setNextRotation(4);
        return blocks;
    }
}
