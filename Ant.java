package Minor_Project; 
public class Ant
{
	protected int trailS;
	protected int t[];
	protected boolean flag[];
	Ant(int tourSize)
	{
		trailS=tourSize;
		t=new int[tourSize];
		flag=new boolean[tourSize];
	}
	// Put the city that is visited in the tour
	protected void visitCity(int currIndex,int city)
	{
		t[currIndex+1]=city;
		flag[city]=true;
	}
	// Check if a particular city is visited or not
	protected boolean visited(int a)
	{
		return(flag[a]);
	}
	// Find length of the trail followed
	protected double tLength(double graph[][])
	{
		int i;
		double l=graph[t[trailS-1]][t[0]];
		for(i=0;i<trailS-1;i++)
		{
			l=l+graph[t[i]][t[i+1]];
		}
		return l;
	}
	// Clear the trail after each iteration
	protected void clear()
	{
		for(int i=0;i<trailS;i++)
		{
			flag[i]=false;
		}
	}
}

