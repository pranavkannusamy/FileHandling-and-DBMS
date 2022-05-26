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

class FileSize implements Runnable
{
	private long ByteCount = 0;
	public String filePath,url,uname,pwd,query="select * from wctable",q;
	public int fz,n,flag=0,count=0;
	public FileSize(String fp,String ul,String un,String pd)
     {
		filePath=fp;
		url=ul;
		uname=un;
		pwd=pd;
	}
	public void run()
    {
	    Path path = Paths.get(filePath);
	    try 
        {
		ByteCount = Files.size(path);
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
			  fz=rs.getInt(7);
		  }
		  count++;
	    }
	    if(flag==0)
	    {
		  q="insert into wctable (SNo,FileName,FileSize) values ("+(count+1)+",'"+filePath+"',"+ByteCount+");";
		  int p = st.executeUpdate(q);
		  System.out.print(ByteCount+" ");
	    }
	    else
	    {
	    	if(fz!=ByteCount) {
    			  String q="UPDATE wctable SET FileSize="+ByteCount+" WHERE SNo="+n+";";
    			  int c = st.executeUpdate(q);
    			  System.out.print((int)ByteCount + " ");}
    			  else {
    			  String q="SELECT FileSize FROM wctable WHERE SNo="+n+";";
    			  ResultSet p = st.executeQuery(q);
    			  p.next();
    			  System.out.print(p.getInt("FileSize")+" ");}
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