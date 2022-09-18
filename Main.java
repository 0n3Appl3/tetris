import javax.swing.*;

public class Main {
    static int width = 550;
    static int height = 525;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.getContentPane().add(new Window(width, height));
        frame.setVisible(true);
        while (true) {
            frame.repaint();
        }
    }
}