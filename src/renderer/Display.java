package renderer;

import renderer.buttons.ButtonArray;
import renderer.buttons.OrbitButton;
import renderer.shapes.RubiksCube;
import renderer.tools.RotationSphere;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

public class Display extends Canvas implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener{
    private Thread thread;
    private JFrame frame;
    private final int frameRate = 1000; //fps
    private static final String title = "Rubik's Cube Pochmann Method";
    private static boolean running = false;
    public static int WIDTH = 1600;
    public static int HEIGHT = 1200;
    public static int edgePad = (int) (Math.min(WIDTH, HEIGHT)*0.02);

    public static final Color background = new Color(30, 30, 30); // screen background color
    public static final Color text = new Color(170, 170, 170); // screen background color
    private ButtonArray BA;
    private RubiksCube RCube;
    private RotationSphere sphere;
    private Color currentColor;

    
    public Display() {
        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }
    
    public static void main(String[] args) throws IOException {
        Display display = new Display();
        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setIconImage(ImageIO.read(new File("imgs/cube.png")));
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(true);
        display.frame.setVisible(true);

        display.start();
    }
    
    public synchronized void start(){
        running = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }
    public synchronized void stop(){
        try{
            this.thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1_000_000_000.0 / frameRate;
        double delta = 0;
        int frames = 0;

        init();

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                delta--;
                render();
                frames++;
            }

            if(System.currentTimeMillis() - timer > 1_000){
                timer += 1_000;
                this.frame.setTitle(title + " | " + frames + " fps");
                frames = 0;
            }
        }
         stop();
    }

    private void init() {
        int size = 600;
        this.BA = new ButtonArray(Display.edgePad, Display.edgePad, 100, new File("imgs/orbit.png"));
        this.RCube = new RubiksCube(size, 0, 0, 0);
        this.RCube.rotate(false, 0, 35, 35);
        this.sphere = new RotationSphere( size );
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH, HEIGHT);

//        tetra.render(g);
        sphere.render(g);
        RCube.render(g);
        BA.render(g);

        g.dispose();
        bs.show();
    }
    private void update(){
//        this.tetra.rotate(true, 30.0/(frameRate), 30.0/(frameRate), 0);
//        this.RCube.rotate(true, 20.0/frameRate, 20.0/frameRate, 0);
    }

    Point old = null;
    @Override
    public void mouseDragged(MouseEvent e) {
        double rotationFactor = 40;// magnifies rotation
        if(sphere.contains(e) && BA.orbiting()) {
            Point current = new Point(e.getX() - Display.WIDTH / 2, e.getY() - Display.HEIGHT / 2);
            if (old != null) {
                double[] rot = sphere.getRotation(old, current);

                RCube.rotate(rot[0] > 0, 0, Math.abs(rot[0])*rotationFactor, 0);
                RCube.rotate(rot[1] > 0, 0, 0, Math.abs(rot[1])*rotationFactor);
            }
            old = current;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(sphere.contains(e)) System.out.println("INSIDE SPHERE");
        if(old==null) {
            Color selectedColor = BA.contains(e);
            if (selectedColor != null) {
                System.out.println("Color Selected");
                currentColor = selectedColor;
            } else if (currentColor != null && !BA.orbiting()) {
                RCube.click(currentColor, e);
            }
        }

        old = null; // resets frag from rotation drag action
//        System.out.println(e.getX() + ", " + e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        RCube.changeScale(notches < 0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        if(sphere.contains(e)) System.out.println("INSIDE SPHERE");
//        if(old==null) {
//            Color selectedColor = BA.contains(e);
//            if (selectedColor != null) {
//                System.out.println("Color Selected");
//                currentColor = selectedColor;
//            } else if (currentColor != null && !BA.orbiting()) {
//                RCube.click(currentColor, e);
//            }
//        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
