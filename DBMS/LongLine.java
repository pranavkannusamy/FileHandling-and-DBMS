package UptDB;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class LongLine implements Runnable
{
	private static int MIN_VALUE = 0;
	public static BufferedReader reader = null;
	public String filePath,url,uname,pwd,query="select * from wctable",q;
	public int ll,n,flag=0,count=0;
	public LongLine(String fp,String ul,String un,String pd) 
      {
		filePath=fp;
		url=ul;
		uname=un;
		pwd=pd;
	}
	public void run()
    {
       int length=0,max=MIN_VALUE;
       try 
       {
    	   File file = new File(filePath);
         FileInputStream fileStream = new FileInputStream(file);
         InputStreamReader input = new InputStreamReader(fileStream);
         reader = new BufferedReader(input);
         String data=reader.readLine();
         while(data!=null) 
         {
  	     if(data.length()>max)
  	     max=data.length();
  	     data=reader.readLine();
         }
         length=max;
		     Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection(url,uname,pwd);
	     Statement st = con.createStatement();
	     ResultSet rs = st.executeQuery(query);
	     while(rs.next())
	     {
		  if(rs.getString(2).compareTo(filePath)==0)
		  {
			  flag=1;
			  n=rs.getInt(1);
			  ll=rs.getInt(6);
		  }
		  count++;
	     }
	     if(flag==0)
	     {
		  q="insert into wctable (SNo,FileName,LongLinelen) values ("+(count+1)+",'"+filePath+"',"+length+");";
		  int p = st.executeUpdate(q);
		  System.out.print(length+" ");
	     }
	     else
	     {
	    	 if(ll!=length) {
    			  String q="UPDATE wctable SET LongLinelen="+length+" WHERE SNo="+n+";";
    			  int c = st.executeUpdate(q);
    			  System.out.print(length + " ");}
    			  else {
    			  String q="SELECT LongLinelen FROM wctable WHERE SNo="+n+";";
    			  ResultSet p = st.executeQuery(q);
    			  p.next();
    			  System.out.print(p.getInt("LongLinelen")+" ");}
	     }
		  st.close();
		  con.close();
         }
         catch(Exception e)
         {
    	   e.printStackTrace();
         }
	}
}