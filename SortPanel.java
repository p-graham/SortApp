/*******************************************************************/
// Phil Graham   
// Assignment 3
// Due: 07/28/14
// File: SortPanel.java
// Description: Contains GUI and algorithm methods for Sort Animation
/*******************************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.PAGE_AXIS;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

//*****************************************************************************
//Name:SortPanel
//Purpose:Contains the button panels and the graph panel
//*****************************************************************************
class SortPanel extends JPanel
{
    private SortAnimationPanel panel;//The graph panel
    private ButtonPanel buttPanel;//First row of Buttons
    private ButtonPanel2 buttsPanel;//Second row of Buttons
    private static final int PANEL_WIDTH = 300;//dimensions of the graph
    private static final int PANEL_HEIGHT = 400;
    private int[] theArray = new int[300];//the Array in question
    private JButton popButt, sortButt, pauseButt, stopButt;// The JButtons
    private JComboBox comboBox, startBox, speedBox;//The JComboBoxes
    private String[] comboStrings = {"Quick", "Insertion", "Bubble", "Shell"};//The sort options
    private String[] startStrings = {"Random", "Ascending", "Descending"};//The array generation options
    private String[] speedStrings = {"Fast", "Normal", "Slow"};//Speed options
    private String comboSelect = "Quick";//Default values for combo boxes
    private String startSelect = "Random";
    private String speedSelect = "Fast";
    private int speed = 10;//Default speed
    private boolean pause = false;//pause boolean
    private Thread thread;// Thread object for runnable
    private boolean isRunning = true;// stop boolean
    
    //*****************************************************************************
    //Name:SortPanel() -- constructor
    //Purpose:Allows for creation of new SortPanel objects
    //*****************************************************************************
    public SortPanel()
    {
        //set the panels vertically
        this.setLayout(new BoxLayout(this,PAGE_AXIS));
        panel = new SortAnimationPanel();
        buttPanel = new ButtonPanel();
        buttsPanel = new ButtonPanel2();
        add(panel);
        add(buttPanel);
        add(buttsPanel);
    }
    
    //*****************************************************************************
    //Name:PopListener
    //Purpose:Populates the array and paints it on the graph
    //*****************************************************************************
    class PopListener implements ActionListener
    {
        
        int x = 0;
        //Create the random variable
        Random rn = new Random();
        public void actionPerformed(ActionEvent event)
        {
            //For each int in theArray
            for(int i = 0; i < 300; i++)
            {
                //Assign a random number between 0-400
                x = rn.nextInt() % 400;
                if(x<0)
                    x = x * -1;
                theArray[i] = x;                
            }
            if(startSelect.equals("Ascending"))
            {
                Arrays.sort(theArray);
            }
            else if(startSelect.equals("Descending"))
            {
                Arrays.sort(theArray);
                for(int i = 0; i < theArray.length / 2; i++)
                {
                    int temp = theArray[i];
                    theArray[i] = theArray[theArray.length - i - 1];
                    theArray[theArray.length - i - 1] = temp;
                }
            }
            //repaint the panel
            repaint();
            //Ghost/unghost buttons
            popButt.setEnabled(false);
            sortButt.setEnabled(true);
            
            
        }
            
        
    }
    
    //*****************************************************************************
    //Name:SortListener
    //Purpose:Starts the runnable Thread
    //*****************************************************************************
    class SortListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            isRunning = true;//don't let things be stopped
            sortButt.setEnabled(false);//ghost
            thread = new Thread(new SortAnimationPanel());//run the Thread!
            thread.start();//run it for real!
            popButt.setEnabled(true);//unghost
        }
    }
    
    //*****************************************************************************
    //Name: ComboListener
    //Purpose: gets the selection from comboBox
    //***************************************************************************** 
    class ComboListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            JComboBox cb;
            cb = (JComboBox)event.getSource();
            comboSelect = (String)cb.getSelectedItem();
        }
    }
    
    //*****************************************************************************
    //Name: StartListener
    //Purpose: gets the starting position of the array
    //*****************************************************************************
    class StartListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            JComboBox cb;
            cb = (JComboBox)event.getSource();
            startSelect = (String)cb.getSelectedItem();
        }
    }
    
    //*****************************************************************************
    //Name: SpeedListener
    //Purpose: gets the speed from the speedBox
    //*****************************************************************************
    class SpeedListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            JComboBox cb;
            cb = (JComboBox)event.getSource();
            speedSelect = (String)cb.getSelectedItem();
            
            if(speedSelect.equals("Fast"))
                speed = 10;
            else if(speedSelect.equals("Normal"))
                    speed = 100;
                else if(speedSelect.equals("Slow"))
                        speed = 300;
        }
    }
    
    //*****************************************************************************
    //Name: PauseListener
    //Purpose: pauses the sort when pushed
    //*****************************************************************************
    class PauseListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(pause == false)
            {
                thread.suspend();
                pause = true;
            }
            else if(pause == true)
            {
                thread.resume();
                pause = false;
            }
        }
    }
    
    //*****************************************************************************
    //Name:StopListener
    //Purpose:Stops the sort when pressed
    //*****************************************************************************
    class StopListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            isRunning = false;
        }
    }

    //*****************************************************************************
    //Name: ButtonPanel
    //Purpose: Holds the first row of Buttons
    //*****************************************************************************
    class ButtonPanel extends JPanel
    {        
        public ButtonPanel()
        {
            //Declare ActionListeners
            ActionListener popListener = new PopListener();
            ActionListener sortListener = new SortListener();
            ActionListener comboListener = new ComboListener();
            
            //finish declaring the doodads
            popButt = new JButton("Populate Array");
            sortButt = new JButton("Sort");
            sortButt.setEnabled(false);
            comboBox = new JComboBox(comboStrings);
            
            //Assign action listener to respective thing
            popButt.addActionListener(popListener);
            sortButt.addActionListener(sortListener);
            comboBox.addActionListener(comboListener);
            
            //Add them things
            add(panel);
            add(popButt);
            add(sortButt);
            add(comboBox);
            
            
        }
    }
    
    //*****************************************************************************
    //Name: ButtonPanel2
    //Purpose: contains the second row of Buttons
    //*****************************************************************************
    class ButtonPanel2 extends JPanel
    {
        public ButtonPanel2()
        {
            //Declare ActionListeners
            ActionListener startListener = new StartListener();
            ActionListener speedListener = new SpeedListener();
            ActionListener pauseListener = new PauseListener();
            ActionListener stopListener = new StopListener();
            
            //finish declaring objects
            startBox = new JComboBox(startStrings);
            speedBox = new JComboBox(speedStrings);
            pauseButt = new JButton("Pause/Resume");
            stopButt = new JButton("Stop");
            
            //Turn on the buttons
            startBox.addActionListener(startListener);
            speedBox.addActionListener(speedListener);
            pauseButt.addActionListener(pauseListener);
            stopButt.addActionListener(stopListener);
            
            //Paint the buttons!
            add(startBox);
            add(speedBox);
            add(pauseButt);
            //add(stopButt);
        }
    }
    
    //*****************************************************************************
    //Name: SortAnimationPanel
    //Purpose: This is the animated graph panel that gets drawn on
    //*****************************************************************************
    class SortAnimationPanel extends JPanel implements Runnable
    {
        
        private int[] arrayRef = theArray;//reference to the array
        Border empty;//empty border
        
        //Constructor
        public SortAnimationPanel() 
        {
            //kill the edges
            empty = BorderFactory.createEmptyBorder();
            
            //Set the dimensions/preferences
            this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            this.setBorder(empty);
            this.setBackground(Color.white);
        }
        
        //Overided paintComponent method
        @Override public void paintComponent(Graphics g) 
        {
            super.paintComponent(g);    // paints background
            g.setColor(Color.blue);
            if(theArray != null)
            {
                for(int i = 0; i < 300; i++)//for each int
                   g.drawLine(i,400,i,400 - theArray[i]);//paint a line
            }
        }
        @Override
        public void run() 
        {
            //*******************
            //**************************
            //***************************************
            /*****************************************************************/
            //New Algorithms go here
            /*****************************************************************/
            //****************************************
            //***************************
            //********************
            if(comboSelect.equals("Quick"))
            {
                //call quicksort class
                Quicksort q = new Quicksort();
                try {
                    q.sort(theArray);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SortPanel.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
            else if (comboSelect.equals("Insertion"))
            {
                int n = theArray.length;
                for (int j = 1; j < n; j++) 
                {
                    int key = theArray[j];//for comparison
                    int i = j-1;
                    while ( (i > -1) && ( theArray [i] > key ) ) {
                        theArray [i+1] = theArray [i];
                        i--;
                    }
                    theArray[i+1] = key;
                    panel.repaint();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SortPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else if(comboSelect.equals("Bubble"))
            {
                int j;
                boolean flag = true;   //swap flag
                int temp;   

                while ( flag )
                {
                    flag= false;   
                    for( j=0;  j < theArray.length -1;  j++ )
                    {
                        if ( theArray[ j ] > theArray[j+1] )   
                        {
                            temp = theArray[ j ];                //swap elements
                            theArray[ j ] = theArray[ j+1 ];
                            theArray[ j+1 ] = temp;
                            flag = true;              //there was a swap 
                            panel.repaint();
                            try{ 
                                Thread.sleep(speed);}
                            catch (InterruptedException ex){ 
                                Logger.getLogger(SortPanel.class.getName()).log(Level.SEVERE, null, ex);}                            
                        } 
                    } 
                } 
            }
            
            else if(comboSelect.equals("Shell"))
            {
                int inner, outer;
                int len = theArray.length;
                int temp;
                //find initial value of h
                int h = 1;
                while (h <= len / 3)
                    h = h * 3 + 1; // (1, 4, 13, 40, 121, ...)

                while (h > 0) // decreasing h, until h=1
                {
                // h-sort the file
                for (outer = h; outer < len; outer++) 
                {
                    temp = theArray[outer];
                    inner = outer;
                    // one subpass (eg 0, 4, 8)
                    while (inner > h - 1 && theArray[inner - h] >= temp) 
                    {
                        theArray[inner] = theArray[inner - h];
                        inner -= h;
                        panel.repaint();
                        try{ 
                            Thread.sleep(speed);}
                        catch (InterruptedException ex){ 
                            Logger.getLogger(SortPanel.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    theArray[inner] = temp;
                }
                h = (h - 1) / 3; // decrease h
                }
            
            }    
        }
        
        //*****************************************************************************
        //Name: Quicksort
        //Purpose: This sub-class exists to allow quicksort to be recursive
        //*****************************************************************************
        public class Quicksort  
        {
        private int[] numbers;
        private int number;

        public void sort(int[] values) throws InterruptedException 
        {
            // check for empty or null array
            if (values ==null || values.length==0)
                return;
            
            this.numbers = values;
            number = values.length;
            quicksort(0, number - 1);
        }

        private void quicksort(int low, int high) throws InterruptedException 
        {
            int i = low, j = high;
            // Get pivot element from middle
            int pivot = numbers[low + (high-low)/2];

            // Split into two lists
            while (i <= j) 
            {
                while (numbers[i] < pivot) 
                    i++;
                while (numbers[j] > pivot) 
                    j--;
      
                    if (i <= j) 
                    {
                        exchange(i, j);
                        i++;
                        j--;
                    }
                    //Repaint the lines and pause the loop for a bit
                    panel.repaint();
                    Thread.sleep(speed);
                }
                // Recursion
                if (low < j)
                    quicksort(low, j);
                if (i < high)
                    quicksort(i, high);
            }

            private void exchange(int i, int j) 
            {
                int temp = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = temp;
            }
        } 
    }
}
