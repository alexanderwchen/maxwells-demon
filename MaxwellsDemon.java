import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MaxwellsDemon extends JFrame implements ActionListener {

    double deltat = .1; //  in seconds
    Timer timer;
    double resolution = (double) (Toolkit.getDefaultToolkit().getScreenResolution() / 2.54);

    int maxx = 600;
    int maxy = 600;

    Particle[] particles;
    int particleCount;

    JPanel titleArea;
    JPanel playingArea;
    JPanel temperatureArea;
    JPanel bottomArea;
    MainPanel mainArea;

    JButton openDoorButton;
    JButton resetButton;

    public static void main(String[] args) {
        System.out.println("Start");
        new MaxwellsDemon();
    }

    public MaxwellsDemon() {
        System.out.println("MaxwellsDemon: Starting...");
        System.out.println("Resolution: " + resolution);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Maxwell's Demon");
        setLayout(new BorderLayout());
        //setBackground( Color.WHITE );
        //setSize(800, 800);
        setResizable(false);

        //----Top Area
        titleArea = new JPanel();
        //JButton start = new JButton("Start Maxwell's Demon");
        JLabel title = new JLabel("Maxwell's Demon", JLabel.CENTER);
        title.setFont(new Font("Bebas Neue", Font.PLAIN, 72));
        titleArea.add(title);
        add(titleArea, BorderLayout.NORTH);
        //----Top Area

        playingArea = new JPanel();
        playingArea.setLayout(new BorderLayout());

        //top - temperatures
        temperatureArea = new JPanel();
        temperatureArea.setLayout(new GridLayout(1, 2, 50, 0));
        JLabel temperatureLeft = new JLabel("TEMPERATURE1: $$", JLabel.CENTER);
        temperatureLeft.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        JLabel temperatureRight = new JLabel("TEMPERATURE2: $$", JLabel.CENTER);
        temperatureRight.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        temperatureArea.add(temperatureLeft);
        temperatureArea.add(temperatureRight);
        playingArea.add(temperatureArea, BorderLayout.NORTH);

        //top - temperatures
        mainArea = new MainPanel();

        playingArea.add(mainArea, BorderLayout.CENTER);


        add(playingArea, BorderLayout.CENTER);


        //----Bottom Area
        bottomArea = new JPanel();
        bottomArea.setLayout(new GridLayout(1, 2, 30, 0));

        openDoorButton = new JButton("OPEN DOOR");
        openDoorButton.addActionListener(this);
        openDoorButton.setPreferredSize(new Dimension(50, 40));
        resetButton = new JButton("RESET");
        resetButton.addActionListener(this);

        bottomArea.add(new JPanel());
        bottomArea.add(openDoorButton);
        bottomArea.add(resetButton);
        add(bottomArea, BorderLayout.SOUTH);
        //----Bottom Area


        timer = new Timer((int) (1000 * deltat), this);
        timer.start();
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("click");
        if (e.getSource() == timer) {
            //System.out.println("timer");
        }
        if (e.getSource() == openDoorButton) {
            System.out.println("opening door");
            //JOptionPane.showMessageDialog(null, "oop", "You have opened button", JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getSource() == resetButton) {
            System.out.println("reset");
            //JOptionPane.showMessageDialog(null, "oop", "You have opened button", JOptionPane.INFORMATION_MESSAGE);
        }
        //System.out.println(java.time.LocalDateTime.now());
        //particles[0].getInfo();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);// takes too long
/*
        g.setColor( Color.WHITE ); // just white-out the window
        int w = getWidth();  int h = getHeight();
        g.fillRect( 0, 0, w, h );  // with a big rectangle

*/
        //for ( int i=0; i<particleCount; i++ ) { particles[i].drawMe(g); }

    }
}


