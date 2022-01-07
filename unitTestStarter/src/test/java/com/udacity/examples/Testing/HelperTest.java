package com.udacity.examples.Testing;

import junit.framework.TestCase;
import org.junit.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

public class HelperTest {

	@Test
    public void test(){
        Assert.assertEquals(7,7);
    }

    @Test
    public void verify_getCount(){
        List<String> empNames= Arrays.asList("Sareeta", "Udacity");
        long actual = Helper.getCount(empNames); //Alt+Intro
        Assert.assertEquals(2,actual);
    }

    @Ignore
    @Test
    public void verify_getStats(){
        List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        List<Integer> expectedList = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        IntSummaryStatistics stats = Helper.getStats(yrsOfExperience);
        Assert.assertEquals(19,stats.getMax());
        Assert.assertEquals(1,stats.getMin());
        Assert.assertEquals(expectedList,yrsOfExperience);
    }

    @Test
    public void compare_arrays(){
	    int[] actualYrs={10,14,2};
	    int[] expectedYrs={10,14,2};
	    Assert.assertArrayEquals(expectedYrs,actualYrs);
    }

    @Test
    public void verify_getMergedList(){
        List<String> empNames= Arrays.asList("Sareeta", "Udacity");
        String mergedList = Helper.getMergedList(empNames);
        Assert.assertEquals("Sareeta, Udacity",mergedList);
    }

    @Before
    public void init(){
        System.out.println("runs before each method");
    }

    @BeforeClass
    public static void setup(){
        System.out.println("runs before each class");
    }

    @After
    public void initEnd(){
        System.out.println("runs after each method");
    }

    @AfterClass
    public static void tearDown(){
        System.out.println("runs after each class");
    }

//    @Before
//    public void init() {
//        System.out.println("init executed"); }
//    @After
//    public void teardown() {
//        System.out.println("teardown executed"); }
//    @Test
//    public void test() {
//        assertEquals("test", "test1");    }
//    @Test
//    public void validate_getCount() {
//        List<String> empNames = Arrays.asList("sareeta", "", "john","");
//        assertEquals(2, Helper.getCount(empNames));
//    }
//    @Test
//    public void validate_3lengthString() {
//        List<String> empNames = Arrays.asList("sareeta", "", "Jeff","sam");
//        assertEquals(2, Helper.getStringsOfLength3(empNames));
//    }
//    @Test
//    public void verify_list_is_squared(){
//        List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
//        List<Integer> expected = Arrays.asList(169, 16, 225, 36, 289, 64, 361, 1, 4, 9);
//        assertEquals(expected, Helper.getSquareList(yrsOfExperience));
//    }
//    @Test
//    public void verify_merged_list(){
//        List<String> empNames = Arrays.asList("sareeta", "", "john","");
//        assertEquals("sareeta, john", Helper.getMergedList(empNames));
//    }
//    @Test
//    public void verify_getMax(){
//        List<Integer> empLevel = Arrays.asList(3,3,3,5,7,2,2,5,7,5);
//        assertEquals(7, Helper.getStats(empLevel).getMax());
//    }
//    // This method must be public and static
//    @BeforeClass
//    public static void initClass() {
//        System.out.println("init Class executed");
//    }
//    @AfterClass
//    public static void teardownclass() {
//        System.out.println("teardown Class executed");
//    }
//    @Test
//    public void verify_ArrayListTest(){
//        int[] yrsOfExperience = {13,4,15,6,17,8,19,1,2,3};
//        int[] expected = {13,4,15,6,17,8,19,1,2,3};
//        assertArrayEquals(expected, yrsOfExperience);
//    }

}
