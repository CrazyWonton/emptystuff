Author: Kevin Tarczali
Date: 12/8/2017
Course: CS 375
Assignment: A* Presentation
---------------------------------------------
Contents:
	1 - How to Compile
	2 - How to Run
	3 - Data Structures Used & Implementation
	4 - Analysis of Computation Time
	5 - Classes Used
	6 - Control Flow of Classes
---------------------------------------------	
1 - How to Compile
	~> javac AStar.java
---------------------------------------------
2 - How to Run
	~> java AStar <out file 1> <out file 2>
---------------------------------------------
3 - Data Structures Used & Implementation
	Array
		int dist[] = new int[V];
	2D Array
		static boolean closed[][];
	Linked List
		public static LinkedList<timeTaco> mayo;
	Priority Queue
		static PriorityQueue<Cell> open;
---------------------------------------------
4 - Analysis of Computation Time
	A* - O(b^d)	where b is the branching factor and
		d is the depth of the solution
	Dijkstra's - O((|E|+|V|))log|V|) where E is the
		number of edges and V is the number of vertices
---------------------------------------------
5 - Classes Used
		dij = Runs Dijkstras shortest path algorithm 
		AStar = Runs the A* algorithm 
		Cell = A node class for A* Algorithm
		timeTaco = Holds times for algorithm runtime
---------------------------------------------
6 - Control Flow of Classes
	AStar makes instances of Cells for the algorithm. 
	AStar then generates a randomized graph. The graph
	is run through the A* algorithm then the time is
	recorded in an instance of timeTaco. AStar calls
	dij to run Dijkstra's shortest path algorithm. The
	taken is recorded in the same instance of timeTaco.
	AStar then generates another graph and repeats the
	process.
	
		
	