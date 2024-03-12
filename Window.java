import javax.swing.*;
import javax.sound.sampled.*;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Window extends JPanel implements ActionListener {
    private int _width, _height, paddX, paddY;
    private static int gameW = 250;
    private static int gameH = 450;
    private static int frameX = 10;
    private static int frameY = 18;

    private int movementTimer = 0;
    private int currentLevel = 0;
    private int period = 80;

    private int lineClearAlpha = 255;
    private ArrayList<Integer> lineClearY = new ArrayList<Integer>();
    private int consecutiveLines = 0;

    private int praiseTextAlpha = 255;
    
    private boolean placed = false;
    private boolean gameOver = false;
    private boolean gameOverSoundPlayed = false;

    private Timer timer = new Timer(33, this);
    private Level level = null;

    private Block[][] gameFrame = new Block[frameX][frameY];
    private Block[] activeBlocks = new Block[4];

    private Shape shape = null;
    private Shape nextShape = null;

    private static String gameOverLabel = "GAME OVER";
    private static String nextShapeLabel = "NEXT";
    private static String levelLabel = "LEVEL";
    private static String scoreLabel = "SCORE";
    private String praiseText = "";

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

        // Initialise the game.
        level = new Level();
        _width = width;
        _height = height;
        paddX = _width / 2 - (gameW / 2);
        paddY = 20;
        createTetromino();
        timer.start();
        playSound("music/music.wav", -17, true);
    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer)
            repaint();
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        /*
         * Game Frame
         */
        try {
            BufferedImage image = ImageIO.read(new File("textures/background.png"));
            g.drawImage(image, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.setColor(Color.CYAN);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(paddX, paddY, gameW, gameH);
        g2.setColor(Color.WHITE);
        g.setFont(new Font("HelveticaNeue", Font.PLAIN, 14));
        g.drawString(nextShapeLabel, 25, paddY + 13);
        g.drawString(levelLabel, paddX + gameW + 20, paddY + 13);
        g.drawString(scoreLabel, paddX + gameW + 20, paddY + 70);
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
        if (!gameOver) {
            if (!placed) {
                if (movementTimer >= period) {
                    move(1, false, false);
                    movementTimer = 0;
                }
                move(0, false, false);
                movementTimer++;
            } else {
                placed = false;
                playSound("sounds/tetromino_placed.wav", 1);
                for (Block b2 : activeBlocks)
                    b2.setLanded(true);
                level.addScore(1);
                checkLineClearing();
            }
        }

        /*
         * Draw blocks, line clear animation and check game over condition.
         */
        displayBlocks(g);
        if (lineClearY.size() != 0 && lineClearAlpha > 0) {
            g.setColor(new Color(255, 255, 255, lineClearAlpha));
            for (int line : lineClearY) {
                g.fillRect(paddX, paddY + (line * 25), gameW, 25);
            }
            lineClearAlpha -= 20;
        }
        if (lineClearAlpha <= 0)
            lineClearY = new ArrayList<Integer>();
        if (praiseTextAlpha > 0) {
            g.setColor(new Color(255, 255, 255, praiseTextAlpha));
            g.setFont(new Font("HelveticaNeue-Bold", Font.PLAIN, 24));
            g.drawString(praiseText, _width / 2 - g.getFontMetrics().stringWidth(praiseText) / 2, _height / 5);
            praiseTextAlpha -= 5;
        }
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("HelveticaNeue-Bold", Font.PLAIN, 24));
            g.drawString(gameOverLabel, _width / 2 - g.getFontMetrics().stringWidth(gameOverLabel) / 2, _height / 2);
            if (!gameOverSoundPlayed) {
                playSound("sounds/game_over.wav", 1);
                gameOverSoundPlayed = true;
            }
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

        // Attempt a full search to identify a full row of blocks.
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
        // Clear line as an entire row contains blocks.
        if (foundRow) {
            lineClearAlpha = 255;
            lineClearY.add(clearRow);
            if (consecutiveLines < 1)
                playSound("sounds/line_clear.wav", -2);

            for (int k = 0; k < frameX; k++)
                gameFrame[k][clearRow] = null;
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
            consecutiveLines++;
            if (consecutiveLines == 4)
                playRandomVoiceSound();
            checkLineClearing();
            return;
        }
        // Check if player has scored high enough to level up.
        if (level.getLevel() > currentLevel) {
            playSound("sounds/level_up.wav", -2);
            playRandomVoiceSound();
            currentLevel = level.getLevel();
            if (period - 10 <= 0) {
                if (period - 1 > 0)
                    period--;
            } else {
               period -= 10;
            }
        }
        consecutiveLines = 0;
        createTetromino();
    }

    public void move(int magnitude, boolean horizontal, boolean rotate) {
        Block[] temp = new Block[4];
        Block b2 = null;
        boolean validLeftRotation = false;
        boolean validRightRotation = false;

        // Has the player moved their shape horizontally.
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

        // Has the player rotated their shape.
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
        // Move the block.
        for (Block b : activeBlocks) {
            if (!rotate) {
                if (horizontal)
                    b.setFrameX(b.getFrameX() + magnitude);
                else if (magnitude > 0)
                    b.setFrameY(b.getFrameY() + 1);
            }
            gameFrame[b.getFrameX()][b.getFrameY()] = b;
        }
        shape.saveState(activeBlocks);
    }

    public void createTetromino() {
        shape = shape == null ? pickNextTetromino() : nextShape;
        nextShape = pickNextTetromino();
        activeBlocks = shape.draw();
        // Check if any of the new blocks would spawn inside a placed shape. If so, game over.
        for (int i = 0; i < activeBlocks.length; i++) {
            Block block = activeBlocks[i];
            if (gameFrame[block.getFrameX()][block.getFrameY()] != null) {
                gameOver = true;
                break;
            }
        }
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
            default:
                break;
        }
        return s;
    }

    public void playRandomVoiceSound() {
        int rand = (int) (Math.random() * 3);
        praiseTextAlpha = 255;

        switch (rand) {
            case 0:
                playSound("sounds/voice1.wav");
                praiseText = "EXCELLENT";
                break;
            case 1:
                playSound("sounds/voice2.wav");
                praiseText = "SPECTACULAR";
                break;
            case 2:
                playSound("sounds/voice3.wav");
                praiseText = "WELL DONE";
                break;
            case 3:
                playSound("sounds/voice4.wav");
                praiseText = "IMPRESSIVE";
                break;
            default:
                break;
        }
    }

    public void playSound(String path, float gain, boolean loop) {
        try {
            // Get audio file.
            File file = new File(path);
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip.open(audioStream);
            // Set volume.
            if (gain != 0) {
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(volume.getValue() + gain);
            }
            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            // Play audio.
            clip.start();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void playSound(String path) {
        playSound(path, 0);
    }

    public void playSound(String path, float gain) {
        playSound(path, gain, false);
    }
}
