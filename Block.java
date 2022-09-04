import javax.swing.*;
import java.awt.*;

public class Block extends JComponent {
    Color _color;
    int size = 25;
    int _frameX, _frameY;
    boolean _landed = false;

    public Block(int frameX, int frameY, Color color) {
        _frameX = frameX;
        _frameY = frameY;
        _color = color;
    }

    public void draw(Graphics g, int x, int y) {
        g.setColor(_color);
        g.fillRect(x + (_frameX * size), y + (_frameY * size), size, size);
    }

    public int getFrameX() {
        return _frameX;
    }

    public int getFrameY() {
        return _frameY;
    }

    public boolean hasLanded() {
        return _landed;
    }

    public void setFrameX(int frameX) {
        _frameX = frameX;
    }

    public void setFrameY(int frameY) {
        _frameY = frameY;
    }

    public void setLanded(boolean landed) {
        _landed = landed;
    }
}
