package renderer.buttons;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button {
    int x;
    int y;
    int width;
    int height;
    public Button(){
        int x, y = 0;
        int width, height = 100;
    }
    public Button(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paint(Graphics g){
        g.fillRect(x, y, width, height);
    }

    public boolean contains(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        return mx > x && my > y && mx < x+width && my < y+height;
    }
}
