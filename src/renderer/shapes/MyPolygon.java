package renderer.shapes;

import renderer.Display;
import renderer.point.MyPoint;
import renderer.point.PointConverter;

import java.awt.*;

public class MyPolygon {

    private Color color;
    private final MyPoint[] points;
    private Line[] lines;
    private String loc; // x,y,z coordinates to identify overlaps
    private int id;
    
    // bounding rectangle
//    int xmax, xmin, ymax, ymin;
    
//    private int nvert;

    public MyPolygon(Color color, MyPoint... points){
        this.color = color;
        this.points = new MyPoint[points.length];
        for(int i = 0; i < points.length; i++){
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
//            nvert = points.length;
        }

        this.lines = new Line[points.length];
        for(int i = 0; i < points.length-1; i++) {
            Point p1 = PointConverter.convertPoint(this.points[i]);
            Point p2 = PointConverter.convertPoint(this.points[i + 1]);
            this.lines[i] = new Line(p1, p2);
        }
        Point p1 = PointConverter.convertPoint(this.points[points.length-1]);
        Point p2 = PointConverter.convertPoint(this.points[0]);
        this.lines[points.length-1] = new Line(p1, p2);

//        initBoundingRectangle(points);
        
        this.loc = setLoc();
    }

    public MyPolygon(MyPoint... points){
        this.color = Color.WHITE;
        this.points = new MyPoint[points.length];
        for(int i = 0; i < points.length; i++){
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
//            nvert = points.length;
        }

        this.lines = new Line[points.length];
        for(int i = 0; i < points.length-1; i++) {
            Point p1 = PointConverter.convertPoint(this.points[i]);
            Point p2 = PointConverter.convertPoint(this.points[i + 1]);
            this.lines[i] = new Line(p1, p2);
        }
        Point p1 = PointConverter.convertPoint(this.points[points.length-1]);
        Point p2 = PointConverter.convertPoint(this.points[0]);
        this.lines[points.length-1] = new Line(p1, p2);

//        initBoundingRectangle(points);
        
        this.loc = setLoc();
    }


    public void render(Graphics g) {
        Polygon poly = new Polygon();
        for (MyPoint point : this.points) {
            Point p = PointConverter.convertPoint(point);
            poly.addPoint(p.x, p.y);
        }
        g.setColor(this.color);
        g.fillPolygon(poly);

//        for(MyPoint p:this.points) {
//            Point p2 = PointConverter.convertPoint(p);
//            g.drawArc(p2.x-10, p2.y-10, 20, 20, 0, 360);
//        }

        updateLines();

        for(Line line :this.lines) {
            g.setColor(line.color);
            int thickness = Math.max((int) (6 * PointConverter.getScale()), 2);
            for(int i=-thickness/2; i<thickness/2; i++){
                g.drawLine((int)line.x1+i, (int)line.y1, (int)line.x2+i, (int)line.y2);
            }
        }
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        this.loc = setLoc(points);
        for(MyPoint p : points) {
            PointConverter.rotateAxisX(p, CW, xDegrees);
            PointConverter.rotateAxisY(p, CW, yDegrees);
            PointConverter.rotateAxisZ(p, CW, zDegrees);
        }
    }

    public void updateLines(){
        this.lines = new Line[points.length];
        for(int i = 0; i < points.length-1; i++) {
            Point p1 = PointConverter.convertPoint(this.points[i]);
            Point p2 = PointConverter.convertPoint(this.points[i + 1]);
            this.lines[i] = new Line(p1, p2);
        }
        Point p1 = PointConverter.convertPoint(this.points[points.length-1]);
        Point p2 = PointConverter.convertPoint(this.points[0]);
        this.lines[points.length-1] = new Line(p1, p2);
    }

    public boolean contains(int testx, int testy){
        if(!bounded(testx, testy)) return false;
        int intersections = 0;
        for(Line line : this.lines)
            if(line.intersects(testx, testy)) intersections++;
//        System.out.println("Lines: " + lines.length + ", intersected: " + intersections);
        return intersections % 2 == 1;
    }

//    public boolean contains(String loc){
//        int testx = Integer.parseInt(loc.substring(loc.indexOf("X")+1, loc.indexOf("Y")));
//        int testy = Integer.parseInt(loc.substring(loc.indexOf("Y")+1, loc.indexOf("Z")));
//        int intersections = 0;
//        for(Line line : this.lines)
//            if(line.intersects(testx, testy)) intersections++;
//        System.out.println("Lines: " + lines.length + ", intersected: " + intersections);
//        return intersections % 2 == 1;
//    }

    public int height(){
        int pointHeightAvg = 0;
        for(MyPoint p: this.points)
            pointHeightAvg += (int) p.x;
        return pointHeightAvg/this.points.length;
    }

    private String setLoc(MyPoint... ps){
        int x = 0;
        int y = 0;
        int z = 0;
        for(MyPoint p : ps){
            x+=p.x;
            y+=p.y;
            z+=p.z;
        }
        return "X" + x + "Y" + y + "Z" + z;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public String getLoc(){
        return loc;
    }

    public boolean equals(MyPolygon other){
        return other.getLoc() == this.getLoc();
    }

    public void setColor(Color color){
        this.color = color;
    }

    public char getColor(){
        if(this.color.equals(Display.colors[0])) return 'W';
        else if(this.color.equals(Display.colors[1])) return 'O';
        else if(this.color.equals(Display.colors[2])) return 'G';
        else if(this.color.equals(Display.colors[3])) return 'R';
        else if(this.color.equals(Display.colors[4])) return 'B';
        else return 'Y';
    }

    public void resetLineColor(){
        for(Line line : lines) line.resetColor();
    }
    
//    public void initBoundingRectangle(MyPoint... points){
//        this.xmax = Integer.MIN_VALUE;
//        this.xmin = Integer.MAX_VALUE;
//        this.ymax = Integer.MIN_VALUE;
//        this.ymin = Integer.MAX_VALUE;
//        for(MyPoint point : points){
//            Point p = PointConverter.convertPoint(point);
//            this.xmax = Math.max(xmax, (int)p.getX());
//            this.xmin = Math.min(xmin, (int)p.getX());
//            this.ymax = Math.max(ymax, (int)p.getY());
//            this.ymin = Math.min(ymin, (int)p.getY());
//        }
//    }
    
    public boolean bounded(int testx, int testy){

        int xmax = Integer.MIN_VALUE;
        int xmin = Integer.MAX_VALUE;
        int ymax = Integer.MIN_VALUE;
        int ymin = Integer.MAX_VALUE;
        for(MyPoint point : this.points){
            Point p = PointConverter.convertPoint(point);
            xmax = Math.max(xmax, (int)p.getX());
            xmin = Math.min(xmin, (int)p.getX());
            ymax = Math.max(ymax, (int)p.getY());
            ymin = Math.min(ymin, (int)p.getY());
        }

        return testx > xmin && testx < xmax && testy > ymin && testy < ymax;
    }
}
