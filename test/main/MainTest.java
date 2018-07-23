/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author INTERSAN
 */
public class MainTest {
    
    public MainTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   
    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = {"https://www.youtube.com/watch?v=KMU0tzLwhbE"};
        Main.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


   

    
}
