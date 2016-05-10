package sort;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Mergesort {
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		
		Scanner s = new Scanner(new FileReader("hw3.dat"));
		int n=s.nextInt();
		int[] a=new int[n];
		int i=0;
		while(s.hasNextInt())
		{
			//long n = s.nextInt();
			a[i]=s.nextInt();
			i++;
			//System.out.println(n+" "+isprime(n,10));
		}
		s.close();
		mergeSort(a,0,n-1);
		for( i=0;i<n;i++)
		System.out.println(a[i]);
	}


public static void mergeSort (int[] list, int lowIndex, int highIndex) {
    if (lowIndex == highIndex)
            return;
    else {
            int midIndex = (lowIndex + highIndex) / 2;
            mergeSort(list, lowIndex, midIndex);
            mergeSort(list, midIndex + 1, highIndex);
            merge(list, lowIndex, midIndex, highIndex);
           // printIt(list, 0, list.length-1);
    }
}
    
public static void merge(int[] list, int lowIndex, int midIndex, int highIndex) {
    int[] L = new int[midIndex - lowIndex + 2];
        for (int i = lowIndex; i <= midIndex; i++) {
            L[i - lowIndex] = list[i];
        }
   L[midIndex - lowIndex + 1] = Integer.MAX_VALUE;
    
    int[] R = new int[highIndex - midIndex + 1];
        for (int i = midIndex + 1; i <= highIndex; i++) {
            R[i - midIndex - 1] = list[i];
        }
    R[highIndex - midIndex] = Integer.MAX_VALUE;
        
    int i = 0, j = 0;
    for (int k = lowIndex; k <= highIndex; k++) {
        if (L[i] <= R[j]) {
                list[k] = L[i];
                i++;
        }
        else {
                list[k] = R[j];
                j++;
        }
    }
}

}