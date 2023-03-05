import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MovingObjects extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private ArrayList<MovingCircle> circles;
    private MovingCircle activeCircle;
    private int activeCircleIndex;
    private boolean isDragging;
    private int mouseX, mouseY;

    public MovingObjects() {
        circles = new ArrayList<>();
        // create 5 circles with random positions and speeds
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int x = rand.nextInt(450);
            int y = rand.nextInt(450);
            int vx = rand.nextInt(5) + 1;
            int vy = rand.nextInt(5) + 1;
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            MovingCircle circle = new MovingCircle(x, y, 50, vx, vy, color);
            circles.add(circle);
        }
        activeCircleIndex = 0;
        activeCircle = circles.get(activeCircleIndex);
        isDragging = false;
        mouseX = 0;
        mouseY = 0;
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (MovingCircle circle : circles) {
            circle.draw(g);
        }
    }

    public void move() {
        for (MovingCircle circle : circles) {
            circle.move(getWidth(), getHeight());
            // check collision with other circles
            for (MovingCircle other : circles) {
                if (circle != other && circle.collidesWith(other)) {
                    circle.changeDirection();
                    other.changeDirection();
                }
            }
        }
    }

    public void setActiveCircle(int index) {
        activeCircleIndex = index;
        activeCircle = circles.get(activeCircleIndex);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            activeCircle.setVx(-5);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            activeCircle.setVx(5);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            activeCircle.setVy(-5);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            activeCircle.setVy(5);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            activeCircle.setVx(0);
        } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            activeCircle.setVy(0);
        }
    }

    public void mouseClicked(MouseEvent e) {
        // find the clicked circle
        int clickedIndex = -1;
        for (int i = 0; i < circles.size(); i++) {
            MovingCircle circle = circles.get(i);
            if (circle.contains(e.getX(), e.getY())) {
                clickedIndex = i;
                break;
            }
        }
        if (clickedIndex != -1) {
            setActiveCircle(clickedIndex);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (activeCircle.contains(e.getX(), e.getY())) {
            isDragging = true;
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e) {
        isDragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        if (isDragging) {
            int dx = e.getX() - mouseX;
            int dy = e.getY() - mouseY;
            activeCircle.move(dx, dy, getWidth(), getHeight());
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Moving Objects");
        MovingObjects panel = new MovingObjects();
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            panel.move();
            panel.repaint();
            Thread.sleep(10);
        }
    }
}


