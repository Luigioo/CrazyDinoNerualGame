package code.main;

import java.awt.Graphics;
import java.awt.Color;

public class Pipe {
    private int width = 20;
    private int height = 40;
    
    private int xpos = 720;
    private int ypos = 480-height;

    public void update(){
        xpos-=2;

    }
    public void render(Graphics g){
        g.setColor(new Color((float)121/255, (float)117/255, (float)125/255, 0.5f));
        g.fillRect(xpos, ypos, width, height);
    }

    public boolean hit(Bird b){
        return !(b.xpos+b.width<xpos||b.xpos>xpos+width||b.ypos+b.height<ypos||b.ypos>ypos+height);
    }

    public boolean screenExit(){
        return xpos<0-width;
    }

}