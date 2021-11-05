package renderer.point;

import renderer.Display;
import java.awt.Point;

public class PointConverter {

    private static double scale = 0.666;
    private static double scaleStep = 1.1;

    public static Point convertPoint(MyPoint point3D){
        double x3d = point3D.y * scale;
        double y3d = point3D.z * scale;
        double depth= point3D.x * scale;
        double[] newVal = scale(x3d, y3d, depth);
        int x2d = (int) (Display.WIDTH/2 + newVal[0]);
        int y2d = (int) (Display.HEIGHT/2 - newVal[1]);

        return new Point(x2d, y2d);
    }

    private static double[] scale(double x3d, double y3d, double depth){
        double dist = Math.sqrt(x3d*x3d + y3d*y3d);
        double theta = Math.atan2(y3d, x3d);
        double depth2 = 15 - depth;
        double localScale = Math.abs(1400/(depth2+1400));
        dist *= localScale;
        double[] newVal = new double[2];
        newVal[0] = dist * Math.cos(theta);
        newVal[1] = dist * Math.sin(theta);
        return newVal;
    }

    public static void rotateAxisX(MyPoint p, boolean CW, double degrees) {
        double radius = Math.sqrt(p.y * p.y + p.z * p.z);
        double theta = Math.atan2(p.z, p.y);
        theta += 2 * Math.PI / 360 * degrees * (CW?-1:1);
        p.y = radius * Math.cos(theta);
        p.z = radius * Math.sin(theta);
    }

    public static void rotateAxisY(MyPoint p, boolean CW, double degrees) {
        double radius = Math.sqrt(p.x * p.x + p.z * p.z);
        double theta = Math.atan2(p.x, p.z);
        theta += 2 * Math.PI / 360 * degrees * (CW?-1:1);
        p.z = radius * Math.cos(theta);
        p.x = radius * Math.sin(theta);
    }

    public static void rotateAxisZ(MyPoint p, boolean CW, double degrees) {
        double radius = Math.sqrt(p.x * p.x + p.y * p.y);
        double theta = Math.atan2(p.y, p.x);
        theta += 2 * Math.PI / 360 * degrees * (CW?-1:1);
        p.x = radius * Math.cos(theta);
        p.y = radius * Math.sin(theta);
    }

    public static void changeScale(boolean increment){
        if(increment) scale*=scaleStep;
        else scale/=scaleStep;
        System.out.println(scale);
    }

    public static double getScale(){ return scale; }
}
