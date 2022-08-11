import jaco.mp3.player.MP3Player;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 100;
    static int[] x = new int[GAME_UNITS];
    static int[] y = new int[GAME_UNITS];
    static final int INITIAL_BODY_PARTS = 6;
    int bodyParts = INITIAL_BODY_PARTS;
    int applesEaten = 0;
    int appleX;
    int appleY;
    boolean running = false;
    boolean hasTimer = false;
    Random random;
    Timer timer;
    static final char INITIAL_DIRECTION = 'D';
    char direction = INITIAL_DIRECTION;

    GamePanel () {
        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        createApple();

        applesEaten = 0;

        x = new int[GAME_UNITS];
        y = new int[GAME_UNITS];

        direction = 'D';

        bodyParts = INITIAL_BODY_PARTS;

        running = true;

        if(!hasTimer) {
            timer = new Timer(DELAY, this);
            hasTimer = true;
        }

        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void createApple () {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void draw(Graphics g) {
        if(running) {
            for(int i = 0; i < SCREEN_HEIGHT; i++) {
                g.drawLine(i*UNIT_SIZE,0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            g.setColor(Color.red);
            g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 100, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void move() {
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'W':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'S':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'A':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        for(int i = bodyParts; i > 0; i--) {
            if(x[i] == appleX && y[i] == appleY) {
                createApple();
                bodyParts++;
                applesEaten++;
            }
        }
    }

    public void checkCollision () {
        for(int i = bodyParts; i>0; i--) {
            if ((x[0]) == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }

        if (x[0] < 0) {
            running = false;
        }

        if(x[0] >= SCREEN_WIDTH) {
            running = false;
        }

        if (y[0] < 0) {
            running = false;
        }

        if(y[0] >= SCREEN_HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - fontMetrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT / 2);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten))/2, SCREEN_HEIGHT / 2 + g.getFont().getSize() + 45);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Press R to restart the game!", (SCREEN_WIDTH - fontMetrics.stringWidth("Press R to restart the game!"))/2, SCREEN_HEIGHT / 2 + g.getFont().getSize() + 90);
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed (KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_R:
                    if(!running) {
                        startGame();
                    }
                    break;
                case KeyEvent.VK_A:
                    if(direction != 'D') {
                        direction = 'A';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'A') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'W') {
                        direction = 'S';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'S') {
                        direction = 'W';
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
}
