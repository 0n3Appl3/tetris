import java.awt.*;

public class O extends Shape {
    public O() {
        color = new Color(227, 211, 36);
        x = new int[] { 4, 5, 4, 5 };
        y = new int[] { 0, 0, 1, 1 };
    }

    @Override
    public Block[] rotate() {
        return blocks;
    }
}
