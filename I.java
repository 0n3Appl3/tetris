import java.awt.*;

public class I extends Shape {
    public I() {
        color = new Color(222, 44, 44);
        x = new int[] { 4, 4, 4, 4 };
        y = new int[] { 0, 1, 2, 3 };
    }

    @Override
    public Block[] rotate() {
        switch (index) {
            case 0:
                blocks[0].setRelativePosition(-1, 1); 
                blocks[2].setRelativePosition(1, -1);
                blocks[3].setRelativePosition(2, -2);
                break;
            case 1:
                blocks[0].setRelativePosition(1, -1);
                blocks[2].setRelativePosition(-1, 1);
                blocks[3].setRelativePosition(-2, 2);
                break;
            default:
                break;
        }
        setNextRotation(2);
        return blocks;
    }
}
