package renderer.shapes;

import java.awt.*;
import java.util.*;

public class Tetrahedron {

    private MyPolygon[] polygons;
    private final Color color;

    public Tetrahedron(Color color, MyPolygon... polygons){
        this.color = color;
        this.polygons = polygons;
        this.setPolygonColor();
    }

    public Tetrahedron(MyPolygon... polygons){
        this.color = Color.WHITE;
        this.polygons = polygons;
    }

    public void render(Graphics g){
        for(MyPolygon poly : this.polygons){
            poly.render(g);
        }
//        for(int i=3; i<6; i++){
//            this.polygons[i].render(g);
//        }
    }

    public void render(Graphics g, HashSet<String> blacklisted){
        for(MyPolygon poly : this.polygons){
            if(!blacklisted.contains(poly.getLoc())) poly.render(g);
        }
    }

    public void render(Graphics g, MyPolygon[]... polys) {
        for(int i=3; i<6; i++){
            MyPolygon poly = this.polygons[i];
            for(MyPolygon[] faces : polys) {
                for (MyPolygon face : faces) {
                    if (face.getId() == poly.getId()) {
                        poly.render(g);
                    }
                }
            }
        }
    }

    public void updateEdges(){
        for(MyPolygon poly : this.polygons){
            poly.updateLines();
        }
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        for(MyPolygon poly : this.polygons){
            poly.rotate(CW, xDegrees, yDegrees, zDegrees);
        }
        sortPolygons();
    }

    public void sortPolygons(){
        int[][] order = new int[this.polygons.length][2];
        for(int i=0; i<this.polygons.length; i++) {
            order[i][0] = i;
            order[i][1] = this.polygons[i].height();
        }
        Arrays.sort(order, Comparator.comparingInt(a -> a[1]));
        MyPolygon[] polygonsSorted = new MyPolygon[this.polygons.length];
        for(int i=0; i<this.polygons.length; i++)
            polygonsSorted[i] = this.polygons[order[i][0]];
//        for(int[] arr: order) System.out.println(Arrays.toString(arr));
        this.polygons = polygonsSorted;
    }

    public int height(){
        int sum = 0;
        for(MyPolygon poly : this.polygons){
            sum += poly.height();
        }
        return sum;
    }

    private void setPolygonColor(){
        for(MyPolygon poly : this.polygons){
            poly.setColor(this.color);
        }
    }

    public MyPolygon findPoly(int y, int z){
        for(MyPolygon poly : this.polygons){
            poly.resetLineColor();
        }
        for(int i=this.polygons.length-1; i >= this.polygons.length-3; i--){
            if(this.polygons[i].contains(y, z)) return this.polygons[i];
        }
        return null;
    }

    public MyPolygon findPoly(int y, int z, MyPolygon[] edges, MyPolygon[] corners){
        for(MyPolygon poly : this.polygons) {
            poly.resetLineColor();
        }

        for(int i=this.polygons.length-1; i >= this.polygons.length-3; i--){
            if (this.polygons[i].contains(y, z)){
                for(int j=0; j<edges.length; j++){
                    if(this.polygons[i].getId() == edges[j].getId() || this.polygons[i].getId() == corners[j].getId()) {
                        return this.polygons[i];
                    }
                }
            }
        }
        return null;
    }

    public int assignIds(int id){
        for(MyPolygon poly : polygons){
            poly.setId(id++);
        }
        return id;
    }

//    public MyPolygon findAllVisiblePolygons(){
//        for(MyPolygon poly : this.polygons){
//            poly.resetLineColor();
//        }
//        for(int i=this.polygons.length-1; i >= this.polygons.length-3; i--){
//            if(this.polygons[i].contains(this.polygons[i].getLoc())) return this.polygons[i];
//        }
//        return null;
//    }

    public void addPolys(HashMap<String, Integer> map){
        for(MyPolygon poly : this.polygons){
            map.put(poly.getLoc(), map.getOrDefault(poly.getLoc(), 0)+1);
        }
    }

    public MyPolygon[] getPolygons(){
        return polygons;
    }

//    public int[][] getLocs(){
//        int[][] polyLocs = new int[this.polygons.length][3];
//        for(int i=0; i<polyLocs.length; i++){
//            polyLocs[i] = this.polygons[i].getLoc();
//        }
//        return polyLocs;
//    }
}
