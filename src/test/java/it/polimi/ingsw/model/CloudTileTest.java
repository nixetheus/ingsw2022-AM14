package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;

/**
 * CloudTile class test
 */
public class CloudTileTest {
  @Test
  public void testEmptyCloud(){
    CloudTile cloudTest = new CloudTile(0);
    assertEquals(Arrays.toString(cloudTest.getStudents()), "[0, 0, 0, 0, 0]");
    cloudTest.addStudent(0);
    cloudTest.addStudent(0);
    cloudTest.addStudent(1);
    assertEquals(Arrays.toString(cloudTest.getStudents()), "[2, 1, 0, 0, 0]");
    cloudTest.emptyCloud();
    assertEquals(Arrays.toString(cloudTest.getStudents()), "[0, 0, 0, 0, 0]");
  }
}
