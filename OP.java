import java.awt.*;

public class OP extends Shape {
    public OP() {
        color = new Color(169, 212, 28);
        x = new int[] { 4, 5, 4, 5, 4 };
        y = new int[] { 1, 1, 2, 2, 3 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[4].setRelativePosition(-1, -2);
                break;
            case 1:
                blocks[4].setRelativePosition(2, -1);
                break;
            case 2:
                blocks[4].setRelativePosition(1, 2);
                break;
            case 3:
                blocks[4].setRelativePosition(-2, 1);
                break;
            default:
                break;
        }
        setNextRotation(4);
        return blocks;
    }
}
