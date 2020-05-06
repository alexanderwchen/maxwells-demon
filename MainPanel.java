import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    int maxx = 600;
    int maxy = 600;
    Timer timer;
    double deltat = .1; //  in seconds
    int particleCount;
    Particle[] particles;

    public MainPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(maxx, maxy));
        setBackground(Color.WHITE);
        setVisible(true);

        particleCount = 50;
        particles = new Particle[particleCount];
        for ( int i=0; i<particleCount; i++ ){
            if (i%2 == 0) {
                particles[i] = new Particle(maxx, maxy, true);
            }
            else{
                particles[i] = new Particle(maxx, maxy, false);
            }
            particles[i].getInfo();
        }


        timer = new Timer((int)(1000 * deltat),this );
        timer.start();


    }

    public void moveAll()
    {
        for ( int i=0; i<particleCount; i++ ) { particles[i].move(deltat); }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource()==timer ) {
            //System.out.println("New Timer");
            moveAll();
           // repaint();
        }
    }

    @Override
    public void paintComponent( Graphics g ){
        super.paintComponent(g);
        for ( int i=0; i<particleCount; i++ ) {
            particles[i].drawMe(g);
        }
        particles[0].getInfo();
        System.out.println("Paint");
    }
}
