package Minor_Project;
import java.util.*;
public class Run 
{
	public static void main(String[] args) 
	{
		Scanner sc=new Scanner(System.in);
		double c=1;
		double a=1;
		double b=5;
		double e=0.5;
		double phl=500;
		double af=0.8;
		double rf=0.01;
		int it=1000;
		int noc=5;
		AntColony obj=new AntColony(c,a,b,e,phl,af,rf,it,noc);
		obj.start();
		obj.display();
	}
}
