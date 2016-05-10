package sort;


public class selectionsort {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a={5,1,2,3,4};
		Selectionsort(a);
		for(int i=0;i<a.length;i++)
		System.out.println(a[i]);
		

	}
	public static void Selectionsort(int[] a){
		
		for(int i=0;i<a.length;i++)
			{
				int indexmin = i;
				for(int j=i+1;j<a.length;j++)
				{
					if(a[j]<a[indexmin])
					{
					
						indexmin=j;
					
					}
					
					
					
				}
				if(indexmin!=i)
				{
					int temp =a[i];
					a[i]=a[indexmin];
					a[indexmin]=temp;
				}
			}

	}

}
