package renderer.tools;

import renderer.point.MyPoint;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class RotationSphere {

    private MyPoint center;
    private int radius;

    public RotationSphere(int radius){
        this.center = new MyPoint(0, 0, 0);
        this.radius = radius;
    }

    public boolean contains(MouseEvent e){
        // find displacements relative to sphere center in 3d coords
        Point p = PointConverter.convertPoint(center);
        int disY = e.getX() - p.x;
        int disZ = e.getY() - p.y;
        // compare to radius
        return ( (disY*disY + disZ*disZ) < getRadius()*getRadius());
    }

    private double[] convertToSpherical(Point p){
//        double l = Math.sqrt(Math.pow(radius - p.getY(), 2) + Math.pow(Math.abs(p.getX()), 2));
//        double theta = (int) Math.asin(l/(radius*2))*2;
//        // compensate for CCW if necessary
//        if(p.getX() < 0) theta += 180;
//
//        double h = Math.sqrt(Math.pow(radius - p.getY(), 2) +);
//        double phi = 0;

        double theta = Math.acos(p.getY()/getRadius());
        double phi = Math.acos(p.getX()/getRadius());

        return new double[]{theta, phi};
    }

    public double[] getRotation(Point p1, Point p2){
        double[] rot1 = convertToSpherical(p1);
        double[] rot2 = convertToSpherical(p2);

        // return necessary transformation
        return new double[]{rot2[0]-rot1[0], rot2[1]-rot1[1]};
    }

    public void render(Graphics g){
        Point p = PointConverter.convertPoint(this.center);
        g.setColor(Color.WHITE);
        g.drawArc(p.x -getRadius()/2, p.y -getRadius()/2, getRadius(), getRadius(), 0, 360);
    }

    public int getRadius(){
        return (int)(this.radius*PointConverter.getScale());
    }

}
