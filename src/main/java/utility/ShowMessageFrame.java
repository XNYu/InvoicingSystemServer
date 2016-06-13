package utility;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class ShowMessageFrame extends javax.swing.JFrame implements MouseMotionListener {
    private JLabel text;
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screensize = tk.getScreenSize();
    int height = screensize.height;
    int width = screensize.width;
    private String str = null;
    Color background = new Color(0,0,0,0.0f);
	Color transparent = new Color(0,0,0,0.3f);
	Color white = new Color(245,245,245);
	Color deepGray = new Color(26,32,45);
	Color gray = new Color(41,48,62);
	Color blue = new Color(16,133,253);
    public ShowMessageFrame(String str,int x,int y) {
        this.str = str;
        this.setUndecorated(true);
        this.setBackground(background);
        this.setLocation(x,y);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initGUI();
            }
        }).start();
    }

    private void initGUI() {
        try {
            setUndecorated(true);
//            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            {
                text = new JLabel("<html>" + str + "</html>", JLabel.CENTER);
                getContentPane().add(text, BorderLayout.CENTER);
                text.setFont(new Font("黑体", Font.PLAIN, 15));
                text.setBackground(new java.awt.Color(0,0,0,0.4f));
            }
            pack();
//            setBounds(width / 2 - 180, height - 150, 360, 100);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
    	new ShowMessageFrame("输入名称已存在",60,80);
    	
    }

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point mousepoint = MouseInfo.getPointerInfo().getLocation();
		System.out.println(mousepoint.x+"\t"+mousepoint.y);
		
	}

}