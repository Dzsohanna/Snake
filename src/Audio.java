import javax.sound.sampled.*;
import java.io.*;

public class Audio {

    //audoi fajlok kezelese
    //a clip hasznalhato hang lejatszas, megallitas, ujrainditasra
    Clip audio;

    Audio(String path) {
        //a clip eleresi utvonala
        //egy objektumot hoz letre a megadott eleresi ut alapjan
        File file = new File(path);
        try {
            //adatfolyam amely hangokat tartalmaz                 olvashato formaba hozza
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            //clip obj-ot hoz letre
            audio = AudioSystem.getClip();
            //megnyitja es lehetove teszi h lejatszhato legyen
            audio.open(audioInputStream);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("IO error");
        }

    }
}