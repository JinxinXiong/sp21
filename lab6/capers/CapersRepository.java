package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author Jinxin
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD, ".capers"); // TODO Hint: look at the `join`
                                                                            //      function in Utils
    static final String story_path = ".capers/story.txt";
    static final String dog_path = ".capers/dogs";
    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        // TODO
        if (! CAPERS_FOLDER.exists()) {
            CAPERS_FOLDER.mkdir();
            File story = new File(story_path);
            try {
                story.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File dogs = new File(dog_path);
            dogs.mkdir();
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO
        File story = new File(story_path);
        if (! story.exists()) {
            try {
                story.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // get the previous content
        String contents;
        File inFile = new File(story_path);
        contents = Utils.readContentsAsString(inFile);
        String newline = contents + text + '\n';
        System.out.println(newline);

        Utils.writeContents(story, newline);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog dog = new Dog(name, breed, age);
        dog.saveDog();
        System.out.println(dog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
        File file_by_name = Utils.join(dog_path, name);
        if (! file_by_name.exists()) {
            System.out.println("No such a dog exits, please check the dog's name!");
            return;
        }

        Dog dog = Utils.readObject(file_by_name, Dog.class);
        dog.haveBirthday();

        Utils.writeObject(file_by_name, dog);
    }
}
