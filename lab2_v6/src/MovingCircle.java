import java.awt.*;

public class MovingCircle {
    private int x, y, r, vx, vy;
    private Color color;

    public MovingCircle(int x, int y, int r, int vx, int vy, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
    }

    public void move(int maxX, int maxY) {
        x += vx;
        y += vy;
        // check boundary
        if (x - r < 0) {
            x = r;
            vx = -vx;
            changeColor();
        } else if (x + r > maxX) {
            x = maxX - r;
            vx = -vx;
            changeColor();
        }
        if (y - r < 0) {
            y = r;
            vy = -vy;
            changeColor();
        } else if (y + r > maxY) {
            y = maxY - r;
            vy = -vy;
            changeColor();
        }
    }

    public void move(int dx, int dy, int maxX, int maxY) {
        x += dx;
        y += dy;
        // check boundary
        if (x - r < 0) {
            x = r;
        } else if (x + r > maxX) {
            x = maxX - r;
        }
        if (y - r < 0) {
            y = r;
        } else if (y + r > maxY) {
            y = maxY - r;
        }
    }

    public boolean collidesWith(MovingCircle other) {
        int dx = x - other.x;
        int dy = y - other.y;
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        return distance <= r + other.r;
    }

    public void changeColor() {
        color = new Color((color.getRed() + 50) % 256, (color.getGreen() + 50) % 256, (color.getBlue() + 50) % 256);
    }

    public void changeDirection() {
        vx = -vx;
        vy = -vy;
        changeColor();
    }

    public boolean contains(int px, int py) {
        int dx = x - px;
        int dy = y - py;
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        return distance <= r;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
}
