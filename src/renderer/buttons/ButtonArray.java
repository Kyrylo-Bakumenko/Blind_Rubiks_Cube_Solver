package renderer.buttons;

import renderer.Display;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class ButtonArray {
    ColorButton[] buttons;
    OrbitButton orbitButton;
    public ButtonArray(){
        int size = 100;
        this.buttons = new ColorButton[]{
                new ColorButton(Display.edgePad, Display.edgePad, size, size, Color.RED),
                new ColorButton(Display.edgePad+(int)(size*1.1), Display.edgePad+(int)(size*1.1), size, size, Color.BLUE),
                new ColorButton(Display.edgePad+2*(int)(size*1.1), Display.edgePad+2*(int)(size*1.1), size, size, Color.WHITE),
                new ColorButton(Display.edgePad+3*(int)(size*1.1), Display.edgePad+3*(int)(size*1.1), size, size, Color.GREEN),
                new ColorButton(Display.edgePad+4*(int)(size*1.1), Display.edgePad+4*(int)(size*1.1), size, size, Color.ORANGE),
                new ColorButton(Display.edgePad+5*(int)(size*1.1), Display.edgePad+5*(int)(size*1.1), size, size, Color.YELLOW)};
    }
    public ButtonArray(int x, int y, int size){
        this.buttons = new ColorButton[]{
                new ColorButton(x, y, size, size, Color.RED),
                new ColorButton(x, y+(int)(size*1.1), size, size, Color.BLUE),
                new ColorButton(x, y+2*(int)(size*1.1), size, size, Color.WHITE),
                new ColorButton(x, y+3*(int)(size*1.1), size, size, Color.GREEN),
                new ColorButton(x, y+4*(int)(size*1.1), size, size, Color.ORANGE),
                new ColorButton(x, y+5*(int)(size*1.1), size, size, Color.YELLOW)};
    }

    public ButtonArray(int x, int y, int size, File file){
        this.buttons = new ColorButton[]{
                new ColorButton(x, y, size, size, Color.RED),
                new ColorButton(x, y+(int)(size*1.1), size, size, Color.BLUE),
                new ColorButton(x, y+2*(int)(size*1.1), size, size, Color.WHITE),
                new ColorButton(x, y+3*(int)(size*1.1), size, size, Color.GREEN),
                new ColorButton(x, y+4*(int)(size*1.1), size, size, Color.ORANGE),
                new ColorButton(x, y+5*(int)(size*1.1), size, size, Color.YELLOW)};
        this.orbitButton = new OrbitButton(x, y+6*(int)(size*1.1), size, size, file);
    }
    public void render(Graphics g){
        orbitButton.render(g);
        for(ColorButton button: buttons) button.render(g);
    }

    public void deselectAll(){
        for(ColorButton button: buttons)
            if(button.getSelected()) button.toggle();
        if(orbitButton.getSelected()) orbitButton.toggle();
    }

    public boolean orbiting(){
        return orbitButton.getSelected();
    }

    public Color contains(MouseEvent e){
        for(ColorButton button: buttons){
            if(button.contains(e)){
                deselectAll();
                button.toggle();
                return button.getColor();
            }
        }
        if(orbitButton.contains(e)){
            deselectAll();
            orbitButton.toggle();
        }
        return null;
    }
}
