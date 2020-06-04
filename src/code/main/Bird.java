package code.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;

public class Bird{

    public float JUMP_FORCE = -6;
    private float GFORCE = 0.2f;

    public float xpos = 100;
    public float ypos = 215;//used in main
    public int width = 25;
    public int height = 25;

    public NeuralNetwork brain;

    public float yvel = 0;//used in main

    public float score = 0;//used in main
    public float fitness;

    public Bird(){
        this.brain = new NeuralNetwork(1, 2, 2);
    }
    public Bird(NeuralNetwork nn){
        this.brain = nn;
    }

    public void update(){

        score++;

        ypos += yvel;
        yvel += GFORCE;

        if(ypos>=(480-height)){
            // System.out.println("falling out");
            yvel = Math.min(yvel,0);
            ypos = 479-height;
        }
        else if(ypos<0){
            yvel = Math.max(yvel, 0);
            ypos = 0;
        }

    }
    public void render(Graphics g){
        g.setColor(new Color((float)252/255, (float)186/255, (float)3/255, 0.4f));
		g.fillRect((int)xpos, (int)ypos, width, height);
    }

    public void jump(){
        if(onGround()){
            yvel = JUMP_FORCE;
        }
    }

    public boolean onGround(){
        return ypos>478.9-height;
    }

    public int intOnGround(){
        return ypos>478.9-height ? 0:1;
    }

    public Pipe closestPipe(ArrayList<Pipe> pipes){
        Pipe closestPipe = null;
        int closestDistance = 1024;
        for(Pipe p:pipes){

			int distance = (int)p.xpos - (int)this.xpos;
            if(distance<closestDistance && distance > 0){
                closestPipe = p;
                closestDistance = distance;
            }
        }
        return closestPipe;
    }

    public void mutate(){
        brain.mutate(1.0f);
    }

    public void think(ArrayList<Pipe> pipes){
        Pipe targetPipe = closestPipe(pipes);
        if(targetPipe != null){
            float[] inputArray = new float[]{
                //(float)intOnGround(),
                targetPipe.xpos/720
            };
            float[] output = brain.feedForward(inputArray);
            if(output[0]>output[1]*1.65){
                jump();
            }

        }
    }


}