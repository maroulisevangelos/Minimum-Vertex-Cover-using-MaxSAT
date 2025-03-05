# Minimum-Vertex-Cover-using-MaxSAT
This project implements an algorithm to find the Minimum Vertex Cover of an undirected graph using Maximum Satisfiability (MaxSAT). The program is written in Java and operates in two modes: reading input from a file or generating a random graph.

Features:

  Input File Mode: Reads the graph details from a user-specified text file (graph.txt) that includes the number of nodes, graph density, and adjacency matrix.
  
  Random Graph Mode: Generates a random graph based on user-specified parameters (number of nodes and density).
  
  MaxSAT Solver Integration: Constructs a DIMACS format input file for a MaxSAT solver, runs the solver, and processes the output to find the Minimum Vertex Cover.
  
  Result Output: Prints the solution, including the nodes in the Minimum Vertex Cover.

Implementation Details:

  Input File Mode Execution:

    java Min_Vertex_Cover 0
    
  Random Graph Mode Execution:

    java Min_Vertex_Cover 1
    
DIMACS File Generation: For each cell in the adjacency matrix with a value of 1, writes the clause 1 Xi Xj 0 to dimacs.wcnf, where Xi and Xj are the nodes connected by the edge. Writes the clause 1 -Xi -Xj 0 for each Xj > Xi to ensure the SAT solver returns the Minimum Vertex Cover.

User Input: Prompts the user for the name of the graph.txt file (without the .txt extension) that contains the graph. The text file should be in the same directory as the code.

MaxSAT Solver: The solver used is cashwmaxsatplus. Please download an appropriate MaxSAT solver from the MaxSAT Evaluation website under the "complete solvers" category. Ensure the solver is placed in the same directory as the code.

Example Output:

    Minimum Vertex Cover: {Node1, Node2, Node3}

Files Included:

  Min_Vertex_Cover.java: Contains the implementation code.

All necessary code required for this project is included in this repository, except for the MaxSAT solver, which can be downloaded from the provided link.
