import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;

public class MaxwellsDemon extends JFrame implements ActionListener, MouseListener {

    double deltat = .1; //  in seconds
    Timer timer;
    double resolution = Toolkit.getDefaultToolkit().getScreenResolution() / 2.54;

    int maxx = 600;
    int maxy = 600;

    Particle[] particles;
    int particleCount;

    JPanel titleArea;
    JPanel playingArea;
    JPanel playingAreaTop;
    JPanel bottomArea;
    MainPanel mainArea;

    JButton addParticleButton;
    JButton resetButton;

    JLabel temperatureLeft;
    JLabel temperatureRight;
    JLabel particlesLeft;
    JLabel particlesRight;

    JProgressBar progressLeft;
    JProgressBar progressRight;


    DecimalFormat df = new DecimalFormat("###.##");

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
        JLabel title = new JLabel("Maxwell's Demon", JLabel.CENTER);
        title.setFont(new Font("Bebas Neue", Font.PLAIN, 72));
        titleArea.add(title);
        add(titleArea, BorderLayout.NORTH);
        //----Top Area

        playingArea = new JPanel();
        playingArea.setLayout(new BorderLayout());

        mainArea = new MainPanel();

        playingArea.add(mainArea, BorderLayout.CENTER);

        JPanel tempPanel = new JPanel();
        tempPanel.setPreferredSize(new Dimension(50, 600));

        JPanel tempPanel2 = new JPanel();
        tempPanel2.setPreferredSize(new Dimension(50, 600));

        playingArea.add(tempPanel, BorderLayout.WEST);
        playingArea.add(tempPanel2, BorderLayout.EAST);

        add(playingArea, BorderLayout.CENTER);

        //top - labels
        playingAreaTop = new JPanel();
        playingAreaTop.setLayout(new GridLayout(3, 2, 0, 0));

        temperatureLeft = new JLabel("TEMPERATURE1: " + df.format(mainArea.getLeftTemperature()), JLabel.CENTER);
        temperatureLeft.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        temperatureRight = new JLabel("TEMPERATURE2: " + df.format(mainArea.getRightTemperature()), JLabel.CENTER);
        temperatureRight.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        playingAreaTop.add(temperatureLeft);
        playingAreaTop.add(temperatureRight);

        particlesLeft = new JLabel(String.valueOf(mainArea.getLeftParticles()), JLabel.CENTER);
        particlesLeft.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        particlesRight = new JLabel(String.valueOf(mainArea.getRightParticles()), JLabel.CENTER);
        particlesRight.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        playingAreaTop.add(particlesLeft);
        playingAreaTop.add(particlesRight);

        progressLeft = new JProgressBar(SwingConstants.HORIZONTAL,4,36);
        progressLeft.setValue((int) mainArea.getLeftTemperature());
        progressLeft.setStringPainted(true);
        progressLeft.setString(String.valueOf(mainArea.getLeftTemperature()));
        progressRight = new JProgressBar(SwingConstants.HORIZONTAL,4,36);
        progressLeft.setValue((int) mainArea.getRightTemperature());
        progressRight.setStringPainted(true);
        progressRight.setString(String.valueOf(mainArea.getRightTemperature()));
        playingAreaTop.add(progressLeft);
        playingAreaTop.add(progressRight);


        playingArea.add(playingAreaTop, BorderLayout.NORTH);
        //top - labels

        //----Bottom Area
        bottomArea = new JPanel();
        bottomArea.setLayout(new GridLayout(1, 2, 30, 0));

        addParticleButton = new JButton("ADD PARTICLE");
        addParticleButton.addActionListener(this);
        addParticleButton.setPreferredSize(new Dimension(50, 40));
        resetButton = new JButton("RESET");
        resetButton.addActionListener(this);

        bottomArea.add(new JPanel());
        bottomArea.add(addParticleButton);
        bottomArea.add(resetButton);
        add(bottomArea, BorderLayout.SOUTH);
        //----Bottom Area

        addMouseListener(this);

        timer = new Timer((int) (1000 * deltat), this);
        timer.start();
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addParticleButton) {
            System.out.println("add particle");
            mainArea.addParticle();
        }

        if (e.getSource() == resetButton) {
            System.out.println("reset");
            mainArea.reset();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        temperatureLeft.setText("TEMPERATURE1: " + df.format(mainArea.getLeftTemperature()));
        temperatureRight.setText("TEMPERATURE2: " + df.format(mainArea.getRightTemperature()));
        particlesLeft.setText(String.valueOf(mainArea.getLeftParticles()));
        particlesRight.setText(String.valueOf(mainArea.getRightParticles()));
        progressLeft.setValue((int) mainArea.getLeftTemperature());
        progressRight.setValue((int) mainArea.getRightTemperature());
        progressLeft.setString(String.valueOf(df.format(mainArea.getLeftTemperature())));
        progressRight.setString(String.valueOf(df.format(mainArea.getRightTemperature())));
        super.paint(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mainArea.click();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mainArea.click();
        repaint();
    }
}


