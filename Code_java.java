package canvasclient;
import java.io.*;
public class Code_java 
{
	private String path,name_f,name;
	public Code_java(String name_f,String path,String name )
	{
		this.path=path;
		this.name="java "+name;
		this.name_f="javac "+name_f;
		try {
			   Process pro1 = Runtime.getRuntime().exec(this.name_f, null, new File(this.path));
			   Process pro2 = Runtime.getRuntime().exec(this.name, null, new File(this.path));
			   BufferedReader br = new BufferedReader(new InputStreamReader(pro2.getInputStream())); 
			   String msg = null;
			   while ((msg = br.readLine()) != null) 
			   {
			    System.out.println(msg);
			   }
			  } catch (IOException exception) {
			  } 
	}
	
	/*public static void main(String argus[])
	{
		String path="C:\\Study";
		String name_f="test.java";
		String name="test";
		Code_java a= new Code_java(name_f,path,name);
	}*/
}
