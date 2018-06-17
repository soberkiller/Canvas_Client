package canvasclient;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
public class Save_Grade 
{
	private double grade;
        private String path;
        
	public Save_Grade(double grade, String path) throws IOException
	{
            this.grade=grade;
            this.path=path;
            this.set_txt();
	}
	public void set_grade(double grade)
	{
		this.grade = grade;
	}
	public double get_grade()
	{
		return this.grade;
	}
	public void set_txt() throws IOException
	{
                FileOutputStream fs = new FileOutputStream(new File(path+"/grade.txt"));
		PrintStream p = new PrintStream(fs);
		p.println("Grade:"+this.grade);
		p.close();
	}
	/*public static void main(String argus[]) throws IOException 
	{
		double x=50.5;
		Save_Grade a=new Save_Grade(x);
		
	}*/
}
