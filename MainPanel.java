import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainPanel extends JPanel implements ActionListener {
    final static int maxx = 600;
    final static int maxy = 600;

    final static int wallThickness = 20;
    final static int wallLeft = maxx/2-wallThickness/2;
    final static int wallRight = maxx/2+wallThickness/2;

    Timer timer;
    double deltat = .1; //  in seconds
    int particleCount = 0;
    ArrayList<Particle> particles = new ArrayList<Particle>();

    public MainPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(maxx, maxy));
        setBackground(Color.WHITE);
        setVisible(true);

//        particleCount = 20;
//        for ( int i=0; i<particleCount; i++ ){
//            if (i%4 == 0) {
//                particles.add(new Particle(maxx, maxy, wallThickness,true, true));
//            }
//            else if (i%4 == 1) {
//                particles.add(new Particle(maxx, maxy, wallThickness,true, false));
//            }
//            else if (i%4 == 2) {
//                particles.add(new Particle(maxx, maxy, wallThickness,false, true));
//            }
//            else{
//                particles.add(new Particle(maxx, maxy, wallThickness,false, false));
//            }
//            particles.get(i).getInfo();
//        }
        timer = new Timer((int)(1000 * deltat),this );
        timer.start();

    }

    public void addParticle() {
        System.out.println("Adding 4 Particles");
        particles.add(new Particle(maxx, maxy, wallThickness, true, true));
        particles.add(new Particle(maxx, maxy, wallThickness, true, false));
        particles.add(new Particle(maxx, maxy, wallThickness, false, true));
        particles.add(new Particle(maxx, maxy, wallThickness, false, false));
        particleCount+=4;
    }
    public void moveAll()
    {
        for ( int i=0; i<particleCount; i++ ) { particles.get(i).move(deltat); }
        checkIntersection();
    }

    public void checkIntersection()
    {
        for(Particle p : particles){
            if(wallLeft <= p.getX()+5 && p.getX() <= wallRight){
                p.flipX();
            }
        }
    }

    public double getLeftTemperature(){
        if(particleCount == 0){
            return 0;
        }
        double totalTempSquared = 0;
        double count = 0;

        for(Particle p : particles){
            if(p.getIsLeft() == true){
                totalTempSquared += p.getVelocitycm() * p.getVelocitycm();
                count++;
            }
        }
        return totalTempSquared/count;
    }

    public double getRightTemperature(){
        if(particleCount == 0){
            return 0;
        }
        double totalTempSquared = 0;
        double count = 0;

        for(Particle p : particles) {
            if (p.getIsLeft() == false) {
                totalTempSquared += p.getVelocitycm() * p.getVelocitycm();
                count++;
            }
        }
        return totalTempSquared/count;
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
            particles.get(i).drawMe(g);
        }
        //particles[0].getInfo();

        g.setColor(Color.BLACK);
        g.fillRect( wallLeft, 0, wallThickness, maxy );

        System.out.println("Paint");
    }
}
