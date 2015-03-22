/*******************************************************************/
// Phil Graham  
// Assignment 3
// Due: 07/28/14
// File: SortApp.java
// Description: Contains main routine for Sort Animation
/*******************************************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

//*****************************************************************************
//Name:SortApp
//Purpose:This is the big frame that holds all the panels
//*****************************************************************************
public class SortApp extends JFrame
{
  //Frame dimensions
  private static final int FRAME_WIDTH = 620;
  private static final int FRAME_HEIGHT = 500;
  
  //lefty and righty panels
  private SortPanel p1,p2;
  

  //*****************************************************************************
  //Name:SortApp -- constructor
  //Purpose: Creates new SortApp frames
  //*****************************************************************************
  public SortApp(String title) throws FileNotFoundException
  {
        //Set the title
        super(title);
        //create the big panels
        p1 = new SortPanel();
        p2 = new SortPanel();
        //add the big panels
        add(p1, BorderLayout.EAST);
        add(p2, BorderLayout.WEST);
       
  }
  
    //*****************************************************************************
    //Name: this is the main routine
    //Purpose: this is the main routine
    //*****************************************************************************
    public static void main(String[] args) throws FileNotFoundException
    {
        //make the frame
        JFrame frame = new SortApp("Sorting Animation");
        //set close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set size
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        //Don't let the frame be invisible
        frame.setVisible(true);
    }
}
      

