import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MaxwellsDemon extends JFrame implements ActionListener{

    double deltat = 1000; //  in seconds
    Timer timer;

    public static void main( String[] args ){
        System.out.println("Start");
        new MaxwellsDemon();
    }

    public MaxwellsDemon(){
        System.out.println("MaxwellsDemon: Starting...");

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setTitle("Maxwell's Demon");
        setBackground( Color.WHITE );

        timer = new Timer((int)(1 * deltat), this);
        timer.start();

        setSize( 600,600 );
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("click");
        repaint();
    }
}