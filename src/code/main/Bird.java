package code.main;

import java.awt.Graphics;
import java.awt.Color;

public class Bird{

    private float JUMP_FORCE = -6;
    private float GFORCE = 0.2f;

    public float xpos = 100;
    public float ypos = 215;
    public int width = 25;
    public int height = 25;

    float yvel = 0;

    public Bird(){

    }

    public void update(){
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
        g.setColor(new Color(0x3030ff));
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


}