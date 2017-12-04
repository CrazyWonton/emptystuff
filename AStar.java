import java.lang.*;
import java.io.*;
import java.util.*;

public class AStar {
	public static final int DIAGONAL_COST = 14;
	public static final int V_H_COST = 10;

	static class Cell{  
		int heuristicCost = 0; //Heuristic cost
		int finalCost = 0; //G+H
		int i, j;
		Cell parent; 

		Cell(int i, int j){
			this.i = i;
			this.j = j; 
		}

		@Override
			public String toString(){
				return "["+this.i+", "+this.j+"]";
			}
	}

	//Blocked cells are just null Cell values in grid
	static Cell [][] grid = new Cell[5][5];

	static PriorityQueue<Cell> open;

	static boolean closed[][];
	static int startI, startJ;
	static int endI, endJ;

	public static void setBlocked(int i, int j){
		grid[i][j] = null;
	}

	public static void setStartCell(int i, int j){
		startI = i;
		startJ = j;
	}

	public static void setEndCell(int i, int j){
		endI = i;
		endJ = j; 
	}

	static void checkAndUpdateCost(Cell current, Cell t, int cost){
		if(t == null || closed[t.i][t.j])return;
		int t_final_cost = t.heuristicCost+cost;

		boolean inOpen = open.contains(t);
		if(!inOpen || t_final_cost<t.finalCost){
			t.finalCost = t_final_cost;
			t.parent = current;
			if(!inOpen)open.add(t);
		}
	}

	public static void AStar(){ 

		//add the start location to open list.
		open.add(grid[startI][startJ]);

		Cell current;

		while(true){ 
			current = open.poll();
			if(current==null)break;
			closed[current.i][current.j]=true; 

			if(current.equals(grid[endI][endJ])){
				return; 
			} 

			Cell t;  
			if(current.i-1>=0){
				t = grid[current.i-1][current.j];
				checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

				if(current.j-1>=0){                      
					t = grid[current.i-1][current.j-1];
					checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
				}

				if(current.j+1<grid[0].length){
					t = grid[current.i-1][current.j+1];
					checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
				}
			} 

			if(current.j-1>=0){
				t = grid[current.i][current.j-1];
				checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
			}

			if(current.j+1<grid[0].length){
				t = grid[current.i][current.j+1];
				checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
			}

			if(current.i+1<grid.length){
				t = grid[current.i+1][current.j];
				checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

				if(current.j-1>=0){
					t = grid[current.i+1][current.j-1];
					checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
				}

				if(current.j+1<grid[0].length){
					t = grid[current.i+1][current.j+1];
					checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
				}  
			}
		} 
	}

	public static long test(int tCase, int x, int y, int si, int sj, int ei, int ej, int[][] blocked){
		//System.out.println("\n\nTest Case #"+tCase);
		//Reset
		grid = new Cell[x][y];
		closed = new boolean[x][y];
		open = new PriorityQueue<>((Object o1, Object o2) -> {
				Cell c1 = (Cell)o1;
				Cell c2 = (Cell)o2;

				return c1.finalCost<c2.finalCost?-1:
				c1.finalCost>c2.finalCost?1:0;
				});
		//Set start position
		setStartCell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part

		//Set End Location
		setEndCell(ei, ej); 

		for(int i=0;i<x;++i){
			for(int j=0;j<y;++j){
				grid[i][j] = new Cell(i, j);
				grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
				//                  System.out.print(grid[i][j].heuristicCost+" ");
			}
			//              System.out.println();
		}
		grid[si][sj].finalCost = 0;

		/*
		   Set blocked cells. Simply set the cell values to null
		   for blocked cells.
		 */
		for(int i=0;i<blocked.length;++i){
			setBlocked(blocked[i][0], blocked[i][1]);
		}
		final long startTime = System.nanoTime();
		AStar(); 
		final long endTime = System.nanoTime();

		if(closed[endI][endJ]){
			//Trace back the path
			int length = 0; 
			//System.out.println("Path: ");
			Cell current = grid[endI][endJ];
			//System.out.print(current);
			while(current.parent!=null){
				length++;
				//System.out.print(" -> "+current.parent);
				current = current.parent;
			} 
			average +=length;
			numTrials++;
			//System.out.println("Length = " + length);
		} 
		return (endTime - startTime);
	}
	static int average = 0;
	static int numTrials = 0;
	public static void generateStuff(int x, int y){
		//int casenum;
		//int x=10;
		//int y=10;
		int startx=1;
		int starty=1;
		int endx=x-1;
		int endy=y-1;
		int[][] blocked = new int[x*y][2];
		for(int i=0;i<(x*y);i++){
			blocked[i][0] = 0;
			blocked[i][1] = 0;	
		}
		int randomX;
		int randomY;
		int blockIndex = 0;
		System.out.println(blocked.length);
		for(int i=0;i<x*y;i++){	
			randomX = 0 + (int)(Math.random()*x)*i*17*13;
			randomY = 0 + (int)(Math.random()*y)*7*11;
			blocked[blockIndex][0] = randomX%x;
			blocked[blockIndex][1] = randomY%y;
			//System.out.println("["+(randomX%x)+","+(randomY%y)+"]");
			blockIndex++;

			long a = test(i,x,y,startx,starty,endx,endy,blocked);
			dij t = new dij();
			long d = t.convertInput(x,y,blocked);

			timeTaco tt = new timeTaco(a,d);
			mayo.add(tt);
		}
	}

	public static LinkedList<timeTaco> mayo;
	public static class timeTaco{
		long d;
		long a;

		public timeTaco(long dd,long aa){
			d = dd;
			a = aa;
		}
		public String toString(){
			return a + ", " + d;
		}
	}

	public static class dij{
		static final int V=9;
		int minDistance(int dist[], Boolean sptSet[]){
			int min = Integer.MAX_VALUE, min_index=-1;

			for (int v = 0; v < V; v++)
				if (sptSet[v] == false && dist[v] <= min)
				{
					min = dist[v];
					min_index = v;
				}

			return min_index;
		}

		void dijkstra(int graph[][], int src)
		{
			int dist[] = new int[V];

			Boolean sptSet[] = new Boolean[V];

			for (int i = 0; i < V; i++)
			{
				dist[i] = Integer.MAX_VALUE;
				sptSet[i] = false;
			}

			dist[src] = 0;

			for (int count = 0; count < V-1; count++)
			{

				int u = minDistance(dist, sptSet);

				sptSet[u] = true;

				for (int v = 0; v < V; v++)
					if (!sptSet[v] && graph[u][v]!=0 &&
							dist[u] != Integer.MAX_VALUE &&
							dist[u]+graph[u][v] < dist[v])
						dist[v] = dist[u] + graph[u][v];
			}
		}

		public long convertInput(int x, int y, int[][] blocked){
			int graph [][] = new int[x][y];
			for(int i=0;i<x;i++)
				for(int j=0;j<y;j++)
					graph[i][j]=1;
			int xi;
			int yi;
			for(int i=0;i<blocked.length;i++){
				xi = blocked[i][0];
				yi = blocked[i][1];
				graph[xi][yi]=0;	
			}
			dij t = new dij();
			final long startTime = System.nanoTime();
			t.dijkstra(graph,0);
			final long endTime = System.nanoTime();
			//System.out.println("DIJJJJ");
			return (endTime-startTime);
		}
	}

	public static void main(String[] args) throws IOException{   
		mayo = new LinkedList<>();
		generateStuff(100,100);
		//System.out.println("Length = " + (average/numTrials));
		//average = 0;
		//numTrials = 0;
		//generateStuff(100,100);
		//System.out.println("Length = " + (average/numTrials));
		BufferedWriter d = new BufferedWriter(new FileWriter(args[0]));
		BufferedWriter a = new BufferedWriter(new FileWriter(args[1]));
		for(timeTaco t:mayo){
			d.write(t.d+"\n");
			a.write(t.a+"\n");
			//System.out.println(t);
		}
		a.close();
		d.close();
	}
}
