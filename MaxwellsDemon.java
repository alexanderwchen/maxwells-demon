import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MaxwellsDemon extends JFrame implements ActionListener{

    double deltat = .1; //  in seconds
    Timer timer;
    double resolution = (double) (Toolkit.getDefaultToolkit().getScreenResolution() / 2.54);

    int maxx = 600;
    int maxy = 600;

    Particle[] particles;
    int particleCount;

    JPanel playingArea;

    public static void main( String[] args ){
        System.out.println("Start");
        new MaxwellsDemon();
    }

    public MaxwellsDemon(){
        System.out.println("MaxwellsDemon: Starting...");
        System.out.println("Resolution: " + resolution);

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setTitle("Maxwell's Demon");
        setLayout(new BorderLayout());
        setBackground( Color.WHITE );
        setSize( maxx, maxy );

        add( new JButton("Maxwell's Demon"), BorderLayout.NORTH);

/*
        JPanel panel0 = new JPanel();
        panel0.setBackground( Color.yellow );
        add(panel0);  // this.add ...
        panel0.setLayout( new BorderLayout() );
        panel0.add( new JButton("CA"), BorderLayout.WEST );
        panel0.add( new JButton("MN"), BorderLayout.NORTH );
        panel0.add( new JButton("DC"), BorderLayout.EAST );
        panel0.add( new JButton("OH"), BorderLayout.CENTER );
        panel0.add( new JButton("TX"), BorderLayout.SOUTH );
*/
        /*JButton jb1 = new JButton("Hi");
        add(jb1, BorderLayout.NORTH);*/





        particleCount = 0;
        /*particles = new Particle[particleCount];
        for ( int i=0; i<particleCount; i++ ){
            if (i%2 == 0) {
                particles[i] = new Particle(maxx, maxy, true);
            }
            else{
                particles[i] = new Particle(maxx, maxy, false);
            }
            particles[i].getInfo();
        }*/

        timer = new Timer((int)(1000 * deltat), this);
        timer.start();

        setVisible(true);
    }
    public void moveAll()
    {
        for ( int i=0; i<particleCount; i++ ) { particles[i].move(deltat); }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("click");
        if ( e.getSource()==timer ) { moveAll(); }
        System.out.println(java.time.LocalDateTime.now());
        //particles[0].getInfo();
        //repaint();
    }

    @Override
    public void paint( Graphics g )
    {
        //super.paint(g); takes too long

        g.setColor( Color.WHITE ); // just white-out the window
        int w = getWidth();  int h = getHeight();
        g.fillRect( 0, 0, w, h );  // with a big rectangle


        //for ( int i=0; i<particleCount; i++ ) { particles[i].drawMe(g); }

    }



    public class Particle
    {
        double x,y;
        double vx,vy;
        double oldx,oldy;
        double maxx,maxy;
        boolean isSlow;
        double velocitycm;
        double velocitypx;
        double resolution = (double) (Toolkit.getDefaultToolkit().getScreenResolution() / 2.54);

        public Particle(double maxx, double maxy, boolean isSlow)
        {
            this.maxx = maxx;
            this.maxy = maxy;

            x = Math.random() * maxx;
            y = Math.random() * maxy;
            this.isSlow = isSlow;

            double angle = Math.random()*360;

            if(isSlow == true){
                velocitycm = Math.random()*2+2;
            }

            else{
                velocitycm = Math.random()*2+4;
            }

            velocitypx = velocitycm * resolution;
            vx = velocitypx * Math.cos(velocitypx);
            vy = velocitypx * Math.sin(velocitypx);
        }

        public void move( double deltat )
        {
            oldx = x; oldy = y;
            x += vx * deltat;
            y += vy * deltat;
            stayOnScreen();
        }

        public void stayOnScreen()
        {
            if ( x<0 ) { vx *= -1; }
            if ( y<0 ) { vy *= -1; }
            if ( x>maxx ) { vx *= -1; }
            if ( y>maxy ) { vy *= -1; }
        }

        public void drawMe( Graphics g )
        {
            //g.setColor( Color.WHITE ); // erase the old one
            //g.fillOval( (int)(oldx-2), (int)(oldy-2), 5, 5 );

            if(isSlow){
                g.setColor( Color.BLUE );
            }
            else{
                g.setColor(Color.RED);
            }

            g.fillOval( (int)(x-2), (int)(y-2), 5, 5 );
        }

        public void getInfo(){
            System.out.println("Particle Info (in cm):");
            System.out.println("x: " + x/resolution);
            System.out.println("y: " + y/resolution);
            System.out.println("vx: " + vx/resolution);
            System.out.println("vy: " + vy/resolution);
            System.out.println("isSlow: " + isSlow);
            System.out.println("Velocity: " + velocitycm);
        }
    }
}