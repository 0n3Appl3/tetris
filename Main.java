import javax.swing.*;

public class Main {
    static int width = 550;
    static int height = 525;
    static int mode = 0;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main <classic/pentix>");
            return;
        }
        switch (args[0]) {
            case "classic":
                mode = 1;
                break;
            case "pentix":
                mode = 2;
                break;
            default:
                System.out.println("Only classic and pentix modes available!");
                return;
        }
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.getContentPane().add(new Window(width, height, mode));
        frame.setVisible(true);
    }
}