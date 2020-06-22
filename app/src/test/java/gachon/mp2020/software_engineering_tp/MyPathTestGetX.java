package gachon.mp2020.software_engineering_tp;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class MyPathTestGetX extends TestCase {
    private DijkstraAlgorithm dijkstra;
    private ArrayList<String> result;
    private ArrayList<Double> xArray = new ArrayList<Double>(Arrays.asList(new Double[]{210.0, 195.0, 320.0, 368.0, 442.0, 418.0, 433.0}));

    @Before
    public void setUp() throws Exception {
        dijkstra= new DijkstraAlgorithm("가천관", "IT대학");
        result = dijkstra.getStops();
    }

    public void tearDown(){

    }

    @Test
    public void testGetXarray() {
        ArrayList<Double> x = new MyPath(result).getXarray();
        xArray.replaceAll(e -> e * 2.6);
        assertEquals("Not Correct", xArray, x);
    }

}