/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author Xiao
 */
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
}