package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {

	private ArrayList<Block> snake = new ArrayList<>();
	
	Random rand = new Random();
	
	private boolean up_pressed = false;
	private boolean down_pressed = false;
	private boolean left_pressed = false;
	private boolean right_pressed = false;
	
	private boolean snake_eaten = true;
	private int apple_xpos = -1;
	private int apple_ypos = -1;
	
	private Timer timer;
	
	private boolean game_over = false;
	private boolean isPlaying = false;
	
	public Game() {
		// enable keypad inputs
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		// initialize timer
		timer = new Timer(5, this);
		timer.start();
		snake.add(new Block(375, 275));
		snake.add(new Block(360, 275));
		snake.add(new Block(345, 275));
	}
	
	public void paint(Graphics g) {
		if (game_over) {
			isPlaying = false;
			g.setColor(Color.RED);
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			g.drawString("GAME OVER!", 270, 310);
			g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
			g.drawString("Press Enter to Play Again", 300, 330);
		} else {
			// fill background of game window
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);

			// fill borders of game window
			g.setColor(Color.MAGENTA);
			g.fillRect(0, 0, 5, 600);
			g.fillRect(0, 0, 800, 5);
			g.fillRect(780, 0, 5, 600);
			g.fillRect(0, 558, 800, 5);
			
			for(int i = 0; i < snake.size(); i++) {
				g.setColor(snake.get(i).getColor());
				g.fillRect(snake.get(i).getXpos(), 
						   snake.get(i).getYpos(), 
						   snake.get(i).getWidth(), 
						   snake.get(i).getHeight());
				wait(10);
			}
			
			if (apple_xpos != -1 && apple_ypos != -1) {
				g.setColor(Color.RED);
				g.fillRect(apple_xpos, apple_ypos, 10, 10);
			}
		}
		// wait(10);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// get the code of the key pressed
		int key_code = e.getKeyCode();

		if (key_code == KeyEvent.VK_UP && !down_pressed) {
			up_pressed = true;
			isPlaying = true;
			down_pressed = false;
			left_pressed = false;
			right_pressed = false;
		}

		if (key_code == KeyEvent.VK_DOWN && !up_pressed) {
			down_pressed = true;
			isPlaying = true;
			up_pressed = false;
			left_pressed = false;
			right_pressed = false;
		}
		
		if (key_code == KeyEvent.VK_LEFT && !right_pressed) {
			left_pressed = true;
			isPlaying = true;
			right_pressed = false;
			up_pressed = false;
			down_pressed = false;
		}
		
		if (key_code == KeyEvent.VK_RIGHT && !left_pressed) {
			right_pressed = true;
			isPlaying = true;
			up_pressed = false;
			down_pressed = false;
			left_pressed = false;
		}
		
		if (game_over && key_code == KeyEvent.VK_ENTER) {
			isPlaying = false;
			game_over = false;
			snake.clear();
			snake.add(new Block(375, 275));
			snake.add(new Block(360, 275));
			snake.add(new Block(345, 275));
			up_pressed = false;
			down_pressed = false;
			left_pressed = false;
			right_pressed = false;
			apple_xpos = -1;
			apple_ypos = -1;
			snake_eaten = true;
		}
	}
	
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	public void checkCollisionWall() {
		Rectangle snake_head = new Rectangle(snake.get(0).getXpos(),
											 snake.get(0).getYpos(),
											 snake.get(0).getWidth(),
											 snake.get(0).getHeight());
		Rectangle wall_one = new Rectangle(0, 0, 5, 600);
		Rectangle wall_two = new Rectangle(0, 1, 800, 5);
		Rectangle wall_three = new Rectangle(780, 0, 5, 600);
		Rectangle wall_four = new Rectangle(0, 558, 800, 5);
				
		if (snake_head.intersects(wall_one) || snake_head.intersects(wall_two) || snake_head.intersects(wall_three) || snake_head.intersects(wall_four)) {
			game_over = true;
		}
	}
	
	public void checkCollisionSnake() {
		Rectangle snake_head = new Rectangle(snake.get(0).getXpos(),
				 snake.get(0).getYpos(),
				 snake.get(0).getWidth(),
				 snake.get(0).getHeight());
		for (int i = 1; i < snake.size(); i++) {
			Rectangle snake_block = new Rectangle(snake.get(i).getXpos(),
					 							  snake.get(i).getYpos(),
					 							  snake.get(i).getWidth(),
					 							  snake.get(i).getHeight());
			if (snake_head.intersects(snake_block)) {
				game_over = true;
				break;
			}
		}
	}
	
	public void updateSnake() {
		if (left_pressed) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setXpos(snake.get(i - 1).getXpos());
				snake.get(i).setYpos(snake.get(i - 1).getYpos());
			}
			snake.get(0).setXpos(snake.get(0).getXpos() - 15);
			right_pressed = false;
			up_pressed = false;
			down_pressed = false;
		} else if (right_pressed) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setXpos(snake.get(i - 1).getXpos());
				snake.get(i).setYpos(snake.get(i - 1).getYpos());
			}
			snake.get(0).setXpos(snake.get(0).getXpos() + 15);
			left_pressed = false;
			up_pressed = false;
			down_pressed = false;
		} else if (up_pressed) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setXpos(snake.get(i - 1).getXpos());
				snake.get(i).setYpos(snake.get(i - 1).getYpos());
			}
			snake.get(0).setYpos(snake.get(0).getYpos() - 15);
			left_pressed = false;
			right_pressed = false;
			down_pressed = false;
		} else if (down_pressed) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setXpos(snake.get(i - 1).getXpos());
				snake.get(i).setYpos(snake.get(i - 1).getYpos());
			}
			snake.get(0).setYpos(snake.get(0).getYpos() + 15);
			left_pressed = false;
			right_pressed = false;
			up_pressed = false;
		}
	}
	
	public void generateApple() {
		apple_xpos = (rand.nextInt(50) * 15) + 15;
		apple_ypos = (rand.nextInt(34) * 15) + 20;
	}
	
	public int comparePos() {
		Block penultimate = snake.get(snake.size() - 2);
		Block last = snake.get(snake.size() - 1);
		
		if (penultimate.getXpos() > last.getXpos()) {
			// snake moving right
			return 0;
		} else if (penultimate.getXpos() < last.getXpos()) {
			// snake moving left
			return 1;
		} else if (penultimate.getYpos() > last.getYpos()) {
			// snake moving down
			return 2;
		} else {
			// snake moving up
			return 3;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (isPlaying) {
			updateSnake();
			checkCollisionWall();
			checkCollisionSnake();
			if (snake_eaten) {
				snake_eaten = false;
				generateApple();
			}
			Rectangle rectApple = new Rectangle(apple_xpos, apple_ypos, 10, 10);
			Rectangle snake_head = new Rectangle(snake.get(0).getXpos(),
					 							 snake.get(0).getYpos(),
					 							 snake.get(0).getWidth(),
					 							 snake.get(0).getHeight());
			if (snake_head.intersects(rectApple)) {
				snake_eaten = true;
				Block last = snake.get(snake.size() - 1);
				int comp = comparePos();
				if (comp == 0) {
					Block new_tail = new Block(last.getXpos() - 15, last.getYpos());
					snake.add(new_tail);
				} else if (comp == 1) {
					Block new_tail = new Block(last.getXpos() + 15, last.getYpos());
					snake.add(new_tail);
				} else if (comp == 2) {
					Block new_tail = new Block(last.getXpos(), last.getYpos() - 15);
					snake.add(new_tail);
				} else {
					Block new_tail = new Block(last.getXpos(), last.getYpos() + 15);
					snake.add(new_tail);
				}
			}
		}
		repaint();
	}
	
	// Unused Methods
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}

}
