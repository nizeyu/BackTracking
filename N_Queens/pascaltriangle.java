import java.util.ArrayList;
import java.util.List;

public class pascaltriangle {
	public static void main(String[] args) {
		//System.out.print(triangle(6));
		List<List<Integer>> l=new ArrayList<>();
		l=generate(4);
		for(int i=0;i<l.size();i++)
		{
			for(int j=0;j<l.get(i).size();j++)
			System.out.print(l.get(i).get(j));
			 System.out.println();
		}
	}

	public static int[][] triangle(int n) {
		// TODO Auto-generated method stub
		int[][] a=new int[n][];
		int i;
		for(i=0;i<n;i++){
			a[i]=new int[i+1];
			a[i][0]=1;
			for(int j=1;j<i;j++)
				a[i][j]=a[i-1][j-1]+a[i-1][j];
			a[i][i]=1;
			
		}
		return a;
	}
	 public static List<List<Integer>> generate(int numRows) {
	        List<List<Integer>> l=new ArrayList<List<Integer>>();
	        for(int i=0;i<numRows;i++)
	        {
	            l.add(i,new ArrayList<Integer>());
	            l.get(i).add(0,new Integer(1));
	            for(int j=1;j<i;j++)
	            {
	                l.get(i).add(j,l.get(i-1).get(j-1)+l.get(i-1).get(j));
	            }
	            if(i!=0)
	            l.get(i).add(i,new Integer(1));
	            
	        }
	        return l;
	        
	    }

}
