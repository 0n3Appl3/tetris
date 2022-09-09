import java.awt.*;

public class S extends Shape {
    public S() {
        color = new Color(47, 67, 168);
        x = new int[] { 4, 5, 4, 3 };
        y = new int[] { 0, 0, 1, 1 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(1, 1); 
                blocks[1].setRelativePosition(0, 2);
                blocks[3].setRelativePosition(1, -1);
                break;
            case 1:
                blocks[0].setRelativePosition(-1, -1);
                blocks[1].setRelativePosition(0, -2);
                blocks[3].setRelativePosition(-1, 1);
                break;
            default:
                break;
        }
        setNextRotation(2);
        return blocks;
    }
}
