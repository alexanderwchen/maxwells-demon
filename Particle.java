import java.awt.*;

public class Particle
{

    public static final int SLOWMIN = 2;
    public static final int SLOWMAX = 4;
    public static final int FASTMIN = 4;
    public static final int FASTMAX = 6;

    private static final int DIAMETER = 5;
    private double x,y;
    private double vx,vy;
    private double oldx,oldy;
    private double maxx,maxy;
    private boolean isSlow;
    private double velocitycm;
    private double velocitypx;
    private double resolution = (double) (Toolkit.getDefaultToolkit().getScreenResolution() / 2.54);
    private boolean isLeft;

    public double getX() {
        return x;
    }
    public double getY(){
        return y;
    }

    public double getVelocitycm(){
        return velocitycm;
    }

    public boolean getIsLeft(){
        return isLeft;
    }

    public int getDiameter(){
        return DIAMETER;
    }

    public Particle(double maxx, double maxy, double wallThickness, boolean isSlow, boolean isLeft)
    {
        this.maxx = maxx;
        this.maxy = maxy;

        this.isLeft = isLeft;

        if(isLeft){
            x = Math.random() * ((maxx-wallThickness)/2);
        }
        else{
            x = Math.random() * ((maxx-wallThickness)/2) + (maxx+wallThickness)/2;
        }

        y = Math.random() * maxy;

        this.isSlow = isSlow;
        double angle = Math.random()*360;
        if(isSlow == true){
            velocitycm = Math.random()*(SLOWMAX-SLOWMIN)+SLOWMIN;
        }
        else{
            velocitycm = Math.random()*(FASTMAX-FASTMIN)+FASTMIN;
        }

        velocitypx = velocitycm * resolution;
        vx = velocitypx * Math.cos(angle);
        vy = velocitypx * Math.sin(angle);
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
        if ( x<0 ) { flipX(); }
        if ( y<0 ) { flipY(); }
        if ( x>maxx-DIAMETER ) { flipX(); }
        if ( y>maxy-DIAMETER ) { flipY(); }
    }

    public void flipX(){
        vx *= -1;
    }
    public void flipY(){
        vy *= -1;
    }

    public void flipSides(){
        isLeft = !isLeft;
    }

    public void drawMe( Graphics g )
    {
        if(isSlow){
            g.setColor( Color.BLUE );
        }
        else{
            g.setColor(Color.RED);
        }

        g.fillOval((int) x, (int)(y), DIAMETER, DIAMETER );
        g.setColor( Color.BLACK );
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
