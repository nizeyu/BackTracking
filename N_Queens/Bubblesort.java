package sort;

public class Bubblesort {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a={4,3,2,1};
		betterbubblesort(a);
		for(int i=0;i<a.length;i++)
		System.out.println(a[i]);
		

	}
	public static void bubblesort(int[] a){
		
		for(int i=0;i<a.length-1;i++)
		{
			int t=0;
			boolean swap=false;
			for(int j=1;j<a.length-t;j++)
			{
				if(a[j-1]>a[j])
				{
					int temp=a[j-1];
				    a[j-1]=a[j];
				    a[j]=temp;
				    swap=true;
				}
				
				
			}
			t++;
			if(swap==false)
				break;
			
		}
	}
		public static void betterbubblesort(int[] a){
			int n=a.length;
			//int newn=0;
			while(n>1)//or n>0;
			{
				int newn=0;
				for(int j=1;j<n;j++)
				{
					if(a[j-1]>a[j])
					{
						int temp=a[j-1];
						a[j-1]=a[j];
						a[j]=temp;
						newn=j;
					}
					//t=j;
					
					
				}
				n=newn;
					
				
			}

		
		
		
	}
	

}
