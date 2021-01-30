package edu.vt.ece.hw5.set;

import lists.LockFreeList;

import java.util.concurrent.ThreadLocalRandom;

public class LockFreeListTest extends Thread {
    long elapsed;
    int iter;
    int threadCount;
    int cont_percentage;
    static LockFreeList<Integer> list = new LockFreeList<Integer>();

    public LockFreeListTest (int threadCount, int iter, int percent) {
        this.threadCount = threadCount;
        this.iter = iter;
        this.cont_percentage = percent;
    }

    /*public static void reset() {
        list = null;
    }*/

    public void run() {
        long start = System.currentTimeMillis();
        for(int i = 0; i < iter; i++)
        {
            int RNG = ThreadLocalRandom.current().nextInt(0, 500);
            int RNG2 = ThreadLocalRandom.current().nextInt(0, 100);
            if (RNG2 <= cont_percentage)
            {
                list.contains(RNG);
            }
            else if (RNG2 <= ((100 - cont_percentage)/2 + cont_percentage))
            {
                list.add(RNG);
            }
            else
            {
                list.remove(RNG);
            }
        }
        long end = System.currentTimeMillis();
        elapsed = end - start;
    }

    public long getElapsedTime() {
        return elapsed;
    }
}
