import javax.swing.*;
import java.awt.*;

public class Block extends JComponent {
    private Color _color;
    private int size = 25;
    private int _frameX, _frameY;
    private boolean _landed = false;

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

    public Color getColor() {
        return _color;
    }

    public int getBlockSize() {
        return size;
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

    public void setRelativePosition(int x, int y) {
        _frameX += x;
        _frameY += y;
    }
}
