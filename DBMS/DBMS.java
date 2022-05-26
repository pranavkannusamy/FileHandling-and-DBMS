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

public class DBMS {

public static String COMMAND,PWD="",URL="jdbc:mysql://localhost:3306/pranavdbtable",UNAME="root";
public static String[] FILE_PATH = new String[100];
public static void main(String[] args) throws Exception 
{
    int i,j,z=0;
    String a="";
    Scanner sc = new Scanner(System.in);
		do 
		{
			System.out.println("Enter the Password: ");
			PWD = sc.nextLine();
			if(PWD.compareTo("Pranav@112002")==0)
			{
				System.out.println("Database Connected");
			}
			else
			{
				System.out.println("Incorrect Password");
			}
			
		}while(PWD.compareTo("Pranav@112002")!=0);
		do
		{
		  System.out.println("Enter the Command");
		  System.out.print("$ ");
		  COMMAND=sc.nextLine();
		  String[] str=COMMAND.split(" ");
		  if(str.length==1)
		  {
		    a=str[0];
		    z=1;
		  }
		  else if(str.length>=2)
		  {
		    for(j=0;j<str.length;j++)
		    {
		      if((str[j].compareTo("--help")!=0) && (str[j].compareTo("wc")!=0) && (str[j].compareTo("-l")!=0) && (str[j].compareTo("-c")!=0) &&
		        (str[j].compareTo("-m")!=0) && (str[j].compareTo("-L")!=0) && (str[j].compareTo("-w")!=0))
		      {
			     FILE_PATH[z++]=str[j];
		      }
		      else
		      {
		       a=str[j];
		      }
		    }
		 }
		for(i=0;i<z;i++)
		{
		if(a.compareTo("wc")==0)
		{
		  LineCount lc = new LineCount(FILE_PATH[i],URL,UNAME,PWD);
		  WordCount wc = new WordCount(FILE_PATH[i],URL,UNAME,PWD);
		  CharCount cc = new CharCount(FILE_PATH[i],URL,UNAME,PWD);
		  Print pf = new Print(FILE_PATH[i]);
		  lc.run();
		  wc.run();
		  cc.run();
		  pf.run();
		}
		else if(a.compareTo("-m")==0) 
		{ 
		 CharCount cc = new CharCount(FILE_PATH[i],URL,UNAME,PWD);
		 Print pf = new Print(FILE_PATH[i]);
		 cc.run();
		 pf.run();
		} 
		else if(a.compareTo("-l")==0)
		{
		 LineCount lc = new LineCount(FILE_PATH[i],URL,UNAME,PWD);
		 Print pf = new Print(FILE_PATH[i]);
		 lc.run();
		 pf.run();
		}
		else if(a.compareTo("-w")==0)
		{
		 WordCount wc = new WordCount(FILE_PATH[i],URL,UNAME,PWD);
		 Print pf = new Print(FILE_PATH[i]);
		 wc.run();
		 pf.run();
		}
		else if(a.compareTo("-c")==0)
		{
		 FileSize fz = new FileSize(FILE_PATH[i],URL,UNAME,PWD);
		 Print pf = new Print(FILE_PATH[i]);
		 fz.run();
		 pf.run();
		}
		else if(a.compareTo("-L")==0)
		{
		 LongLine ll = new LongLine(FILE_PATH[i],URL,UNAME,PWD);
		 Print pf = new Print(FILE_PATH[i]);
		 ll.run();
		 pf.run();
		}
		else if(a.compareTo("--help")==0)
		{
		 System.out.println("Enter '-w' to get Word the Count");
		 System.out.println("Enter '-m' to get Character Count");
		 System.out.println("Enter '-l' to get Line Count");
		 System.out.println("Enter '-L' to get the Length of the Longest Line");
		 System.out.println("Enter '-c' to get the Filesize");
		 break;
		}
		else
		{
		 System.out.println("Enter The Command Properly");
		}
		}
		z=0;
		}while((a.compareTo("wc")!=0) || (a.compareTo("-m")!=0) || (a.compareTo("-l")!=0) || (a.compareTo("-L")!=0) || (a.compareTo("-c")!=0) || (a.compareTo("-c")!=0) || (a.compareTo("--help")!=0));
	}
  }


