package gachon.mp2020.software_engineering_tp;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class DijkstraUnitTest extends TestCase {

    private DijkstraAlgorithm dijkstra;
    private ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{"IT대학", "22", "23", "26", "24", "17", "가천관"}));

    @BeforeClass
    public void setUp() throws Exception {
        dijkstra= new DijkstraAlgorithm("가천관", "IT대학");
    }

    public void tearDown(){

    }

    @Test
    public void testObjectNotNull(){
        assertNotNull(dijkstra);
    }

    @Test(timeout=5000)
    public void testGetDistance(){
        double result=dijkstra.getDistance();
        assertEquals("Not Correct",428.0,result,0);
    }

    @Test(timeout=5000)
    public void testGetStops() {
        ArrayList<String> result = dijkstra.getStops();
        assertEquals("Not Correct",expected,result);
    }

}