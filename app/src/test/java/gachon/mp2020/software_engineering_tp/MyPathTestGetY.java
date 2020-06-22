package gachon.mp2020.software_engineering_tp;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class MyPathTestGetY extends TestCase {
    private DijkstraAlgorithm dijkstra;
    private ArrayList<String> result;
    private ArrayList<Double> yArray = new ArrayList<Double>(Arrays.asList(new Double[]{467.0, 443.0, 479.0, 445.0, 411.0, 392.0, 386.0}));

    public void setUp() throws Exception {
        dijkstra= new DijkstraAlgorithm("가천관", "IT대학");
        result = dijkstra.getStops();
    }

    public void testGetYarray() {
        ArrayList<Double> y = new MyPath(result).getYarray();
        yArray.replaceAll(e->e*2.4);
        assertEquals("Not Correct",yArray,y);
    }
}