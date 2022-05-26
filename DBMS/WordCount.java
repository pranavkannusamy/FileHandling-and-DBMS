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

class WordCount implements Runnable
{
	public static BufferedReader reader = null;
	public String filePath,url,uname,pwd,query="select * from wctable";
	public int wc,n,flag=0,count=0;
	public WordCount(String fp,String ul,String un,String pd)
      {
		filePath=fp;
		url=ul;
		uname=un;
		pwd=pd;
	}
	public void run()
      {
        int wordCount = 0;
        try
        {
          File file = new File(filePath);
          FileInputStream fileStream = new FileInputStream(file);
          InputStreamReader input = new InputStreamReader(fileStream);
          reader = new BufferedReader(input);
          String data=reader.readLine();
          while(data != null)
          {
            String[] words = data.split(" ");
            wordCount = wordCount + words.length;
            data=reader.readLine();
          }
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
				  wc=rs.getInt(4);
			  }
			  count++;
		  }
		  if(flag==0)
		  {
			 String q="insert into wctable (SNo,FileName,WordCount) values ("+(count+1)+",'"+filePath+"',"+wordCount+");";
			  int p = st.executeUpdate(q);
			  System.out.print(wordCount+" ");
		  }
		  else
		  {
			  if(wc!=wordCount) {
    			  String q="UPDATE wctable SET WordCount="+wordCount+" WHERE SNo="+n+";";
    			  int c = st.executeUpdate(q);
    			  System.out.print(wordCount + " ");}
    			  else {
    			  String q="SELECT WordCount FROM wctable WHERE SNo="+n+";";
    			  ResultSet p = st.executeQuery(q);
    			  p.next();
    			  System.out.print(p.getInt("WordCount")+" ");}
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