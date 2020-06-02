package code.main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class GameThread extends JPanel implements Runnable{

	private AbGame abGame;
	private Input input;

    private Thread thread;
    private float frameCap = 1.0f/120.0f;
    private float SECOND = 1000000000.0f;

    public GameThread(AbGame ab){
        abGame = ab;
		thread = new Thread(this);
		input = new Input(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	public GameThread(AbGame ab, int wid, int hig){
		this(ab);
		this.setPreferredSize(new Dimension(wid, hig));
		JFrame frame = new JFrame("3cube");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		abGame.render(g);
	}

    public void start(){
        abGame.setup();
		thread.start();
		System.out.println("still alive");
    }

    
    @Override
    public void run(){

        float firstTime = 0;
		float lastTime = System.nanoTime() / SECOND;
		float passedTime = 0;
		
		float unprocessedTime = 0;
		float fpsTime = 0;
		int frames = 0;
        int fps = 0;
        
        while(true){
            boolean render = false;
			
			firstTime = System.nanoTime() / SECOND;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			fpsTime += passedTime;
			
			while(unprocessedTime >= frameCap)
			{
				render = true;
				unprocessedTime -= frameCap;
				
                //update
                abGame.update();
				input.update();
                //print fps
				if(fpsTime >= 1)
				{
					fpsTime = 0;
					fps = frames;
					frames = 0;
					System.out.println(fps);
				}
			}
			
			if(render)
			{
                //render
                repaint();
                //
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			

        }


	}
	
	public Input getInput(){
		return input;
	}
    
    
}