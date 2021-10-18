package renderer.buttons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OrbitButton extends Button{

    BufferedImage img;
    private final int border;
    boolean selected;

    public OrbitButton(int x, int y, int width, int height, File file) {
        super(x, y, width, height);
        border = (int)(0.05*Math.min(width, height));
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Color prevColor = g.getColor();
        if(selected){
            g.setColor(Color.WHITE);
            g.drawRect(super.x, super.y, super.width+2*border, super.height+2*border);
        }
        g.setColor(prevColor);
        g.drawImage(img, super.x + super.width/6, super.y + super.height/6, null);
    }

    public boolean getSelected(){
        return selected;
    }

    public void toggle(){
        selected = !selected;
    }
}
