import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Window extends JPanel {
    int _width, _height, paddX, paddY;
    int gameW = 250;
    int gameH = 450;
    int frameX = 10;
    int frameY = 18;
    int movementTimer = 0;
    boolean collisionFound = false;
    boolean placed = false;

    Block[][] gameFrame = new Block[frameX][frameY];
    Block[] activeBlocks = new Block[4];
    Block lowestActiveBlock;

    Shape shape;

    public Window(int width, int height) {
        InputMap im = getInputMap(WHEN_FOCUSED);
        ActionMap am = getActionMap();

        /*
         * Assigning KeyStroke events
         */
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "onLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "onRight");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "onDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "onUp");

        /*
         * Key Events (Player Controls)
         */
        am.put("onLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(-1, true, false);
            }
        });
        am.put("onRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(1, true, false);
            }
        });
        am.put("onDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(1, false, false);
            }
        });
        am.put("onUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(0, false, true);
            }
        });

        _width = width;
        _height = height;
        paddX = _width / 4;
        paddY = 20;
        createTetromino();
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Stroke s = g2.getStroke();

        /*
         * Game Frame
         */
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, _width, _height);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(paddX, paddY, gameW, gameH);
        g.drawRect(15, paddY, 100, 200);
        g.setFont(new Font("Helvetica", Font.PLAIN, 14));
        g.drawString("Work In Progress", paddX + gameW + 20, paddY + 20);

        /*
         * Collision Checking
         */
        for (int i = 0; i < activeBlocks.length; i++) {
            Block block = activeBlocks[i];
            if (block.getFrameY() + 1 > frameY - 1) {
                placed = true;
                break;
            }
            if (gameFrame[block.getFrameX()][block.getFrameY() + 1] != null) {
                Block blockBelow = gameFrame[block.getFrameX()][block.getFrameY() + 1];

                if (blockBelow.hasLanded()) {
                    placed = true;
                    break;
                }
            }
        }

        /*
         * Movement
         */
        if (!placed) {
            if (movementTimer == 1000) {
                move(1, false, false);
                movementTimer = 0;
            }
            movementTimer++;
        } else {
            placed = false;
            for (Block b2 : activeBlocks)
                b2.setLanded(true);
            createTetromino();
        }

        /*
         * Draw
         */
        // System.out.println("-----");
        // String t = "";
        for (int i = 0; i < frameY; i++) {
            // t = "";
            for (int j = 0; j < frameX; j++) {
                Block b = gameFrame[j][i];

                if (b != null) {
                    b.draw(g, paddX, paddY);
                    // t += "1";
                } else {
                    // t += "0";
                }
            }
            // System.out.println(t);
        }
    }

    public void move(int magnitude, boolean horizontal, boolean rotate) {
        Block[] temp = new Block[4];

        if (horizontal) {
            for (Block b : activeBlocks) {
                if (b.getFrameX() + magnitude < 0 || b.getFrameX() + magnitude > frameX - 1)
                    return;
            }
        }
        for (int i = 0; i < activeBlocks.length; i++) {
            Block tempB = activeBlocks[i];
            temp[i] = tempB;
            gameFrame[tempB.getFrameX()][tempB.getFrameY()] = null;
        }
        activeBlocks = temp;

        if (rotate)
            activeBlocks = shape.rotate();

        for (Block b : activeBlocks) {
            if (!rotate) {
                if (horizontal)
                    b.setFrameX(b.getFrameX() + magnitude);
                else
                    b.setFrameY(b.getFrameY() + 1);
            }
            gameFrame[b.getFrameX()][b.getFrameY()] = b;
        }
        shape.saveState(activeBlocks);
    }

    public void createTetromino() {
        shape = new Z();
        int rand = (int) (Math.random() * 8);

        switch (rand) {
            case 0:
                shape = new I();
                break;
            case 1:
                shape= new O();
                break;
            case 2:
                shape = new T();
                break;
            case 3:
                shape = new J();
                break;
            case 4:
                shape = new L();
                break;
            case 5:
                shape = new S();
                break;
            case 6:
            default:
                break;
        }
        activeBlocks = shape.draw();
    }
}
