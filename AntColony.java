package Minor_Project;
import java.util.*;
public class AntColony 
{
	private double c;
	private double alpha;
	private double beta;
	private double evaporation;
	private double pheromoneLeft;
	private double antFactor;
	private double randomFactor;
	private int maxIt;
	private int noOfCities;
	private int noOfAnts;
	private double graph[][];
	private double trails[][];
	private List<Ant> ants=new ArrayList<>();
	private Random random=new Random();
	private double probabilities[];
	private int currindex;
	private int bestTourOrder[];
	private double tourLength;
	public AntColony(double tr,double al,double be,double af,double rf,int iter,int nCities)
	{
		c=tr;alpha=al;beta=be;evaporation=0.5;pheromoneLeft=500;antFactor=af;randomFactor=rf;maxIt=iter;
		graph=generateRandomMatrix(nCities);
		noOfCities=nCities;
		noOfAnts=(int)(noOfCities*antFactor);
		trails=new double[noOfCities][noOfCities];
		probabilities=new double[noOfCities];
		for(int i=0;i<noOfAnts;i++)
			ants.add(new Ant(noOfCities));
	}
	// Generate initial solution
	public double[][] generateRandomMatrix(int n) 
    {
        double randomMatrix[][]=new double[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(i==j)
                    randomMatrix[i][j]=0;
                else
                    randomMatrix[i][j]=Math.abs(random.nextInt(100)+1);
            }
        }
        System.out.print("\t");
        for(int i=0;i<n;i++)
        	System.out.print(i+"\t");
        System.out.println();
        for(int i=0;i<n;i++)
        {
        	System.out.print(i+"\t");
            for(int j=0;j<n;j++)
            	System.out.print(randomMatrix[i][j]+"\t");
            System.out.println();
        }
        int sum=0;
        for(int i=0;i<n-1;i++)
            sum+=randomMatrix[i][i+1];
        sum+=randomMatrix[n-1][0];
        System.out.print("\nNaive solution 0-1-2-...-n-0 = "+sum+"\n");
        return (randomMatrix);
    }
	// Start ant colony optimization to travelling salesperson problem
	public void start() 
    {
		solve();
    }
	public int[] solve() 
    {
        setAnts();
        clearTrail();
        for(int i=0;i<maxIt;i++)
        {
            moveAnts();
            updateTrails();
            updateBestTrail();
        }
        System.out.print("\nBest tour length: "+(tourLength-noOfCities));
        System.out.print("\nBest tour order: "+Arrays.toString(bestTourOrder));
        return (bestTourOrder.clone());
    }
	// Set the ants for the stimulation process
	private void setAnts() 
    {
        for(int i=0;i<noOfAnts;i++)
        {
            for(Ant ant:ants)
            {
                ant.clear();
                ant.visitCity(-1,random.nextInt(noOfCities));
            }
        }
        currindex=0;
    }
	// After each iteration we have to move the ants
	private void moveAnts() 
    {
        for(int i=currindex;i<noOfCities-1;i++)
        {
            for(Ant ant:ants)
            {
                ant.visitCity(currindex,selectNextC(ant));
            }
            currindex++;
        }
    }
	// Selecting the next city for ant to visit
	private int selectNextC(Ant ant) 
    {
        int t=random.nextInt(noOfCities-currindex);
        if(random.nextDouble()<randomFactor) 
        {
            int cityIndex=-1;
            for(int i=0;i<noOfCities;i++)
            {
                if(i==t && !ant.visited(i))
                {
                    cityIndex=i;
                    break;
                }
            }
            if(cityIndex!=-1)
                return cityIndex;
        }
        calcProbabilities(ant);
        double r=random.nextDouble();
        double total=0;
        for(int i=0;i<noOfCities;i++) 
        {
            total+=probabilities[i];
            if(total>=r) 
                return i;
        }
        throw new RuntimeException("There are no other cities");
    }
	// Calculate the probability of finding the next city
	private void calcProbabilities(Ant ant) 
    {
        int i=ant.t[currindex];
        double pheromone=0.0;
        for(int l=0;l<noOfCities;l++) 
        {
            if(!ant.visited(l)) 
                pheromone+=Math.pow(trails[i][l],alpha)*Math.pow(1.0/graph[i][l],beta);
        }
        for(int j=0;j<noOfCities;j++) 
        {
            if (ant.visited(j)) 
                probabilities[j]=0.0;
            else 
            {
                double numerator=Math.pow(trails[i][j],alpha)*Math.pow(1.0/graph[i][j],beta);
                probabilities[j]=numerator/pheromone;
            }
        }
    }
	// Update the trail used by the ant
	private void updateTrails() 
    {
        for(int i=0;i<noOfCities;i++) 
        {
            for(int j=0;j<noOfCities;j++) 
                trails[i][j]*=evaporation;
        }
        for(Ant a:ants) 
        {
            double contribution=pheromoneLeft/a.tLength(graph);
            for(int i=0;i<noOfCities-1;i++)
                trails[a.t[i]][a.t[i+1]]+=contribution;
            trails[a.t[noOfCities-1]][a.t[0]]+=contribution;
        }
    }
	// Updating the best solution
	private void updateBestTrail() 
    {
        if(bestTourOrder==null) 
        {
            bestTourOrder=ants.get(0).t;
            tourLength=ants.get(0).tLength(graph);
        }
        for(Ant a:ants) 
        {
            if(a.tLength(graph)<tourLength) 
            {
                tourLength=a.tLength(graph);
                bestTourOrder=a.t.clone();
            }
        }
    }
	// Clear the trail after stimulation
	private void clearTrail() 
    {
        for(int i=0;i<noOfCities;i++)
        {
            for(int j=0;j<noOfCities;j++)
                trails[i][j]=c;
        }
    }
}