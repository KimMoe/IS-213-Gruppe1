/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4;

import java.util.List;
import javax.swing.SwingWorker;



/**
 *
 * @author evenal
 */
public class WorkerTest
{

    public static final long LIMIT1 = 1000;
    public static final long LIMIT2 = 1000;
    long result = 0;
    private SwingWorker<Long, Long> worker;
    private int[] data;
    int n;


    private WorkerTest(int n) {
        this.n = n;
        data = new int[n];
        for (int i = 0; i < n; i++) data[i] = 1;
        worker = new SwingWorker< Long, Long>()
        {
            @Override
            protected Long doInBackground() {
                long count = 0;
                while (nextPermutation(data)) {
                    count++;
                    publish(count);
                }
                return count;
            }


            boolean nextPermutation(int[] array) {
                // Find longest non-increasing suffix
                int i = array.length - 1;
                while (i > 0 && array[i - 1] >= array[i])
                    i--;
                // Now i is the head index of the suffix

                // Are we at the last permutation already?
                if (i <= 0)
                    return false;

                // Let array[i - 1] be the pivot
                // Find rightmost element that exceeds the pivot
                int j = array.length - 1;
                while (array[j] <= array[i - 1])
                    j--;
                // Now the value array[j] will become the new pivot
                // Assertion: j >= i

                // Swap the pivot with j
                int temp = array[i - 1];
                array[i - 1] = array[j];
                array[j] = temp;

                // Reverse the suffix
                j = array.length - 1;
                while (i < j) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                    j--;
                }

                // Successfully computed the next permutation
                return true;
            }


            @Override
            protected void done() {
                if (isCancelled()) System.err.println("Cancelled");
                else if (isDone()) System.err.println("Complete result = " + result);
            }

            @Override
            protected void process(List<Long> chunks) {
                for (long d : chunks) {
                    System.out.println("intermediate " + d);
                    result = d;
                }
            }
        };
    }


    public void start() {
        try {
            worker.execute();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

        if (worker.isCancelled()) System.err.println("cancelled");
        if (worker.isDone()) System.err.println("Complete");
    }

    public static final void main(String[] args) {
        WorkerTest test = new WorkerTest(5);
        test.start();
    }
}
