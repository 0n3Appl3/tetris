import javax.swing.*;
import javax.sound.sampled.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class Window extends JPanel implements ActionListener {
    int _width, _height, paddX, paddY;
    int gameW = 250;
    int gameH = 450;
    int frameX = 10;
    int frameY = 18;

    int movementTimer = 0;
    int currentLevel = 0;
    int period = 100;

    int lineClearAlpha = 255;
    int lineClearY = 0;
    int lineClearSize = 0;

    boolean collisionFound = false;
    boolean placed = false;

    Timer timer = new Timer(33, this);
    Level level = null;

    Block[][] gameFrame = new Block[frameX][frameY];
    Block[] activeBlocks = new Block[4];
    Block lowestActiveBlock;

    Shape shape = null;
    Shape nextShape = null;

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
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "onSpace");

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
        am.put("onSpace", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (!placed) {
                    move(1, false, false);
                    checkPlaced();
                }
            }
        });

        level = new Level();
        _width = width;
        _height = height;
        paddX = _width / 4;
        paddY = 20;
        createTetromino();
        timer.start();
    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer)
            repaint();
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
        g.setFont(new Font("HelveticaNeue", Font.PLAIN, 14));
        g.drawString("NEXT", 25, paddY + 13);
        g.drawString("LEVEL", paddX + gameW + 20, paddY + 13);
        g.drawString("SCORE", paddX + gameW + 20, paddY + 70);
        g.setFont(new Font("HelveticaNeue-Bold", Font.PLAIN, 18));
        g.drawString(String.valueOf(level.getLevel()), paddX + gameW + 20, paddY + 40);
        g.drawString(String.format("%,.0f", (double)level.getScore()), paddX + gameW + 20, paddY + 97);

        if (nextShape != null)
            nextShape.previewShape(g);

        /*
         * Collision Checking
         */
        checkPlaced();

        /*
         * Movement
         */
        if (!placed) {
            if (movementTimer >= period) {
                move(1, false, false);
                movementTimer = 0;
            }
            movementTimer++;
        } else {
            placed = false;
            playSound("tetromino_placed.wav");
            for (Block b2 : activeBlocks)
                b2.setLanded(true);
            level.addScore(1);
            checkLineClearing();
        }

        /*
         * Draw
         */
        displayBlocks(g);
        if (lineClearY != 0 && lineClearAlpha > 0) {
            g.setColor(new Color(255, 255, 255, lineClearAlpha));
            g.fillRect(paddX, paddY + (lineClearY * 25), gameW, 25);
            lineClearAlpha -= 10;
        }
    }

    public void displayBlocks(Graphics g) {
        for (int i = 0; i < frameY; i++) {
            for (int j = 0; j < frameX; j++) {
                Block b = gameFrame[j][i];

                if (b != null)
                    b.draw(g, paddX, paddY);
            }
        }
    }

    public void checkPlaced() {
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
    }

    public void checkLineClearing() {
        boolean filled = true;
        boolean foundRow = false;
        int emptySlotsInRow = 0;
        int fullRowEmpty = 10;
        int topRow = 0;
        int clearRow = 0;

        for (int i = frameY - 1; i >= 0; i--) {
            filled = true;
            emptySlotsInRow = 0;
            for (int j = frameX - 1; j >= 0; j--) {
                Block b = gameFrame[j][i];
                if (b == null) {
                    filled = false;
                    emptySlotsInRow++;
                }
                if (emptySlotsInRow == fullRowEmpty) {
                    topRow = i + 1;
                    break;
                }
            }
            if (filled && !foundRow) {
                clearRow = i;
                foundRow = true;
            }
            if (emptySlotsInRow == fullRowEmpty)
                break;
        }

        if (foundRow) {
            lineClearAlpha = 255;
            lineClearY = clearRow;
            lineClearSize = gameFrame[0][lineClearY].getBlockSize();
            playSound("line_clear.wav");

            for (int k = 0; k < frameX; k++) {
                gameFrame[k][clearRow] = null;
            }
            for (int l = clearRow - 1; l >= topRow; l--) {
                for (int m = 0; m < frameX; m++) {
                    Block b2 = gameFrame[m][l];
                    if (b2 != null) {
                        b2.setFrameY(b2.getFrameY() + 1);
                        gameFrame[b2.getFrameX()][b2.getFrameY()] = b2;
                    } 
                    gameFrame[m][l] = null;
                }
            }
            level.addLinesCleared();
            level.addScore(10);
            checkLineClearing();
            return;
        }
        if (level.getLevel() > currentLevel) {
            playSound("level_up.wav");
            currentLevel = level.getLevel();
            if (period - 10 <= 0) {
                if (period - 1 > 0)
                    period--;
            } else {
               period -= 10;
            }
        }
        createTetromino();
    }

    public void move(int magnitude, boolean horizontal, boolean rotate) {
        Block[] temp = new Block[4];
        Block b2 = null;
        boolean validLeftRotation = false;
        boolean validRightRotation = false;

        if (horizontal) {
            for (Block b : activeBlocks) {
                if (b.getFrameX() + magnitude < 0 || b.getFrameX() + magnitude > frameX - 1)
                    return;
                b2 = gameFrame[b.getFrameX() + magnitude][b.getFrameY()];
                if (b2 != null && b2.hasLanded())
                    return;
            }
        }
        for (int i = 0; i < activeBlocks.length; i++) {
            Block tempB = activeBlocks[i];
            temp[i] = tempB;
            gameFrame[tempB.getFrameX()][tempB.getFrameY()] = null;
        }
        activeBlocks = temp;

        if (rotate) {
            activeBlocks = shape.rotate();
            while (!validLeftRotation && !validRightRotation) {
                validLeftRotation = true;
                validRightRotation = true;

                for (Block b : activeBlocks) {
                    if (b.getFrameX() < 0)
                        validLeftRotation = false;
                    else if (b.getFrameX() > 9)
                        validRightRotation = false;
                }
                if (!validLeftRotation || !validRightRotation) {
                    for (Block b : activeBlocks) {
                        if (!validLeftRotation)
                            b.setFrameX(b.getFrameX() + 1);
                        else
                            b.setFrameX(b.getFrameX() - 1);
                    }
                }
            }
        }

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
        if (shape == null)
            shape = pickNextTetromino();
        else
            shape = nextShape;
        nextShape = pickNextTetromino();
        activeBlocks = shape.draw();

    }

    public Shape pickNextTetromino() {
        Shape s = new Z();
        int rand = (int) (Math.random() * 8);

        switch (rand) {
            case 0:
                s = new I();
                break;
            case 1:
                s = new O();
                break;
            case 2:
                s = new T();
                break;
            case 3:
                s = new J();
                break;
            case 4:
                s = new L();
                break;
            case 5:
                s = new S();
                break;
            case 6:
            default:
                break;
        }
        return s;
    }

    public void playSound(String path) {
        try {
            File file = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
