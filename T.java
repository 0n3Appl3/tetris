import java.awt.*;

public class T extends Shape {
    public T() {
        color = new Color(123, 171, 160);
        x = new int[] { 4, 3, 4, 5 };
        y = new int[] { 0, 1, 1, 1 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(1, 1); 
                blocks[1].setRelativePosition(1, -1);
                blocks[3].setRelativePosition(-1, 1);
                break;
            case 1:
                blocks[0].setRelativePosition(-1, 1);
                blocks[1].setRelativePosition(1, 1);
                blocks[3].setRelativePosition(-1, -1);
                break;
            case 2:
                blocks[0].setRelativePosition(-1, -1); 
                blocks[1].setRelativePosition(-1, 1);
                blocks[3].setRelativePosition(1, -1);
                break;
            case 3:
                blocks[0].setRelativePosition(1, -1); 
                blocks[1].setRelativePosition(-1, -1);
                blocks[3].setRelativePosition(1, 1);
                break;
            default:
                break;
        }
        setNextRotation(4);
        return blocks;
    }
}
