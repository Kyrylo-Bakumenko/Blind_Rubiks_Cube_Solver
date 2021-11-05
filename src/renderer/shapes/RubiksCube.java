package renderer.shapes;

import renderer.Display;
import renderer.point.PointConverter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.*;

public class RubiksCube {

    Cube[] RCube;
    MyPolygon[] edges;
    MyPolygon[] corners;
    MyPolygon[] centers;
//    Color[] edgeColors;
//    Color[] cornerColors;

    char[] edgeColors;
    char[] cornerColors;
    private static HashMap<Character, Integer> colorKey;

    long rainbowStart;
    long elapsed;
    boolean rainbow;
    private float animationSpeed = 0.0002f;

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
        edges = new MyPolygon[24];
        corners = new MyPolygon[24];
        centers = new MyPolygon[6];
        assignFaces();
        assignColors();

        rainbow = false;
        elapsed = 0;
//        visible = findVisible();
        initColorKey();
    }

    public void click(Color color, MouseEvent e){
        for(int i=this.RCube.length-1; i>=0; i--){
            if(this.RCube[i].click(color, e, edges, corners, centers)){
                updateState();
                return;
            }
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
        if(rainbow) {
            elapsed += System.currentTimeMillis() - rainbowStart;
            rainbowStart = System.currentTimeMillis();
            char red = (char) (255 - Math.max(Math.min(((1+Math.sin(elapsed * animationSpeed)) * 128), 255.f), 0.f));
            char green =  (char) (255 - Math.max(Math.min(((1+Math.sin(elapsed * animationSpeed*2)) * 128), 255.f), 0.f));
            char blue = (char) (255 - Math.max(Math.min(((Math.sin(elapsed * animationSpeed*3)) * 128), 255.f), 0.f));
            colorFill(new Color(red, green, blue));
        }

//        for (Cube cube : this.RCube)
//            cube.render(g, blacklisted);

        for (Cube cube : this.RCube)
            cube.render(g, edges, corners, centers);

        displayState(g);
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

    private void assignFaces(){
        int i = 0;
        for(Cube cube : RCube){
            MyPolygon[] temp = cube.getTetra().getPolygons();
            for(MyPolygon poly : temp){
                    if (i==160) return;
                    else if (i==119) edges[0] = poly;
                    else if (i==59) edges[1] = poly;
                    else if (i==11) edges[2] = poly;
                    else if (i==71) edges[3] = poly;
                    else if (i==69) edges[4] = poly;
                    else if (i==33) edges[5] = poly;
                    else if (i==105) edges[6] = poly;
                    else if (i==141) edges[7] = poly;
                    else if (i==6) edges[8] = poly;
                    else if (i==18) edges[9] = poly;
                    else if (i==42) edges[10] = poly;
                    else if (i==30) edges[11] = poly;
                    else if (i==58) edges[12] = poly;
                    else if (i==130) edges[13] = poly;
                    else if (i==94) edges[14] = poly;
                    else if (i==22) edges[15] = poly;
                    else if (i==115) edges[16] = poly;
                    else if (i == 139) edges[17] = poly;
                    else if (i == 151) edges[18] = poly;
                    else if (i == 127) edges[19] = poly;
                    else if (i == 44) edges[20] = poly;
                    else if (i == 92) edges[21] = poly;
                    else if (i == 152) edges[22] = poly;
                    else if (i == 104) edges[23] = poly;
                    else if (i == 125) corners[0] = poly;
                    else if (i == 113) corners[1] = poly;
                    else if (i == 5) corners[2] = poly;
                    else if (i == 17) corners[3] = poly;
                    else if (i == 123) corners[4] = poly;
                    else if (i == 15) corners[5] = poly;
                    else if (i == 51) corners[6] = poly;
                    else if (i == 159) corners[7] = poly;
                    else if (i == 12) corners[8] = poly;
                    else if (i == 0) corners[9] = poly;
                    else if (i == 36) corners[10] = poly;
                    else if (i == 48) corners[11] = poly;
                    else if (i == 4) corners[12] = poly;
                    else if (i == 112) corners[13] = poly;
                    else if (i == 148) corners[14] = poly;
                    else if (i == 40) corners[15] = poly;
                    else if (i == 109) corners[16] = poly;
                    else if (i == 121) corners[17] = poly;
                    else if (i == 157) corners[18] = poly;
                    else if (i == 145) corners[19] = poly;
                    else if (i == 50) corners[20] = poly;
                    else if (i == 38) corners[21] = poly;
                    else if (i == 146) corners[22] = poly;
                    else if (i == 158) corners[23] = poly;
                    else if (i == 65) centers[0] = poly;
                    else if (i == 87) centers[1] = poly;
                    else if (i == 24) centers[2] = poly;
                    else if (i == 76) centers[3] = poly;
                    else if (i == 133) centers[4] = poly;
                    else if (i == 98) centers[5] = poly;
                    i++;

//                switch (i) {
//                    case 160: return;
//                    case 119: edges[0] = poly;
//                    case 59: edges[1] = poly;
//                    case 11: edges[2] = poly;
//                    case 71: edges[3] = poly;
//                    case 69: edges[4] = poly;
//                    case 33: edges[5] = poly;
//                    case 105: edges[6] = poly;
//                    case 141: edges[7] = poly;
//                    case 6: edges[8] = poly;
//                    case 18: edges[9] = poly;
//                    case 42: edges[10] = poly;
//                    case 30: edges[11] = poly;
//                    case 58: edges[12] = poly;
//                    case 130: edges[13] = poly;
//                    case 94: edges[14] = poly;
//                    case 22: edges[15] = poly;
//                    case 115: edges[16] = poly;
//                    case 139: edges[17] = poly;
//                    case 151: edges[18] = poly;
//                    case 127: edges[19] = poly;
//                    case 44: edges[20] = poly;
//                    case 92: edges[21] = poly;
//                    case 152: edges[22] = poly;
//                    case 104: edges[23] = poly;
//                    case 125: corners[0] = poly;
//                    case 113: corners[1] = poly;
//                    case 5: corners[2] = poly;
//                    case 17: corners[3] = poly;
//                    case 123: corners[4] = poly;
//                    case 15: corners[5] = poly;
//                    case 51: corners[6] = poly;
//                    case 159: corners[7] = poly;
//                    case 12: corners[8] = poly;
//                    case 0: corners[9] = poly;
//                    case 36: corners[10] = poly;
//                    case 48: corners[11] = poly;
//                    case 4: corners[12] = poly;
//                    case 112: corners[13] = poly;
//                    case 148: corners[14] = poly;
//                    case 40: corners[15] = poly;
//                    case 109: corners[16] = poly;
//                    case 121: corners[17] = poly;
//                    case 157: corners[18] = poly;
//                    case 145: corners[19] = poly;
//                    case 50: corners[20] = poly;
//                    case 38: corners[21] = poly;
//                    case 146: corners[22] = poly;
//                    case 158: corners[23] = poly;
//                    case 65: centers[0] = poly;
//                    case 87: centers[1] = poly;
//                    case 24: centers[2] = poly;
//                    case 76: centers[3] = poly;
//                    case 133: centers[4] = poly;
//                    case 98: centers[5] = poly;
//                }
//                i++;
            }
        }
    }

    public void assignColors(){
        edgeColors = new char[edges.length];
        cornerColors = new char[corners.length];
        for(int i = 0; i < edges.length; i++) {
            edgeColors[i] = edges[i].getColor();
            cornerColors[i] = corners[i].getColor();
        }
        for(int i=0; i<Display.colors.length; i++)
            centers[i].setColor(Display.colors[i]);
    }

    public void initColorKey(){
        colorKey = new HashMap<>();
        colorKey.put('W', 0);
        colorKey.put('O', 1);
        colorKey.put('G', 2);
        colorKey.put('R', 3);
        colorKey.put('B', 4);
        colorKey.put('Y', 5);
    }

    public void restoreState(){
        for(int i=0; i<edgeColors.length; i++) {
            char c = edgeColors[i];
            edges[i].setColor(Display.colors[colorKey.get(c)]);
        }
        for(int i=0; i<cornerColors.length; i++) {
            char c = cornerColors[i];
            corners[i].setColor(Display.colors[colorKey.get(c)]);
        }
        for(int i=0; i<centers.length; i++)
            centers[i].setColor(Display.colors[i]);
    }

    public void reset(){
        colorFill(Color.WHITE);
    }

    public void colorFill(Color color){
        for(MyPolygon poly : edges)
            poly.setColor(color);
        for(MyPolygon poly : corners)
            poly.setColor(color);
        for(MyPolygon poly : centers)
            poly.setColor(color);
    }

    public void toggleRainbow(){
        rainbow = !rainbow;
        if(rainbow) rainbowStart = System.currentTimeMillis();
        else restoreState();
    }

    public void displayState(Graphics g){
        StringBuilder sb1 = new StringBuilder("Edges:");
        // add edges
        for(int i = 0; i < edgeColors.length; i++){
            if(i % 4 == 0) sb1.append("   ");
            sb1.append(edgeColors[i]);
        }
        // add corners
        StringBuilder sb2 = new StringBuilder("Corners:");
        for(int i = 0; i < cornerColors.length; i++){
            if(i % 4 == 0) sb2.append("   ");
            sb2.append(cornerColors[i]);
        }

        String edgeText = sb1.toString();
        String cornerText = sb2.toString();
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        Color temp = g.getColor();
        g.setColor(Color.WHITE);

        int edgeTextWidth = (int)(g.getFont().getStringBounds(edgeText, frc).getWidth());
        int edgeTextHeight = (int)(g.getFont().getStringBounds(edgeText, frc).getHeight());
        int cornerTextWidth = (int)(g.getFont().getStringBounds(cornerText, frc).getWidth());
        int cornerTextHeight = (int)(g.getFont().getStringBounds(cornerText, frc).getHeight());

        g.drawString(edgeText, (Display.WIDTH-edgeTextWidth)/2, (Display.HEIGHT-edgeTextHeight/2-cornerTextHeight/2-2*Display.edgePad));
        g.drawString(cornerText, (Display.WIDTH-cornerTextWidth)/2, (Display.HEIGHT-cornerTextHeight/2-Display.edgePad));
        g.setColor(temp);
    }

    public void updateState(){
        for(int i = 0; i < edges.length; i++)
            edgeColors[i] = edges[i].getColor();
        for(int i = 0; i < corners.length; i++)
            cornerColors[i] = corners[i].getColor();
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
