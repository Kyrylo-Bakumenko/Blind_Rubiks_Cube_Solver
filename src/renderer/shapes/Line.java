package renderer.shapes;

import renderer.Display;
import renderer.point.MyPoint;
import renderer.point.PointConverter;

import java.awt.*;

public class Line {

    double x1;
    double y1;
    double x2;
    double y2;
    Color color;

    public Line(Point p1, Point p2){
        if(p1.y < p2.y) {
            x1 = p1.x;
            x2 = p2.x;
            y1 = p1.y;
            y2 = p2.y;
        }else{
            x1 = p2.x;
            x2 = p1.x;
            y1 = p2.y;
            y2 = p1.y;
        }
        resetColor();
    }

    // ray tracing intersection towards the right
    public boolean intersects(int testx, int testy){
        if( (testy >= y1 && testy >= y2) || (testy <= y1 && testy <= y2) ) return false;
        int intersectX = (int) ( x1 + ((testy-y1)*(x2-x1))/(y2-y1) );
        if(intersectX > testx) color = Color.CYAN;
        System.out.println("test: " + testx + " tgt: " + intersectX);
        return intersectX > testx;
    }

    public void resetColor(){
        color = Color.BLACK;
    }

}
