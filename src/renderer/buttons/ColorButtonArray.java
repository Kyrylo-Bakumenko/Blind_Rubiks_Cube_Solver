package renderer.buttons;

import renderer.Display;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ColorButtonArray {
    ColorButton[] buttons;
    public ColorButtonArray(){
        int size = 100;
        buttons = new ColorButton[]{
                new ColorButton(Display.edgePad, Display.edgePad, size, size, Color.RED),
                new ColorButton(Display.edgePad+(int)(size*1.1), Display.edgePad+(int)(size*1.1), size, size, Color.BLUE),
                new ColorButton(Display.edgePad+2*(int)(size*1.1), Display.edgePad+2*(int)(size*1.1), size, size, Color.WHITE),
                new ColorButton(Display.edgePad+3*(int)(size*1.1), Display.edgePad+3*(int)(size*1.1), size, size, Color.GREEN),
                new ColorButton(Display.edgePad+4*(int)(size*1.1), Display.edgePad+4*(int)(size*1.1), size, size, Color.ORANGE),
                new ColorButton(Display.edgePad+5*(int)(size*1.1), Display.edgePad+5*(int)(size*1.1), size, size, Color.YELLOW)};
    }
    public ColorButtonArray(int x, int y, int size){
        buttons = new ColorButton[]{
                new ColorButton(x, y, size, size, Color.RED),
                new ColorButton(x, y+(int)(size*1.1), size, size, Color.BLUE),
                new ColorButton(x, y+2*(int)(size*1.1), size, size, Color.WHITE),
                new ColorButton(x, y+3*(int)(size*1.1), size, size, Color.GREEN),
                new ColorButton(x, y+4*(int)(size*1.1), size, size, Color.ORANGE),
                new ColorButton(x, y+5*(int)(size*1.1), size, size, Color.YELLOW)};
    }
    public void paint(Graphics g){
        for(ColorButton button: buttons) button.paint(g);
    }

    public void deselectAll(){
        for(ColorButton button: buttons)
            if(button.getSelected()) button.toggle();
    }

    public Color contains(MouseEvent e){
        for(ColorButton button: buttons){
            if(button.contains(e)){
                deselectAll();
                button.toggle();
                return button.getColor();
            }
        }
        return null;
    }
}
