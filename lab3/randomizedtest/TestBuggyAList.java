package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> testBuggy = new BuggyAList<>();
        AListNoResizing<Integer> testCorrect = new AListNoResizing<>();

        testBuggy.addLast(3);
        testCorrect.addLast(3);
        testBuggy.addLast(4);
        testCorrect.addLast(4);
        testBuggy.addLast(5);
        testCorrect.addLast(5);

        assertEquals(testBuggy.removeLast(), testCorrect.removeLast());
        assertEquals(testBuggy.removeLast(), testCorrect.removeLast());
        assertEquals(testBuggy.removeLast(), testCorrect.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> BL = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BL.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                assertEquals(L.size(), BL.size());
//                System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                if (L.size() == 0){
                    continue;
                } else {
                    int last = L.getLast();
                    int last1 = BL.getLast();
                    assertEquals(last, last1);
//                    System.out.println("getLast: " + last);
                }
            } else if (operationNumber == 3) {
                if (L.size() == 0) {
                    continue;
                } else {
                    int last = L.removeLast();
                    int last1 = BL.removeLast();
                    assertEquals(last, last1);
                    int size = L.size();
//                    System.out.println("removeLast ele: " + last + " , now with length " + size);
                }
            }
        }
    }


}
