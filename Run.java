package Minor_Project;
import java.util.*;
public class Run
{
	public static void main(String[] args) 
	{
		Scanner sc=new Scanner(System.in);
		 double c,a,b,af,rf;
		 int it,noc;
		 //Input from user
		 System.out.println("Enter value for number of tails: ");
		 c=sc.nextDouble();
		 System.out.println("Enter value for alpha factor of ants: ");
		 a=sc.nextDouble();
		 System.out.println("Enter value for beta factor of ants: ");
		 b=sc.nextDouble();
		 System.out.println("Enter value for ant factor: ");
		 af=sc.nextDouble();
		 System.out.println("Enter value for random factor: ");
		 rf=sc.nextDouble();
		 System.out.println("Enter value for maximum number of iteration: ");
		 it=sc.nextInt();
		 System.out.println("Enter value for the number of cities: ");
		 noc=sc.nextInt();
		AntColony obj=new AntColony(c,a,b,af,rf,it,noc);
		//Start the program
		obj.start();
	}
}
