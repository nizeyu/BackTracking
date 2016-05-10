package sort;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class heapsort {
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
		sort(a);
		for( i=0;i<a.length;i++)
		System.out.println(a[i]);
		

	}
	public static  void buildMaxheap(int[]a){
		for(int i=1;i<a.length;i++)
		{
			if(a[i]>a[(i-1)/2])
			{
				swap(a,i,(i-1)/2);
			}
			
		}
		
		
	}

	public static void swap(int[]a,int x,int y){
		int temp=a[x];
		a[x]=a[y];
		a[y]=temp;
		
		
	}
	public static void siftDown(int[] a){
		
		
		
		
	}
	public static void sort(int[] a){
		while(true)
		{
			int i=0;
		    int last=a.length-1;
			swap(a,i,last);
			last--;
			
		}
		
	}
}

