package renderer.buttons;

import renderer.Display;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class ButtonArray {
    ColorButton[] buttons;
    OrbitButton orbitButton;
//    Button pochmannButton;
//    int pButtonWidth = 160;
//    int pButtonHeight = 40;

    public ButtonArray(){
        int size = 100;
        this.buttons = new ColorButton[]{
                new ColorButton(Display.edgePad, Display.edgePad, size, size, Color.RED),
                new ColorButton(Display.edgePad, Display.edgePad+(int)(size*1.1), size, size, Color.BLUE),
                new ColorButton(Display.edgePad, Display.edgePad+2*(int)(size*1.1), size, size, Color.WHITE),
                new ColorButton(Display.edgePad, Display.edgePad+3*(int)(size*1.1), size, size, Color.GREEN),
                new ColorButton(Display.edgePad, Display.edgePad+4*(int)(size*1.1), size, size, Color.ORANGE),
                new ColorButton(Display.edgePad, Display.edgePad+5*(int)(size*1.1), size, size, Color.YELLOW)};
    }
    public ButtonArray(int x, int y, int size){
        this.buttons = new ColorButton[Display.colors.length];
        for(int i=0; i<Display.colors.length; i++)
            this.buttons[i] = new ColorButton(x, y+(int)(i*size*1.1), size, size, Display.colors[i]);
    }

    public ButtonArray(int x, int y, int size, File file){
        this.buttons = new ColorButton[Display.colors.length];
        for(int i=0; i<Display.colors.length; i++)
            this.buttons[i] = new ColorButton(x, y+(int)(i*size*1.1), size, size, Display.colors[i]);

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

//    public void initPochmannButton(){
//        this.pochmannButton = new Button((Display.WIDTH-pButtonWidth)/2, Display.edgePad, pButtonWidth, pButtonHeight);
//    }
//    public void drawPochmannButton(Graphics g){
//        Color temp = g.getColor();
//        g.setColor(Display.background);
//        g.fillRoundRect((Display.WIDTH-pButtonWidth)/2, Display.edgePad, pButtonWidth, pButtonHeight, 30, 30);
//        g.setColor(Color.GRAY);
//        g.drawRoundRect((Display.WIDTH-pButtonWidth)/2, Display.edgePad, pButtonWidth, pButtonHeight, 30, 30);
//        g.setColor(temp);
//    }
}
