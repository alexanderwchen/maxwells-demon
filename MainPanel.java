import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainPanel extends JPanel implements ActionListener {
    private static final int MAXX = 600;
    private static final int MAXY = 600;

    private static final int WALL_THICKNESS = 30;
    private static final int WALL_LEFT = (MAXX-WALL_THICKNESS)/2;
    private static final int WALL_RIGHT = (MAXX+WALL_THICKNESS)/2;

    private static final int DOOR_THICKNESS = 50;
    private static final int DOOR_TOP = (MAXY-DOOR_THICKNESS)/2;
    private static final int DOOR_BOTTOM = (MAXY+DOOR_THICKNESS)/2;

    private Timer timer;
    private static final double DELTAT = .1; //  in seconds

    private int particleCount = 0;
    private ArrayList<Particle> particles = new ArrayList<Particle>();

    private boolean isOpen = false;

    public MainPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(MAXX, MAXY));
        setBackground(Color.WHITE);
        setVisible(true);

        timer = new Timer((int)(1000 * DELTAT),this );
        timer.start();

    }

    public void addParticle() {
        particles.add(new Particle(MAXX, MAXY, WALL_THICKNESS, true, true));
        particles.add(new Particle(MAXX, MAXY, WALL_THICKNESS, true, false));
        particles.add(new Particle(MAXX, MAXY, WALL_THICKNESS, false, true));
        particles.add(new Particle(MAXX, MAXY, WALL_THICKNESS, false, false));
        particleCount+=4;
    }

    private void moveAll()
    {
        for ( int i=0; i<particleCount; i++ ) { particles.get(i).move(DELTAT); }
        checkIntersection();
    }

    private void checkIntersection()
    {
        for(Particle p : particles){

            if(p.getX() + p.getDiameter() >= WALL_LEFT && p.getX() <= WALL_RIGHT){
                p.flipX();

                if(DOOR_BOTTOM >= p.getY() + p.getDiameter() && p.getY() >= DOOR_TOP && isOpen){
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
        double totalTempSquaredAverage;
        double scaledTotalTempSquaredAverage;

        for(Particle p : particles){
            if(p.getIsLeft() == true){
                totalTempSquared += p.getVelocitycm() * p.getVelocitycm();
                count++;
            }
        }

        totalTempSquaredAverage = totalTempSquared/count;

        scaledTotalTempSquaredAverage =
                100 * ((totalTempSquaredAverage - particles.get(0).SLOWMIN * particles.get(0).SLOWMIN)/
                (particles.get(0).FASTMAX * particles.get(0).FASTMAX -
                        particles.get(0).SLOWMIN * particles.get(0).SLOWMIN));

        return scaledTotalTempSquaredAverage;
    }//Temperature is scaled to 0 to 100

    public double getRightTemperature(){
        if(particleCount == 0){
            return 0;
        }
        double totalTempSquared = 0;
        double count = 0;
        double totalTempSquaredAverage;
        double scaledTotalTempSquaredAverage;

        for(Particle p : particles){
            if(p.getIsLeft() == false){
                totalTempSquared += p.getVelocitycm() * p.getVelocitycm();
                count++;
            }
        }

        totalTempSquaredAverage = totalTempSquared/count;

        scaledTotalTempSquaredAverage =
                100 * ((totalTempSquaredAverage - particles.get(0).SLOWMIN * particles.get(0).SLOWMIN)/
                (particles.get(0).FASTMAX * particles.get(0).FASTMAX -
                        particles.get(0).SLOWMIN * particles.get(0).SLOWMIN));

        return scaledTotalTempSquaredAverage;
    }//Temperature is scaled to 0 to 100

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
            g.fillRect(WALL_LEFT, 0, WALL_THICKNESS, MAXY);
        }

        else{
            g.fillRect(WALL_LEFT, 0, WALL_THICKNESS, DOOR_TOP);
            g.fillRect(WALL_LEFT, DOOR_BOTTOM, WALL_THICKNESS, MAXY-DOOR_BOTTOM);
        }

    }
}
