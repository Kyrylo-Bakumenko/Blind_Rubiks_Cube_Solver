package renderer.buttons;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ColorButton extends Button{
    private final Button button;
    private final Color color;
    private boolean selected;
    private final int border;
    public ColorButton(){
        button = new Button(0, 0, 100, 100);
        color = Color.white;
        selected = false;
        border = 5;
    }
    public ColorButton(int x, int y, int width, int height, Color color){
        button = new Button(x, y, width, height);
        this.color = color;
        selected = false;
        border = (int)(0.05*Math.min(width, height));
    }


    public void render(Graphics g){
        Color prevColor = g.getColor();
        if(selected){
            g.setColor(Color.WHITE);
            g.drawRect(button.x, button.y, button.width+2*border, button.height+2*border);
        }
        g.setColor(color);
        g.fillRect(button.x+border, button.y+border, button.width, button.height);
        g.setColor(prevColor);
    }

    public boolean contains(MouseEvent e) {
        return button.contains(e);
    }

    public Color getColor(){
        return color;
    }

    public boolean getSelected(){
        return selected;
    }

    public void toggle(){
        selected = !selected;
    }
}
