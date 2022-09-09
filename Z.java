import java.awt.*;

public class Z extends Shape {
    public Z() {
        color = new Color(86, 204, 57);
        x = new int[] { 4, 3, 4, 5 };
        y = new int[] { 0, 0, 1, 1 };
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
