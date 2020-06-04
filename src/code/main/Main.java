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
	private int population = 500;
	private ArrayList<Bird> birds = new ArrayList<Bird>();
	private ArrayList<Pipe> pipes = new ArrayList<Pipe>();

	private int frames = 0;
	private int pipeGapFrames = (int)Math.random()*(240-100)+100;

	private int Gens;
	private ArrayList<Bird> saved = new ArrayList<Bird>();

	private int loops = 1;
	
	@Override
	public void setup() {
		for (int i=0; i<population; i++){
			birds.add(new Bird());
		}
	}
	
	@Override
	public void update() {
		for(int i=loops;i>=0;i--){

			if(pipeGapFrames == 0){
				pipes.add(new Pipe());
				pipeGapFrames = (int)(Math.random()*200)+50;
				//pipeGapFrames = 90;
			}
	
			if(input.justPressed(KeyEvent.VK_SPACE)){
				//System.out.println("space pressed");
				// System.out.println(pipes.size());
				// System.out.println(pipeGapFrames);
				// bird.jump();
			}
	

	
			//jump or not
			for(Bird b:birds){
				b.think(pipes);
				
			}
			//
	
	
			for(Bird b:birds){
				b.update();
			}
	
			for(Pipe p : pipes){
				p.update();
			}
	
			//birds hit pipes
			ArrayList<Bird> removeBirds = new ArrayList<Bird>();
			for(Pipe p:pipes){
				for(Bird b:birds){
					if(p.hit(b)){
						removeBirds.add(b);
						saved.add(b);
					}
				}
			}
			for(Bird b:removeBirds){
				birds.remove(b);
			}
			removeBirds.clear();
			//
	
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
	
			//?restart game
			if(birds.size()==0){
				pipes.clear();
				pipeGapFrames = (int)(Math.random()*(200-90))+90;
	
				//birds.clear();
				nextGeneration();
	
			}
			//
			pipeGapFrames--;

		}
		if(input.justPressed(KeyEvent.VK_LEFT)){
			loops = Math.max(1, loops/2);
			System.out.println("loops: "+ loops);
			// Bird tb = new Bird();
			// Pipe tp = tb.closestPipe(pipes);
			// System.out.println(tp.xpos);
		}
		if(input.justPressed(KeyEvent.VK_RIGHT)){
			loops = loops*2;
			System.out.println("loops: "+ loops);
		}
	}
	
	@Override
	public void render(Graphics g) {

		for(int i=birds.size()-1;i>=0;i--){
			birds.get(i).render(g);
		}

		for(int i=pipes.size()-1;i>=0;i--){
			pipes.get(i).render(g);
		}
	}

	private void nextGeneration(){
		Gens++;
		System.out.println("Generation "+ Gens);
		birds.add(saved.get(saved.size()-1));
		calculateFitness();
		for (int i = 0; i < population-1; i++){
		  birds.add(pickOne());
		}
		saved.clear();
	}
	  
	private Bird pickOne(){
		int index = 0;
		double r = Math.random();
		while (r > 0) {
			r -= saved.get(index).fitness;
			index++;
		}
		index--;
		Bird b = saved.get(index);
		Bird child = new Bird(b.brain);
		child.mutate();
		return child;
	}
	  
	private void calculateFitness(){
		float sum = 0;
		for (Bird b : saved){
		  sum += b.score;
		}
	  
		for (Bird b : saved){
		  b.fitness = b.score/sum;
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