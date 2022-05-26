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

class LineCount implements Runnable
{
	public static BufferedReader reader = null;
	public String filePath,url,uname,pwd,query="select * from wctable";
	public int lc,n,flag=0,count=0;
	public LineCount(String fp,String ul,String un,String pd)
      {
		filePath=fp;
		url=ul;
		uname=un;
		pwd=pd;
	}
	public void run()
    {
      int LineCount=0;
	  try 
        {
		  File file = new File(filePath);
	      FileInputStream fileStream = new FileInputStream(file);
	      InputStreamReader input = new InputStreamReader(fileStream);
	      reader = new BufferedReader(input);
	      String data=reader.readLine();
	      while(data != null)
          {
	         LineCount++;
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
				  lc=rs.getInt(3);
			  }
			  count++;
		  }
		  if(flag==0)
		  {
			 String q="insert into wctable (SNo,FileName,LineCount) values ("+(count+1)+",'"+filePath+"',"+LineCount+");";
			  int p = st.executeUpdate(q);
			  System.out.print(LineCount+" ");
		  }
		  else
		  {
			  if(lc!=LineCount) {
			  String q="UPDATE wctable SET LineCount="+LineCount+" WHERE SNo="+n+";";
			  int c = st.executeUpdate(q);
			  System.out.print(LineCount + " ");}
			  else {
			  String q="SELECT LineCount FROM wctable WHERE SNo="+n+";";
			  ResultSet p = st.executeQuery(q);
			  p.next();
			  System.out.print(p.getInt("LineCount")+" ");}
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