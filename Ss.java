package pla;

import java.util.ArrayList;
import java.util.List;

public class Ss//Class used to finding the longest subsequence common to all sequences in a set of  sequences 
{
	private String str;
	private List<String> lis=new ArrayList<String>();
	private List<String> samestr=new ArrayList<String>();
	private int score=0;
	public Ss()
	{
		
	}
	public static String getMaxSubString(String s1,String s2)  
    {  
        String max = "",min = "";  
        max = (s1.length()>s2.length())?s1: s2;  
        min = (max==s1)?s2: s1;         
        for(int x=0; x<min.length(); x++)  
        {  
            for(int y=0,z=min.length()-x; z!=min.length()+1; y++,z++)  
            {  
                String temp = min.substring(y,z);                 
                //sop(temp);  
                if(max.contains(temp))//if(s1.indexOf(temp)!=-1)  
                    return temp;  
            }  
        }  
        return "";  
    } 
	
	public List<String> getSameStr(String str1,String str2)
	{
		String Longest_str;
		int len;
		this.str=str1;
		Longest_str=getMaxSubString(this.str,str2);
		len= Longest_str.length();
		this.lis.add(String.valueOf(len));
		this.samestr.add(Longest_str);
		return lis;
	}
	public List<String> getsame()
	{
		return this.samestr;
	}
	public List<String> getleng()
	{
		return this.lis;
	}
	public int judgement()
	{
		String[] str=new String[this.getleng().size()];
		this.getleng().toArray(str);
		for(int i=0;i<str.length;i++)
		{
			if (Integer.parseInt(str[i])>10)
			{
				score=score+10;
			}
		}
		return score;
	}
	public static void main(String argus[])
	{
		String str1="abcdefghijklmnopqrstuvwxyz";
		String str2="bcdefghijklmnjklmnopqrstuvwxyz";
		String str3="abcdefghijklmnopqrstuvwxyz";
		String str4="njklmnopqrstuvwxyz";
		Ss ss1=new Ss();
		ss1.getSameStr(str1, str2);
		ss1.getSameStr(str1,str3);
		ss1.getSameStr(str1, str4);
		System.out.println(ss1.getleng());
		System.out.println(ss1.getsame());
		System.out.println(ss1.judgement());
	}

}
