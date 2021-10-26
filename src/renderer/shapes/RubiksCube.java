package renderer.shapes;

import renderer.Display;
import renderer.point.PointConverter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

public class RubiksCube {

    Cube[] RCube;
    Tetrahedron[] Corners;
    Tetrahedron[] Edges;

    HashSet<String> blacklisted;
//    HashSet<String> visible;

    public RubiksCube(int size, int x, int y, int z){
        RCube = new Cube[27];
        int cubeSize = size/3;
        for(int i=0; i<27; i++){
            int layer = i/9;
            int col = i%3;
            int row = (i%9)/3;
            RCube[i] = new Cube(cubeSize, x-cubeSize*(layer-1), y-cubeSize*(col-1), z-cubeSize*(row-1));
        }

        createBlacklist();
        assignIds();
//        visible = findVisible();
    }

    public void click(Color color, MouseEvent e){
        for(int i=this.RCube.length-1; i>=0; i--){
            if(this.RCube[i].click(color, e)) return;
        }

    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        for(int i=this.RCube.length-1; i>=0; i--){
            this.RCube[i].rotate(CW, xDegrees, yDegrees, zDegrees);
        }
        sortCubes();
        createBlacklist();
//        visible = findVisible();
    }

    // sort cublets to appear in correct depth order
    public void render(Graphics g){
        for (Cube cube : this.RCube)
            cube.render(g, blacklisted);
    }

    public void changeScale(boolean increment){
        PointConverter.changeScale(increment);
        for(Cube cube : RCube){
            cube.updateEdges();
        }
    }

    public void createBlacklist(){
        HashMap<String, Integer> map = new HashMap<>();
        blacklisted = new HashSet<>();
        for(Cube cube : this.RCube){
            cube.addFaces(map);
        }
        for(String loc : map.keySet()){
            if(map.get(loc) != 1) blacklisted.add(loc);
        }
    }

    public void assignIds(){
        int id = 0;
        for(Cube cube : RCube){
            id = cube.assignIds(id);
        }
    }

//    // step will also define minimum size of surface to be visible
//    public HashSet<String> findVisible(){
//        // safe search, if there is but a single pixel visible, render it.
//        HashSet<String> visible = new HashSet<>();
//        for(int x = 0; x<Display.WIDTH; x+=step){
//            for(int y =0; y<Display.HEIGHT; y+=step){
//                for(Cube cube : RCube){
//                    String loc = cube.findFaceLocation(x, y);
//                    visible.add(loc);
//                }
//            }
//        }
//
//        return visible;
//    }
    
    public void sortCubes(){
        int[][] order = new int[this.RCube.length][2];
        for(int i=0; i<this.RCube.length; i++) {
            order[i][0] = i;
            order[i][1] = this.RCube[i].height();
        }
        Arrays.sort(order, Comparator.comparingInt(a -> a[1]));
        Cube[] RCubeSorted = new Cube[this.RCube.length];
        for(int i=0; i<this.RCube.length; i++)
            RCubeSorted[i] = this.RCube[order[i][0]];
//        for(int[] arr: order) System.out.println(Arrays.toString(arr));
        this.RCube = RCubeSorted;

//        for(Cube cube : RCube){
//            cube.sortFaces();
//        }
    }
}
