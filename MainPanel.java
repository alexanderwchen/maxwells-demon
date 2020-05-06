import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainPanel extends JPanel implements ActionListener {
    final static int maxx = 600;
    final static int maxy = 600;

    final static int wallThickness = 30;
    final static int wallLeft = (maxx-wallThickness)/2;
    final static int wallRight = (maxx+wallThickness)/2;

    final static int doorThickness = 50;
    final static int doorTop = (maxy-doorThickness)/2;
    final static int doorBottom = (maxy+doorThickness)/2;

    boolean isOpen = false;

    Timer timer;
    double deltat = .1; //  in seconds
    int particleCount = 0;
    ArrayList<Particle> particles = new ArrayList<Particle>();

    public MainPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(maxx, maxy));
        setBackground(Color.WHITE);
        setVisible(true);

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

            if(p.getX() + p.getDiameter() > wallLeft && p.getX() < wallRight + p.getDiameter()){
                p.flipX();

                if(doorBottom >= p.getY() + p.getDiameter() && p.getY() >= doorTop && isOpen){
                    p.flipX();
                    p.flipSides();
                }
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

    public void click(){
        isOpen = !isOpen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource()==timer ) {
            moveAll();
        }
    }

    @Override
    public void paintComponent( Graphics g ){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect( wallLeft, 0, wallThickness, maxy );

        if(isOpen == true) {
            g.setColor(Color.WHITE);
            g.fillRect(wallLeft, doorTop, wallThickness, doorThickness);
        }

        for ( int i=0; i<particleCount; i++ ) {
            particles.get(i).drawMe(g);
        }
    }
}
