import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;

public class MaxwellsDemon extends JFrame implements ActionListener, MouseListener {

    double deltat = .1; //  in seconds
    Timer timer;

    private JPanel titleArea;
    private JPanel playingArea;
    private JPanel playingAreaTop;
    private JPanel bottomArea;
    private MainPanel mainArea;

    private JButton addParticleButton;
    private JButton resetButton;

    private JLabel temperatureLeft;
    private JLabel temperatureRight;
    private JLabel particlesLeft;
    private JLabel particlesRight;

    private JProgressBar progressLeft;
    private JProgressBar progressRight;

    private static final DecimalFormat DF = new DecimalFormat("###.##");

    public static void main(String[] args) {
        new MaxwellsDemon();
    }

    public MaxwellsDemon() {
        System.out.println("MaxwellsDemon: Starting...");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Maxwell's Demon");
        setLayout(new BorderLayout());
        setResizable(false);

        //----Top Area
        titleArea = new JPanel();
        JLabel title = new JLabel("Maxwell's Demon", JLabel.CENTER);
        title.setFont(new Font("Bebas Neue", Font.PLAIN, 72));
        titleArea.add(title);
        add(titleArea, BorderLayout.NORTH);
        //----Top Area

        //----Playing Area
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

        //top - labels
        playingAreaTop = new JPanel();
        playingAreaTop.setLayout(new GridLayout(3, 2, 0, 0));

        temperatureLeft = new JLabel("TEMPERATURE1: " + DF.format(mainArea.getLeftTemperature()), JLabel.CENTER);
        temperatureLeft.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        temperatureRight = new JLabel("TEMPERATURE2: " + DF.format(mainArea.getRightTemperature()), JLabel.CENTER);
        temperatureRight.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        playingAreaTop.add(temperatureLeft);
        playingAreaTop.add(temperatureRight);

        particlesLeft = new JLabel(String.valueOf(mainArea.getLeftParticles()), JLabel.CENTER);
        particlesLeft.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        particlesRight = new JLabel(String.valueOf(mainArea.getRightParticles()), JLabel.CENTER);
        particlesRight.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        playingAreaTop.add(particlesLeft);
        playingAreaTop.add(particlesRight);

        progressLeft = new JProgressBar(SwingConstants.HORIZONTAL,0,100);
        progressLeft.setValue((int) mainArea.getLeftTemperature());
        progressLeft.setStringPainted(true);
        progressLeft.setString(String.valueOf(mainArea.getLeftTemperature()));
        progressRight = new JProgressBar(SwingConstants.HORIZONTAL,0,100);
        progressLeft.setValue((int) mainArea.getRightTemperature());
        progressRight.setStringPainted(true);
        progressRight.setString(String.valueOf(mainArea.getRightTemperature()));
        playingAreaTop.add(progressLeft);
        playingAreaTop.add(progressRight);

        playingArea.add(playingAreaTop, BorderLayout.NORTH);
        //top - labels


        add(playingArea, BorderLayout.CENTER);
        //----Playing Area

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
            System.out.println("add particle button pressed");
            mainArea.addParticle();
        }

        if (e.getSource() == resetButton) {
            System.out.println("reset button pressed");
            mainArea.reset();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        temperatureLeft.setText("TEMPERATURE1: " + DF.format(mainArea.getLeftTemperature()));
        temperatureRight.setText("TEMPERATURE2: " + DF.format(mainArea.getRightTemperature()));
        particlesLeft.setText(String.valueOf(mainArea.getLeftParticles()));
        particlesRight.setText(String.valueOf(mainArea.getRightParticles()));
        progressLeft.setValue((int) mainArea.getLeftTemperature());
        progressRight.setValue((int) mainArea.getRightTemperature());
        progressLeft.setString(String.valueOf(DF.format(mainArea.getLeftTemperature())));
        progressRight.setString(String.valueOf(DF.format(mainArea.getRightTemperature())));
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
        System.out.println("mouse clicked");
        mainArea.click();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouse unclicked");
        mainArea.click();
        repaint();
    }
}


