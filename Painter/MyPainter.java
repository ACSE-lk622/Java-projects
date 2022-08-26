package tw.losfre.tutor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;

import tw.losfre.myclass.MyClock;
import tw.losfre.myclass.MyDrawer;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPainter extends JFrame {
    private JButton clear,undo,redo,saveJPG,saveobj,loadobj;
    private MyDrawer mydrawer;
    private MyClock myClock; 
    public MyPainter (){
        super("Signature");
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout()); 
        myClock = new MyClock();
        clear = new JButton("clear");
        undo = new JButton("undo");
        redo = new JButton("redo");
        saveJPG = new JButton("Save");
        saveobj = new JButton("Saveobj");
        loadobj = new JButton("loadobj");
        top.add(myClock); top.add(clear);top.add(undo);top.add(redo);top.add(saveJPG);top.add(saveobj);top.add(loadobj);
        add(top,BorderLayout.NORTH);
        mydrawer = new MyDrawer();
        add(mydrawer,BorderLayout.CENTER);
        setSize(640, 480);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setListener();//建立在建構式，隨時隨地都在聽
    }
    
    private void setListener(){
        clear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        undo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        redo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });
        saveJPG.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                saveJPG();
            }
        });
        saveobj.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                saveobj();
            }
        });
        loadobj.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                loadobj();
            }
        });
    }
    private void clear(){
        mydrawer.clear();
    }
    private void undo(){
        mydrawer.undo();
    }   
    private void redo(){
        mydrawer.redo();
    }
    private void saveJPG(){
        BufferedImage img  = new BufferedImage(mydrawer.getWidth(),mydrawer.getHeight(),BufferedImage.TYPE_INT_RGB);//給予記憶體一個空間
        Graphics g = img.createGraphics();//這個空間創造出一個圖形 
        mydrawer.paint(g);//渲染
        try{
        ImageIO.write(img, "jpg",new File("D:/Losfre/myjava/dir1/Losfreimage.jpg"));
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    private void saveobj(){
        JFileChooser jfc = new JFileChooser();
        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            File file = jfc.getSelectedFile();
            try{
            mydrawer.saveLines(file);
            JOptionPane.showMessageDialog(null, "Save successfully"); 
            }
            catch(Exception e){
            JOptionPane.showMessageDialog(null, "Save failedly "); 
            }
        }
    }
    private void loadobj(){
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File file = jfc.getSelectedFile();
            try{
                mydrawer.loadLines(file); 
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Load failedly "); 
            }
        }
    }
    public static void main(String [] args ) 
    {
        new MyPainter();    
    }
}
