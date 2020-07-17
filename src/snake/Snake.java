package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.JFrame;

public class Snake implements ActionListener, KeyListener {

	public JFrame jframe;
	public RenderPanel renderPanel;
	public Timer timer = new Timer(20, this);
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	public int ticks = 0, direction = DOWN, score, tailLength = 10, time;
	public Random random;
	public static Snake snake;
	public Point head, cherry;
	public Dimension dim;
	public boolean over = false, paused;

	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(805, 700);
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startgane();
	}

	public void startgane() {

		over = false;
		paused = false;
		ticks = 0;
		direction = DOWN;
		tailLength = 5;
		time = 0;
		score = 0;
		head = new Point(0, -1);
		random = new Random();
		snakeParts.clear();
		cherry = new Point(random.nextInt(79), random.nextInt(66));
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		renderPanel.repaint();
		ticks++;
		if (ticks % 5 == 0 && head != null && !over && !paused) {
			time++;
			snakeParts.add(new Point(head.x, head.y));

			if (direction == UP)
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
					head = new Point(head.x, head.y - 1);
				else
					over = true;

			if (direction == DOWN)
				if (head.y + 1 < 67 && noTailAt(head.x, head.y + 1))
					head = new Point(head.x, head.y + 1);
				else
					over = true;

			if (direction == LEFT)
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
					head = new Point(head.x - 1, head.y);
				else
					over = true;

			if (direction == RIGHT)
				if (head.x + 1 < 80 && noTailAt(head.x + 1, head.y))
					head = new Point(head.x + 1, head.y);
				else
					over = true;
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}

			if (cherry != null) {
				if (head.equals(cherry)) {
					score += 5;
					tailLength++;
					cherry.setLocation(random.nextInt(79), random.nextInt(66));
				}
			}
		}

	}

	private boolean noTailAt(int x, int i) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		snake = new Snake();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT && direction != RIGHT) {
			direction = LEFT;
		}
		if (i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT && direction != LEFT) {
			direction = RIGHT;
		}
		if (i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN && direction != UP) {
			direction = DOWN;
		}
		if (i == KeyEvent.VK_W || i == KeyEvent.VK_UP && direction != DOWN) {
			direction = UP;
		}
		if (i == KeyEvent.VK_SPACE) {
			if (over) {
				startgane();
			} else {
				paused = !paused;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
