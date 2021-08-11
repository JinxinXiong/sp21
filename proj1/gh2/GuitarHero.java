package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    public static final double CONCERT_A = 440.0;
    public static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    private static final int n = 37;
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    private static double getFrequency(int i) {
        return (int) CONCERT_A * Math.pow(2, (i - 24) / 12.0);
    }

    public static void main(String[] args) {
        /* create a list of guitar strings */
        GuitarString[] strings = new GuitarString[n];
        char tmp;
        double freq;
        for (int i = 0; i < n; i += 1){
            tmp = keyboard.charAt(i);
            freq = getFrequency(i);
            strings[i] = new GuitarString(freq);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
//                if (index != -1) {
                if (keyboard.contains("" + key)) {
                    strings[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for(int i = 0; i < n; i += 1) {
                sample += strings[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for(int i = 0; i < n; i += 1) {
                strings[i].tic();
            }
        }
    }
}

