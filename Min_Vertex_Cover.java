import java.lang.Math;
import java.io.*;
import java.util.*;

public class Min_Vertex_Cover{
	
	public static void main(String[] args) {
		
		int nodes = 0;	//number of nodes
		int [] [] graph;	//table which contains the graph
		int joins;	//number of joins
		int countj;		//counter for joins
		int flag = 0;	//flag for writing the input for sta-solver
		int print_flag = 0;	//flag for printing the output
		double dens = 0;	//density of the graph
		double limit;	//used to create the graph
		double randomNum;	//reandom number for creating the graph
		String fileName = "dimacs.wcnf";	//name of the created file
		
		int inp = Integer.parseInt(args[0]);	//read the argument
		Scanner sc= new Scanner(System.in);	
		
		try {
			
			if (inp == 0) {  
				System.out.print("Enter the path of the graph.txt file you want to give as input : ");  
				String str= sc.nextLine();	//read the path of the file
				str += "/graph.txt";	//add the name of the file to the path 
				sc.close();
				
				File myObj = new File(str);
			    Scanner myReader = new Scanner(myObj);  
				String data = myReader.nextLine();	//read the first line 
				nodes = Integer.parseInt(String.valueOf(data));	//save the number of the nodes
				data = myReader.nextLine();	//read the second line
				dens = Double.parseDouble(String.valueOf(data));	//save the density of the graph
				graph  = new int [nodes][nodes];	//create the table
				for (int i=0;i<nodes;i++) {
					data = myReader.nextLine();
					for (int y=0;y<2*nodes;y+=2) {
						graph [i][y/2] = Integer.parseInt(String.valueOf(data.charAt(y)));	//save the given graph
					}
				}
				
			}else {
				
				System.out.print("Enter the density of the graph: ");  
				dens= sc.nextDouble();	//reads the density
				System.out.print("Enter the number of the nodes: ");
				nodes = sc.nextInt();	//reads the number of the nodes
				joins = (int)(dens*nodes*(nodes-1))/2;	//calculate the number of joins
				
				graph  = new int [nodes][nodes];	//create the table
				for (int i=0;i<nodes;i++) {	//initialize the graph
					for (int y=0;y<2*nodes;y+=2) {
						graph [i][y/2] = 0;
					}
				}
				
				countj = 0;	
				limit = 0.5*dens;	//used for creating the graph - as the density increases also the limit increases so the possibility of creating a join is greater
				while (countj<joins) {
					int i = 0;
					while (i<nodes && countj<joins) {
						int y =0;
						while (y<2*nodes && countj<joins) {
							
							randomNum = Math.random();	//create a random number
							if (randomNum <= limit && graph [i][y/2] == 0 && i!=(y/2)) {	//if random number is in the limit and there is not join between the two nodes and it is not join with the same beginning and ending
								graph [i][y/2] = 1;	//create a non-directional join
								graph [y/2][i] = 1;
								countj += 1;	//count joins
							}
							y +=2;
						}
						i += 1;
					}
				}
			}
			try {
				
				File file = new File(fileName);
				FileWriter fw = new FileWriter(file);
			    BufferedWriter bw = new BufferedWriter(fw);
				
			    //create the input file
			    bw.write("h ");
				for(int i=0;i<nodes;i++) {
					for (int y=0;y<2*nodes;y+=2) {
						if (graph[i][y/2]==1) {
							if (flag == 0) {	// first time writing in the file
								flag = 1;
							}else {
								bw.write("1 ");
							}
							bw.write(Integer.toString(i+1));	//write the connected nodes
							bw.write(" ");
							bw.write(Integer.toString(y/2+1));	//write the connected nodes
							bw.write(" 0");	//end of line
							bw.newLine();
						}
					}
				}
				for(int i=1;i<=nodes;i++) {	//write the following to ensure that we take the minimum vertex cover
					for (int y=i+1;y<=nodes;y++) {
						if (flag == 0) {
							flag = 1;
						}else {
							bw.write("1 ");
						}
						bw.write("-");
						bw.write(Integer.toString(i));
						bw.write("  -");
						bw.write(Integer.toString(y));
						bw.write(" 0");
						bw.newLine();
					}
				}
			    bw.close();
			    fw.close();
			} catch (IOException e) {	//exception 
				e.printStackTrace();
		    }
			try {
				String command = "./cashwmaxsatplus dimacs.wcnf";	//call the sat-solver giving as argument the created file

				Process process = Runtime.getRuntime().exec(command);

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));	//read the output of the sat-solver

				System.out.print("The minimum vertex cover consists of nodes: ");
				if (dens == 0){	//if there are no joins
					for(int i=1;i<=nodes;i++){	// minimum vertex cover consists of all nodes
						if (print_flag == 0){
							System.out.print(i);
							print_flag = 1;
						}else{
							System.out.print(", " + i);
						}
					}
				}else{
					String line;
					int i = 2;
					while ((line = reader.readLine()) != null) {
						if (line.charAt(0) == 'v'){	//from sat-solver's answer
							String num = "";
							while (i < line.length()){
								if (line.charAt(i) == '-'){
									while (i < line.length() && line.charAt(i) != ' '){	//overcome negative variables
										i += 1;
										
									}
								} else {
									while (i < line.length() && line.charAt(i) != ' '){	//print positive variables
										num += line.charAt(i);
										i += 1;
									}
									if (print_flag == 0){
										System.out.print(num);
										print_flag = 1;
									}else{
										System.out.print(", " + num);
									}
									num = "";
								}
								i += 1;
							}
						}
					}
				}
				System.out.print(".");
				System.out.println("");
				reader.close();
			} catch (Exception e) {	//exception
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {	//exception 
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	}
}
