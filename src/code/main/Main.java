package code.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Main extends AbGame{

	private Input input;

	private Bird bird = new Bird();
	private ArrayList<Pipe> pipes = new ArrayList<Pipe>();

	private int frames = 0;
	private int pipeGapFrames = 240;
	
	@Override
	public void setup() {
		
	}
	
	@Override
	public void update() {

		if(frames%100 == 0){
			pipes.add(new Pipe());
		}

		if(input.justPressed(KeyEvent.VK_SPACE)){
			System.out.println("space pressed");
			// System.out.println(pipes.size());
			bird.jump();
		}

		bird.update();

		for(Pipe p : pipes){
			p.update();
		}

		for(Pipe p:pipes){
			if(p.hit(bird)){
				System.out.println("hit");
			}
		}

		//remove out-of-screen pipes
		ArrayList<Pipe> removePipes = new ArrayList<Pipe>();
		for(Pipe p : pipes){
			if(p.screenExit()){
				removePipes.add(p);
			}
		}
		for(Pipe p:removePipes){
			pipes.remove(p);
		}
		removePipes.clear();
		//

		
		frames++;
	}
	
	@Override
	public void render(Graphics g) {
		bird.render(g);
		for(Pipe p : pipes){
			p.render(g);
		}
	}
	
	


	public Main(){
		GameThread gameThread = new GameThread(this,720,480);
		input = gameThread.getInput();
		gameThread.start();
	}
	public static void main(String[] args){
		new Main();
	}

}