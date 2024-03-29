import java.awt.*;

public class IP extends Shape {
    public IP() {
        color = new Color(28, 138, 97);
        x = new int[] { 4, 4, 4, 4, 4 };
        y = new int[] { 0, 1, 2, 3, 4 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(2, 2); 
                blocks[1].setRelativePosition(1, 1);
                blocks[3].setRelativePosition(-1, -1);
                blocks[4].setRelativePosition(-2, -2);
                break;
            case 1:
                blocks[0].setRelativePosition(-2, -2); 
                blocks[1].setRelativePosition(-1, -1);
                blocks[3].setRelativePosition(1, 1);
                blocks[4].setRelativePosition(2, 2);
                break;
            default:
                break;
        }
        setNextRotation(2);
        return blocks;
    }
}
