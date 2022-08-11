import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    static int x[] = new int[GAME_UNITS];
    static int y[] = new int[GAME_UNITS];

    GamePanel () {

    }

    public void startGame(){}

    public void paintComponent(Graphics g) {}

    public void draw(Graphics g) {}

    public void move() {}

    public void checkApple() {}

    public void checkCollision () {}

    public void gameOver(Graphics g) {}

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed (KeyEvent e) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
