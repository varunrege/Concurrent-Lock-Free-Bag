package edu.vt.ece.hw5.set;

public class Benchmark {
    private static final String COARSE = "CoarseList";
    private static final String FINE = "FineList";
    private static final String LAZY = "LazyList";
    private static final String LOCKFREE = "LockFreeList";
    private static final String OPTIMISTIC = "OptimisticList";

    // Benchmark Set Implementation here.
    public static void main(String[] args) throws Exception {
        String mode = args.length <= 0 ? COARSE : args[0];
        int threadCount = (args.length <= 1 ? 4 : Integer.parseInt(args[1]));
        int totalIters = (args.length <= 2 ? 64000 : Integer.parseInt(args[2]));
        int percentage = (args.length <= 3 ? 20 : Integer.parseInt(args[3]));
        int iters = totalIters / threadCount;
        run(args, mode, threadCount, iters, percentage);

    }
    private static void run(String[] args, String mode, int threadCount, int iters, int percentage_contains) throws Exception {
        for (int i = 0; i < 5; i++) {
            switch (mode.trim().toLowerCase()) {
                case "coarselist":
                    runCoarseListTest(threadCount, iters, percentage_contains);
                    break;
                case "finelist":
                    runFineListTest(threadCount, iters, percentage_contains);
                    break;
                case "lazylist":
                    runLazyListTest(threadCount, iters, percentage_contains);
                    break;
                case "lockfreelist":
                    runLockFreeListTest(threadCount, iters, percentage_contains);
                    break;
                case "optimistic":
                    runOptimisticListTest(threadCount, iters, percentage_contains);
                    break;
            }
        }
    }
    private static void runCoarseListTest(int threadCount, int iters, int percentage_contains) throws Exception {
        final CoarseListTest[] threads = new CoarseListTest[threadCount];

        for (int t = 0; t < threadCount; t++) {
            threads[t] = new CoarseListTest(threadCount, iters, percentage_contains);
        }

        for (int t = 0; t < threadCount; t++) {
            threads[t].start();
        }

        long totalTime = 0;
        long maxTime = 0;
        for (int t = 0; t < threadCount; t++) {
            threads[t].join();
            totalTime += threads[t].getElapsedTime();
            maxTime = Math.max(maxTime, threads[t].getElapsedTime());
        }

        System.out.println("Average time per thread is " + totalTime / threadCount + "ms");
        System.out.println("Throughput is " + (iters*threadCount) / (maxTime*0.001) + "ops/s");
    }

    private static void runFineListTest(int threadCount, int iters, int percentage_contains) throws Exception {
        final FineListTest[] threads = new FineListTest[threadCount];
        //FineListTest.reset();

        for (int t = 0; t < threadCount; t++) {
            threads[t] = new FineListTest(threadCount, iters, percentage_contains);
        }

        for (int t = 0; t < threadCount; t++) {
            threads[t].start();
        }

        long totalTime = 0;
        long maxTime = 0;
        for (int t = 0; t < threadCount; t++) {
            threads[t].join();
            totalTime += threads[t].getElapsedTime();
            maxTime = Math.max(maxTime, threads[t].getElapsedTime());
        }

        System.out.println("Average time per thread is " + totalTime / threadCount + "ms");
        System.out.println("Throughput is " + (iters*threadCount) / (maxTime*0.001) + "ops/s");
    }
    private static void runLazyListTest(int threadCount, int iters, int percentage_contains) throws Exception {
        final LazyListTest[] threads = new LazyListTest[threadCount];
        //LazyListTest.reset();

        for (int t = 0; t < threadCount; t++) {
            threads[t] = new LazyListTest(threadCount, iters, percentage_contains);
        }

        for (int t = 0; t < threadCount; t++) {
            threads[t].start();
        }

        long totalTime = 0;
        long maxTime = 0;
        for (int t = 0; t < threadCount; t++) {
            threads[t].join();
            totalTime += threads[t].getElapsedTime();
            maxTime = Math.max(maxTime, threads[t].getElapsedTime());
        }

        System.out.println("Average time per thread is " + totalTime / threadCount + "ms");
        System.out.println("Throughput is " + (iters*threadCount) / (maxTime*0.001) + "ops/s");
    }
    private static void runLockFreeListTest(int threadCount, int iters, int percentage_contains) throws Exception {
        final LockFreeListTest[] threads = new LockFreeListTest[threadCount];
        //LockFreeListTest.reset();

        for (int t = 0; t < threadCount; t++) {
            threads[t] = new LockFreeListTest(threadCount, iters, percentage_contains);
        }

        for (int t = 0; t < threadCount; t++) {
            threads[t].start();
        }

        long totalTime = 0;
        long maxTime = 0;
        for (int t = 0; t < threadCount; t++) {
            threads[t].join();
            totalTime += threads[t].getElapsedTime();
            maxTime = Math.max(maxTime, threads[t].getElapsedTime());
        }

        System.out.println("Average time per thread is " + totalTime / threadCount + "ms");
        System.out.println("Throughput is " + (iters*threadCount) / (maxTime*0.001) + "ops/s");
    }
    private static void runOptimisticListTest(int threadCount, int iters, int percentage_contains) throws Exception {
        final OptimisticListTest[] threads = new OptimisticListTest[threadCount];
        //OptimisticListTest.reset();

        for (int t = 0; t < threadCount; t++) {
            threads[t] = new OptimisticListTest(threadCount, iters, percentage_contains);
        }

        for (int t = 0; t < threadCount; t++) {
            threads[t].start();
        }

        long totalTime = 0;
        long maxTime = 0;
        for (int t = 0; t < threadCount; t++) {
            threads[t].join();
            totalTime += threads[t].getElapsedTime();
            maxTime = Math.max(maxTime, threads[t].getElapsedTime());
        }

        System.out.println("Average time per thread is " + totalTime / threadCount + "ms");
        System.out.println("Throughput is " + (iters*threadCount) / (maxTime*0.001) + "ops/s");
    }
}