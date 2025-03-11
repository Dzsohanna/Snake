import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class GamePanel extends JPanel implements ActionListener {

    //final kulcsszo h ne lehessen az erteket tobbet megvaltoztatni
    private final Button back, start, new_game, pausee, play;
    private final int Width = 660;
    private final int Height = 630;
    private final int Size = 30; //egy kocka merete
    private int delay; //idozito kesleltetese a kigyo mozgasahoz
    private final int initial_delay = 300; //kezdo kesleltetes
    private int increasement = 5; //kigyo gyorsitasa
    private final int[] x = new int[100]; //a kigyo poziciojat tarolo tomb
    private final int[] y = new int[100];
    private int body = 4; //a kigyo kezdeti hossza
    private int fruitX, fruitY; //gyumolgy koordinatai
    private Image food; //a gyumolcsok kepei
    private char direction = 'R'; //mozgas irany
    private boolean running; //allapot
    private int Score = 0;
    private Timer timer; //idozito
    private final HighScore high_score;

    public GamePanel() {

        //letrehoz egy uj high score obj-ot
        high_score = new HighScore();
        //fokuszalhato kell legyen hogy megkapja a billantyu utasitasokat
        setFocusable(true);
        //letrehoz egy uj MyKeyAdapter obj-ot
        MyKeyAdapter keyAdapter = new MyKeyAdapter(this);
        addKeyListener(keyAdapter);
        setLayout(null);

        back = new Button("Back");
        start = new Button("Start");
        new_game = new Button("New Game");
        pausee = new Button("Pause");
        play = new Button("Play");

        new_game.setFont(new Font("Arial", Font.BOLD, 12));
        start.addActionListener(this);
        new_game.addActionListener(this);
        pausee.addActionListener(this);
        play.addActionListener(this);

        start.setBounds(710, 330, 105, 50);
        pausee.setBounds(710, 330, 105, 50);
        play.setBounds(710, 330, 105, 50);
        new_game.setBounds(710, 425, 105, 50);
        back.setBounds(710, 520, 105, 50);

        new_game.setVisible(false);
        pausee.setVisible(false);
        play.setVisible(false);

        add(start);
        add(new_game);
        add(pausee);
        add(play);
        add(back);
    }

    public JButton getBackButton() {
        return back;
    }

    public void startGame() {
        delay = initial_delay;
        newfruit();
        running = true;
        //uj timer obj-ot hoz letre
        //a jatek fo resze. ujrarajzolja a panelt, frissiti a kigyot, ellenorzi az utkozest s az evest
        timer = new Timer(delay, this);
        timer.start();
        start.setVisible(false);
        pausee.setVisible(true);
        new_game.setVisible(true);
        //hogy megkapja a billentyu lenyomast
        requestFocusInWindow();
    }

    public void New_game() {
        delay = initial_delay;
        timer.stop();
        running = false;
        Score = 0;
        body = 4;
        //lenullazza a tomboket
        IntStream.range(0, x.length).forEach(i -> x[i] = 0);
        IntStream.range(0, y.length).forEach(i -> y[i] = 0);
        direction = 'R';
        startGame();
    }

    public void resetGame() {
        delay = initial_delay;
        if (timer != null) {
            timer.stop();
        }
        running = false;
        Score = 0;
        body = 4;
        IntStream.range(0, x.length).forEach(i -> x[i] = 0);
        IntStream.range(0, y.length).forEach(i -> y[i] = 0);
        direction = 'R';
        newfruit();
        start.setVisible(true);
        new_game.setVisible(false);
        pausee.setVisible(false);
        play.setVisible(false);
        repaint();
    }


    public void paintComponent(Graphics g) {
        //torli a panel tartalmat
        super.paintComponent(g);
        //Gra[hics2D fejlettebb verzioja a simanak, tobb lehetoseget biztosit
        Graphics2D gd = (Graphics2D) g;

        //tabla kirajzolasa
        IntStream.range(0, Width / Size + 1).forEach(i ->
                IntStream.range(0, Height / Size).forEach(j -> {
                    gd.setColor((i + j) % 2 == 0 ? new Color(112, 112, 107) : new Color(117, 117, 113));
                    gd.fillRect(i * Size, j * Size, Size, Size);
                })
        );

        //elvalaszto vonal a tabla szelen
        gd.setColor(new Color(7, 104, 22));
        gd.setStroke(new BasicStroke(5));
        gd.drawLine(Width + 31, 0, Width + 31, Height);

        //kigyo kirajzolasa
        IntStream.range(0, body)
                .forEach(i -> {
                    if (i == 0) {
                        gd.setColor(new Color(7, 104, 22));
                        gd.fillRect(x[i], y[i], Size - 3, Size - 3);
                        gd.setColor(Color.white);
                        gd.setStroke(new BasicStroke(3));
                        gd.drawOval(x[i] + 3, y[i] + 7, 8, 8);
                        gd.drawOval(x[i] + 17, y[i] + 7, 8, 8);
                        gd.setStroke(new BasicStroke(2));
                        gd.drawLine(x[i] + 4, y[i] + 22, x[i] + 24, y[i] + 22);
                    } else {
                        gd.setColor(new Color(68, 174, 83));
                        gd.fillRect(x[i], y[i], Size - 3, Size - 3);
                    }
                });


        //gyumolcs rajzolasa
        gd.drawImage(food, fruitX, fruitY, null);

        //scorok kiirasa
        gd.setColor(new Color(7, 104, 22));
        gd.setFont(new Font("Arial", Font.BOLD, 25));
        gd.drawString("Score:", 720, 50);
        gd.drawString(String.valueOf(Score), 750, 100);
        gd.setFont(new Font("Arial", Font.BOLD, 23));
        gd.drawString("High score:", 700, 170);
        gd.setFont(new Font("Arial", Font.BOLD, 25));
        gd.drawString(String.valueOf(high_score.getHighScore()), 750, 220);

        if (!running && new_game.isVisible()) {
            Gameover(g);
        }
    }

    public void move() {

        //az elore haladashoz a kovetkezo test darab az elozo helyere kerul
        for (int i = body; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'R':
                x[0] += Size;
                break;
            case 'L':
                x[0] -= Size;
                break;
            case 'U':
                y[0] -= Size;
                break;
            case 'D':
                y[0] += Size;
                break;
        }
    }

    public void newfruit() {
        final String[] Food_Images = new String[]{"img/apple.png", "img/cherry.png",
                "img/berry.png", "img/coconut_.png", "img/peach.png", "img/watermelon.png", "img/orange.png",
                "img/pomegranate.png", "img/pear.png", "img/tomato.png"};
        Random random = new Random();
        fruitX = random.nextInt(Width / Size) * Size;
        fruitY = random.nextInt(Height / Size) * Size;
        //a food imagebol kivalaszt egy random gyumolcsot majd atmeretezi
        food = new ImageIcon(Arrays.stream(Food_Images)
                .skip(random.nextInt(Food_Images.length))
                .findFirst()
                .get())
                .getImage().getScaledInstance(30, 30, 5);
    }

    public void checkFruit() {
        if (x[0] == fruitX && y[0] == fruitY) {
            Audio clicked = new Audio("sounds/eat.wav");
            clicked.audio.start();
            newfruit();
            body++;
            Score += 5;
            delay = Math.max(delay - increasement, 50);
            timer.setDelay(delay);
            high_score.checkHighScore(Score);
        }

    }

    public void checkCollision() {
        for (int i = body; i > 0; i--) {

            //sajat magaval utkozott
            if (x[0] == x[i] && y[0] == y[i])
                running = false;

            //jobb oldal
            if (x[0] >= Width + 1)
                running = false;

            //bal oldal
            if (x[0] < 0)
                running = false;

            //felso oldal
            if (y[0] < 0)
                running = false;

            //also oldal
            if (y[0] >= Height)
                running = false;

            if (!running) {
                Audio clicked = new Audio("sounds/die.wav");
                clicked.audio.start();
                timer.stop();
            }
        }
    }

    public void Gameover(Graphics g) {
        start.setVisible(false);
        pausee.setVisible(false);
        play.setVisible(false);
        new_game.setVisible(true);
        g.setColor(new Color(120, 4, 4));
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Game Over", 200, 250);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Score: " + Score, 250, 350);
    }

    void play() {
        play.setVisible(false);
        pausee.setVisible(true);
        timer.start();
    }

    void pause() {
        play.setVisible(true);
        pausee.setVisible(false);
        timer.stop();
    }

    void audio() {
        Audio clicked = new Audio("sounds/click.wav");
        clicked.audio.start();
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            checkFruit();
        }
        repaint();

        if (e.getSource() == start) {
            audio();
            startGame();
        }

        if (e.getSource() == new_game) {
            audio();
            New_game();
            repaint();
        }

        if (e.getSource() == pausee) {
            audio();
            pause();
        }

        if (e.getSource() == play) {
            audio();
            play();
        }
    }
}