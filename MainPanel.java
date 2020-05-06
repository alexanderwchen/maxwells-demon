import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainPanel extends JPanel implements ActionListener {
    final static private int maxx = 600;
    final static private int maxy = 600;

    final static private int wallThickness = 30;
    final static private int wallLeft = (maxx-wallThickness)/2;
    final static private int wallRight = (maxx+wallThickness)/2;

    final static private int doorThickness = 50;
    final static private int doorTop = (maxy-doorThickness)/2;
    final static private int doorBottom = (maxy+doorThickness)/2;

    private Timer timer;
    final static private double deltat = .1; //  in seconds

    private int particleCount = 0;
    private ArrayList<Particle> particles = new ArrayList<Particle>();

    private boolean isOpen = false;

    public MainPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(maxx, maxy));
        setBackground(Color.WHITE);
        setVisible(true);

        timer = new Timer((int)(1000 * deltat),this );
        timer.start();

    }

    public void addParticle() {
        particles.add(new Particle(maxx, maxy, wallThickness, true, true));
        particles.add(new Particle(maxx, maxy, wallThickness, true, false));
        particles.add(new Particle(maxx, maxy, wallThickness, false, true));
        particles.add(new Particle(maxx, maxy, wallThickness, false, false));
        particleCount+=4;
    }

    private void moveAll()
    {
        for ( int i=0; i<particleCount; i++ ) { particles.get(i).move(deltat); }
        checkIntersection();
    }

    private void checkIntersection()
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

    public int getLeftParticles(){
        int count = 0;

        for(Particle p : particles) {
            if (p.getIsLeft() == false) {
                count++;
            }
        }
        return count;
    }

    public int getRightParticles(){
        return particleCount - getLeftParticles();
    }

    public void click(){
        isOpen = !isOpen;
    }

    public void reset(){
        particleCount = 0;
        particles.clear();
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

        for ( Particle p : particles) {
            p.drawMe(g);
        }
        if(!isOpen) {
            g.setColor(Color.BLACK);
            g.fillRect(wallLeft, 0, wallThickness, maxy);
        }

        else{
            g.fillRect(wallLeft, 0, wallThickness, doorTop);
            g.fillRect(wallLeft, doorBottom, wallThickness, maxy-doorBottom);
        }

    }
}
