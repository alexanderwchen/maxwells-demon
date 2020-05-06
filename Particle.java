import java.awt.*;

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
        System.out.println("Particle Info:");
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("vx: " + vx);
        System.out.println("vy: " + vy);
        System.out.println("isSlow: " + isSlow);
        System.out.println("Velocity: " + velocitypx);
    }
    public void getInfoCM(){
        System.out.println("Particle Info (in cm):");
        System.out.println("x: " + x/resolution);
        System.out.println("y: " + y/resolution);
        System.out.println("vx: " + vx/resolution);
        System.out.println("vy: " + vy/resolution);
        System.out.println("isSlow: " + isSlow);
        System.out.println("Velocity: " + velocitycm);
    }
}
