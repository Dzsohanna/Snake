import java.io.*;

public class HighScore {
    private final String FILE = "highscore.txt";
    private int highScore;

    public HighScore() {
        this.highScore = readHighScore();
    }

    private int readHighScore() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            //beolvassuk a fajl elso sorat merft csak egy szamot tarolunk
            String line = br.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void writeHighScore(int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            //szovegge alakitja a szamot majd felulirja a fajl tartalmat
            bw.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void checkHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            writeHighScore(highScore);
        }
    }
}
