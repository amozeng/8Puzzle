import java.util.*;
import java.util.concurrent.TimeUnit;

public class main {
	static int[][] easy = { {1,3,4}, {8,6,2}, {7,0,5}};
	static int[][] medium = { {2,8,1}, {0,4,3}, {7,6,5}};
	static int[][] hard = {{5,6,7}, {4,0,8}, {3,2,1}};
	static int[][] goal = { {1,2,3}, {8,0,4}, {7,6,5}};
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		int[][] level = hard;

//		BFSearch BreadthFirstSearch = new BFSearch(level, goal);
//		BreadthFirstSearch.bfs();		

//		DFSearch DepthFirstSearch = new DFSearch(level, goal);
//		DepthFirstSearch.dfs();
		
//		IDsearch IterativeDeepeningSearch = new IDsearch(level, goal);
//		int maxLevel = 40;
//		IterativeDeepeningSearch.ids(maxLevel);
		
//		UniformCost UniformCostSearch = new UniformCost(level, goal);
//		UniformCostSearch.uniformCostSearch();
		
//		BestFirstSearch BestFirstSearch = new BestFirstSearch(level, goal);
//		BestFirstSearch.BestFirstSearch();
		
		AStar aStarSearch = new AStar(level, goal);
		//aStarSearch.AStarSearch(1); // h1
//		aStarSearch.AStarSearch(2); // h2
		aStarSearch.AStarSearch(3); // h3
		
		long end = System.currentTimeMillis();
		long duration = end - start;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		//System.out.println("Total time: " + duration);

	}
}
