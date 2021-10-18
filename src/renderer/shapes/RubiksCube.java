package renderer.shapes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

public class RubiksCube {

    Cube[] RCube;
    HashSet<String> blacklisted;

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
    }

    // sort cublets to appear in correct depth order
    public void render(Graphics g){
        createBlacklist();

//        System.out.println("Size: " + blacklisted.size());
//        for(Location loc : blacklisted)
//            System.out.println(loc.toString());

        for (Cube cube : this.RCube)
            cube.render(g, blacklisted);

//        for(int i=this.RCube.length-1; i>=0; i--){
//            this.RCube[i].render(g);
//        }
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
