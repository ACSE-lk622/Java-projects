package tw.losfre.myclass;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.awt.Component;

public class MyDrawer extends JPanel // extend JPanel can be added 
{   private LinkedList<LinkedList<HashMap<String,Integer>>> lines,recycle;
    public MyDrawer(){
        setBackground(Color.YELLOW);
        MyListener myListener = new MyListener();
        addMouseListener(myListener);
        addMouseMotionListener(myListener);
        lines = new LinkedList<>();
        recycle = new LinkedList<>();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(4));
        for (LinkedList<HashMap<String,Integer>> line : lines){
            for (int i = 1;i < line.size();i++){
                HashMap<String,Integer> p1 = line.get(i-1);
                HashMap<String,Integer> p2 = line.get(i);
                g2d.drawLine(p1.get("x"),p1.get("y"),p2.get("x"),p2.get("y"));   
            }
        }

    }
    private class MyListener extends MouseAdapter{ //內部類別方便存取外部類別的屬性跟方法\
        @Override
        public void mousePressed(MouseEvent e) {
            HashMap<String,Integer> point = new HashMap<>();//點
            point.put("x",e.getX());point.put("y",e.getY());
            LinkedList<HashMap<String,Integer>> line = new LinkedList<>();
            line.add(point);//線
            lines.add(line);//多條線
            repaint();
            recycle.clear();
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            HashMap<String,Integer> point = new HashMap<>();
            point.put("x",e.getX());point.put("y",e.getY());
            lines.getLast().add(point);//點放在全部裡面的線的最後一條線，才可以畫，並且存點在裡面
            repaint();
        }
    }
    public void clear(){
        lines.clear();
        repaint();
    }
    public void undo(){
        if (lines.size()>0){
            recycle.add(lines.removeLast());//你丟我撿，for redo
            repaint();
        }
    }
    public void redo(){
        if(recycle.size()>0){
            lines.add(recycle.removeLast());
            repaint();
        }
    }
    public void saveLines (File saveFile) throws Exception{
        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(saveFile));
        oout.writeObject(oout);
        oout.flush();
        oout.close();
    }

    public void loadLines(File loadFile) throws Exception {
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(loadFile));
        Object obj = oin.readObject();
        oin.close();

        lines = (LinkedList<LinkedList<HashMap<String,Integer>>>)obj;
        repaint();
    }
}