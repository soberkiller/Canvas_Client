package canvasclient;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
public class Save_Comment 
{
	private String comment;
        private String path;
	public Save_Comment(String comment,String path) throws IOException
	{
		this.comment=comment;
                this.path=path;
		this.set_txt();
	}
	public void set_comment(String comment)
	{
		this.comment = comment;
	}
	public String get_comment()
	{
		return this.comment;
	}
	public void set_txt() throws IOException
	{
		FileOutputStream fs = new FileOutputStream(new File(path+"/comment.txt"));
		PrintStream p = new PrintStream(fs);
		p.println("Comment:"+this.comment);
		p.close();
	}
	/*public static void main(String argus[]) throws IOException 
	{
		String x="Well done!";
		Save_Comment a=new Save_Comment(x);
		
	}*/
}
