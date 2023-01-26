/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gantt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Reyn
 */
public class func {
    //0 - > priority
    //1 -> burst time
    //2 -> waiting time
    //3 - > return time
    HashMap<Integer, Integer> process = new HashMap();
    JLabel sublabel;
    int[][] proc; //line 106
    static boolean type = true;
    boolean identifier = false;
    public int processindex = 0;
    public int dataindex = 0;
    public int globalX = 3;
    static public int globalY;
    int percentage;
    int defaultcounter = 0;
    public void addNew(int burst){//this basically add new process and  burst time
        process.put(processindex,burst);
        processindex++;
    }
    public void showData(javax.swing.JTable tabol ){//simply display data to the table
        tabol.setValueAt(dataindex, dataindex, 0);
        tabol.setValueAt(process.get(dataindex), dataindex, 1);
        dataindex++;
    }
    public void compute(javax.swing.JTable tabol ,int index){//the code here is for computation
        convert(index);
        try{
        
        for(int i = 0; i < proc.length;i++){
            proc[i][2] = identifier ? proc[i-1][3] :proc[i][3] ;
            proc[i][3] = proc[i][2] + proc[i][1];
            identifier = true;
        }
        
        for(int i = 0; i < process.size();i++){
        tabol.setValueAt(proc[i][0], i, 0);
        tabol.setValueAt(proc[i][1], i, 1);
        tabol.setValueAt(proc[i][2], i, 2);
        tabol.setValueAt(proc[i][3], i, 3);
        }
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    public void convert(int index){
        proc = new int[process.size()][4];
        int count = 0;
    for(Map.Entry<Integer,Integer> entry : process.entrySet()){
        proc[count][0] = entry.getKey();
        proc[count][1] = entry.getValue();
        count++;
    }
    Arrays.sort(proc, (a, b) -> Double.compare(a[index], b[index]));
    }
    public void creation(javax.swing.JPanel panelado,int identif ){//here is the creation of gantt chart
 
        globalX=0;
       identifier = false ;
       //used identifier again as one time loop inside a loop
        for(int i =0; i< proc.length;i++){
            percentage += proc[i][2];
         }       
       
        for(int i =0; i< proc.length;i++){
          
            double finalpercentage = (double)proc[i][2] /(double) percentage;
            if(finalpercentage < (double) 0.06){
                finalpercentage = identifier ? finalpercentage = 0.06 : 0 ;
                defaultcounter++;
                identifier = true;
            }else{
               if (defaultcounter > 0 ){
                if(finalpercentage > 0.18){
                    System.out.println("IM not alive?");
                    for(double x =finalpercentage; x > 0.24 || defaultcounter > 0;x -= 0.06){
                        finalpercentage -= 0.06;
                        defaultcounter = defaultcounter - 1;
                        System.out.println("defaultcounter" + defaultcounter);
                    }
                    
                    
                }
            }else{
                   
               }
            }
            //skip 
            
         JLabel process = type ? new JLabel("Prc{"+proc[i][0]+"}") : new JLabel("Prc{"+proc[i][0]+"}");
         //eto yung process sa loob ng panel 
         JLabel sublabel = new JLabel(Integer.toString(proc[i][identif]));
         //sub label naman yung number sa baba 
         JPanel paintPanel = new JPanel();
         //paint panel is yung panel na gantt chart sya yung may border
        paintPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        //this is how i create the border
        panelado.add(paintPanel,0);
        //panelado is a parameter of creaion method (encapulation or transmutation)
        // here it simply says that paintPanel should be added inside the panelado
        panelado.add(sublabel,0);
        //same here sublabel should be inside panelado right under the paint (Note the positioning was not yet determined here)
        //inadd lang sya kasi pwede ka mag add ng mag add sa loob ng panel ng hindi naka assign ang position
        // but di mo rin yon makiktia kase wala iandd mo lang sya di sya naka show di sya visible wala syang size :D
        paintPanel.add(process,0);
        //pansinin mo dito ipinasok natin si proccess sa loob ni paintpanel tas si paint panel naman pinasok natin
        //sa loob ni panelado sa line 200 :D 
        
        //sana oll nag papasukan hehez pakyu
        
        //X Y WIDTH HEIGHT
        process.setBounds(5,0,50,25);
        //set bounds is determining the x,y,width,and height
        // first 2 param is the positioning and the next 2nd is for size
        paintPanel.setBounds(globalX,globalY,(int)((double) (panelado.getWidth()-8) *(double) finalpercentage),25);
        //same here dito ko na sinize yung paintpanel na may borded na black sabi ko yung x ko is global x (global x every loop mag a adjust)
        // means every creation ng isang panel yung next creation is adjust na yung position
        // yung 3rd parameter ayan yung size nung panel  dapat
        // kinuha ko yung width ng panelado tas kinuha ko din yung percentage ng gagawing panel
        // para makuha natin yung exact size
        if(proc[i][2] == 0 ){
            //etong if is para lang amy sariling pwesto si 0 sa position nya
            //kasi dynamic design yung ginawa naten auto adjust once na lumaki yung size nung isa
            // automatic na lalaki alhat ng size kasi wala akong constant number for size
            // ;ahat sila relative to each other like the width of paint panel is relative to panelado
            // whenever panelado increase size the paintpanel increases also.
            //so yung 0 ginawan ko lang sya ng sarili nyang pwesto kasi mawawala sya sa pwesto pag isinama 
            //naten sya sa dynamic positioning ng mga iba pang values
        sublabel.setBounds(globalX + paintPanel.getWidth(),globalY + 25,200,25);
        
        //this is the positioning for 0
        }
        else{
            //and now kapag hindi na 0 yung value eto na yung position nya
            //mapupunta yung nubmer at the end of the length of certain panel :D 
            // pero notice mo yung 0 may sarili syang pwesto and once lang sya mag rurun kahit naka for loop oyat
         sublabel.setBounds(globalX + paintPanel.getWidth()-18,globalY + 25,200,25);
        }globalX += paintPanel.getWidth() + 1; 
            //etong global x eto yung ginamit sa global variables means every finish ng 1 loop sa for loop mag a adjust automatically
            //para mapunta yung next position ng panel sa end of another panel.
        }
        //and eto after ng isang chart automatic na bababa yung position para sa next chart :D  
            }
    
        public void creationRR(javax.swing.JPanel panelado ){//here is the creation of gantt chart
 int a = 0;
 int finalloc = 0;
 double finalpercentage = 0.04;
        globalX=0;
       identifier = false ;
       //used identifier again as one time loop inside a loop
        for(int i =0; i< proc.length;i++){
            percentage += proc[i][1];
         }       
        
        for(int i = 0; i < percentage;){
            
            if (a == proc.length){
                    a = 0;
                }
               
            if(proc[a][1] == 0){
               a++;
            }else{
                proc[a][1] = proc[a][1] - 1;
                System.out.println(proc[a][0] +" "+ proc[a][1]); 
                JLabel process = new JLabel("{"+proc[a][0]+"}");
         //eto yung process sa loob ng panel 
         sublabel = new JLabel(""+percentage);
         //sub label naman yung number sa baba 
         JPanel paintPanel = new JPanel();
         //paint panel is yung panel na gantt chart sya yung may border
        paintPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        //this is how i create the border
        panelado.add(paintPanel,0);
        panelado.add(sublabel,0);
        paintPanel.add(process,0);
        process.setBounds(5,0,50,25);
        paintPanel.setBounds(globalX,globalY,(int)((double) (panelado.getWidth()-8) *(double) finalpercentage),25);
        finalloc += paintPanel.getWidth();
        if(proc[a][2] == 0){
        
        
        }
        else{
           
         
        }globalX += paintPanel.getWidth() + 1; 
                if (a == proc.length){
                    a = 0;
                }else{
                    i++;
                a++;
                
                }
                
            }
            
        }
        
           sublabel.setBounds(globalX,globalY + 25,200,25);
           
        
        //and eto after ng isaobalY+=50;ng chart automatic na bababa yung position para sa next chart :D  
            } 
    


    
    //tangina mo thankyou di ako nakatulog  6:00 na ngayon ko lang natapos btw pag nabasa mo to chat mo ko ng "PAKYU TY " kase 
    //bawal malaman ng bebe ko to yari ako don na di ako naka tulog hehe 
    }

