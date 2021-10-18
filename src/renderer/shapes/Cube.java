package renderer.shapes;

import renderer.point.MyPoint;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;

public class Cube{

    private Tetrahedron tetra;

    public Cube(int size, int x, int y, int z){
        MyPoint p1 = new MyPoint(size/2 + x, -size/2 + y, -size/2 + z);
        MyPoint p2 = new MyPoint(size/2 + x, size/2 + y, -size/2 + z);
        MyPoint p3 = new MyPoint(size/2 + x, size/2 + y, size/2 + z);
        MyPoint p4 = new MyPoint(size/2 + x, -size/2 + y, size/2 + z);
        MyPoint p5 = new MyPoint(-size/2 + x, -size/2 + y, -size/2 + z);
        MyPoint p6 = new MyPoint(-size/2 + x, size/2 + y, -size/2 + z);
        MyPoint p7 = new MyPoint(-size/2 + x, size/2 + y, size/2 + z);
        MyPoint p8 = new MyPoint(-size/2 + x, -size/2 + y, size/2 + z);

        this.tetra = new Tetrahedron(Color.WHITE, new MyPolygon(p1, p2, p3, p4),
                new MyPolygon(p5, p6, p7, p8),
                new MyPolygon(p1, p2, p6, p5),
                new MyPolygon(p1, p5, p8, p4),
                new MyPolygon(p2, p6, p7, p3),
                new MyPolygon(p4, p3, p7, p8));
    }

    public boolean click(Color color, MouseEvent e){
        MyPolygon targetFace = tetra.findPoly(e.getX(), e.getY());
        if(targetFace != null){
            targetFace.setColor(color);
            System.out.println("Color changed!");
            return true;
        }
        return false;
    }

    public void render(Graphics g){
        this.tetra.render(g);
    }

    public void render(Graphics g, HashSet<String> blacklist){
        this.tetra.render(g, blacklist);
    }

    public int height(){
        return this.tetra.height();
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        this.tetra.rotate(CW, xDegrees, yDegrees, zDegrees);
    }

    public void addFaces(HashMap<String, Integer> map){
        this.tetra.addPolys(map);
    }

//    public int[][] getLocs(){
//        return this.tetra.getLocs();
//    }

    public void sortFaces(){
        this.tetra.sortPolygons();
    }
}
