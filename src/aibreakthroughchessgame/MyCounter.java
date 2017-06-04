/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

/**
 *
 * @author user
 */

import javax.swing.JInternalFrame;

import java.awt.event.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;


/* Used by InternalFrameDemo.java. */
public class MyCounter extends JInternalFrame implements Runnable{
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;
    private Timer timer;
    private Thread thread;
    private int seconds;
    private GameTask task;
    private CounterPanel countboard;
    public MyCounter(int seconds , GameTask task) {
        super("MyCounter #" + (++openFrameCount), 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
        this.timer = new Timer();
        this.thread = new Thread(this);
        this.task = task;
        this.seconds = seconds;
        this.countboard = new CounterPanel(); // here
        initCmp();
        //...Create the GUI and put it in the window...
        //...Then set the window size or call pack...
        setSize(100,100);
        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
    }
    public void initCmp(){
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER,countboard);
        this.countboard.setVisible(true);
    }
    public void start(){
        this.thread.start();
    }
    public void dipose(){
        this.setVisible(false);
        this.getParent().remove(this);
    }
    public void cancel(){
        this.timer.cancel();
    }

    @Override
    public void run() {
        this.timer.schedule(new countTask(this.seconds,this.task),0,1000);
    }
    class countTask extends TimerTask{
        private int seconds;
        private int Init = 0;
        private JInternalFrame InnerFrame;
        private GameTask task;
        public countTask(int seconds ,  GameTask task ){
            this.seconds = seconds ;
            this.task = task;
        }
        @Override
        public void run() {
            countboard.showText(Integer.toString(this.seconds));
            if(Init == seconds){
                dipose();
                this.cancel();
                this.task.Task();
            }
            this.seconds--;
        }
    }
}
