package code.main;

import java.awt.Graphics;
import java.awt.Color;

public class Pipe {
    public int width = 20;//used for Nerual
    public int height = 20;//used for Nerual
    
    public float xpos = 720;//used for Nerual
    private int ypos = 480-height;

    public void update(){
        xpos-=3;

    }
    public void render(Graphics g){
        g.setColor(new Color((float)121/255, (float)117/255, (float)125/255, 0.5f));
        g.fillRect((int)xpos, ypos, width, height);
    }

    public boolean hit(Bird b){
        return !(b.xpos+b.width<xpos||b.xpos>xpos+width||b.ypos+b.height<ypos||b.ypos>ypos+height);
    }

    public boolean screenExit(){
        return xpos<0-width;
    }

}