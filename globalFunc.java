import java.util.*;

// a class to record "state", "cost", "action" generated by successor function, and pass data to pNode class later
class State {
	private int[][] state;
	private int cost;
	private String action;

	public State(int[][] _state, int _cost, String _action) {
		this.state = _state;
		this.cost = _cost;
		this.action = _action;
	}

	public int[][] getState() {
		return state;
	}

	public int getCost() {
		return cost;
	}

	public String getAction() {
		return action;
	}
}


//a priorityQueue comparator for Best-First Search
class bestFirstComparator implements Comparator<pNode> {
	@Override
	public int compare(pNode o1, pNode o2) {
		if (o1.getUnMatchedPosNum() < o2.getUnMatchedPosNum()) {
			return -1;
		} else if (o1.getUnMatchedPosNum() > o2.getUnMatchedPosNum()) {
			return 1;
		}
		return 0;
	}
}

//a priorityQueue comparator for Uniform-Cost Search
class pastCostComparator implements Comparator<pNode> {
	@Override
	public int compare(pNode o1, pNode o2) {
		if (o1.getPastCost() < o2.getPastCost()) {
			return -1;
		} else if (o1.getPastCost() > o2.getPastCost()) {
			return 1;
		}
		return 0;
	}
}

//a priorityQueue comparator for A-Star h1
class AStarH1 implements Comparator<pNode> {
	@Override
	public int compare(pNode o1, pNode o2) {
		if (o1.getPastCost() + o1.getUnMatchedPosNum() < o2.getPastCost() + o2.getUnMatchedPosNum()) {
			return -1;
		} else if (o1.getPastCost() + o1.getUnMatchedPosNum() > o2.getPastCost() + o2.getUnMatchedPosNum()) {
			return 1;
		}
		return 0;
	}
}

//a priorityQueue comparator for A-Star h2
class AStarH2 implements Comparator<pNode> {
	@Override
	public int compare(pNode o1, pNode o2) {
		if ((o1.getManhattanSum() + o1.getPastCost()) < (o2.getManhattanSum() + o2.getPastCost())) {
			return -1;
		} else if ((o1.getManhattanSum() + o1.getPastCost()) > (o2.getManhattanSum() + o2.getPastCost())) {
			return 1;
		}
		return 0;
	}
}

// a priorityQueue comparator for A-Star h3
class AStarH3 implements Comparator<pNode> {
	@Override
	public int compare(pNode o1, pNode o2) {
		if (o1.getPastCost() + o1.getH3Manhattan() < o2.getPastCost() + o2.getH3Manhattan()) {
			return -1;
		} else if (o1.getPastCost() + o1.getH3Manhattan() >o2.getPastCost() + o2.getH3Manhattan()) {
			return 1;
		}
		return 0;
	}
} 

public class globalFunc {
	
	// customized isEqual function to compare two 2d-array is equal or not
	public static boolean isEqual(int[][] a, int[][] b) {
		final int N = 3;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (a[i][j] != b[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	// deep copy function for 2d array
	public static int[][] copyMatrix(int[][] a) {
		int[][] toReturn = new int[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				toReturn[i][j] = a[i][j];
			}
		}
		return toReturn;
	}

	// calculate number of not-matched position of current state, for A-Star h1
	public static int numNotMatchedPos(int[][] goal, int[][] toCampare) {
		int num = 0;
		for (int i = 0; i < goal.length; i++) {
			for (int j = 0; j < goal[0].length; j++) {
				if (toCampare[i][j] != goal[i][j]) {
					num++;
				}
			}
		}
		return num;
	}

	//  calculate Manhattan distance for A-Star h2
	public static int manhattanSum(int[][] goal, int[][] toCompare) {
		int sum = 0;
		for (int i = 0; i < goal.length; i++) {
			for (int j = 0; j < goal[0].length; j++) {
				for (int a = 0; a < toCompare.length; a++) {
					for (int b = 0; b < toCompare[0].length; b++) {
						if (toCompare[a][b] == goal[i][j]) {
							int path = Math.abs(i - a) + Math.abs(j - b);
							sum += path;
						}
					}
				}
			}
		}
		return sum;
	}
	
	// calculate another Manhattan distance for Star h3
	// if the h2 manhatton distance = 3, it means it need 3 moves, 
	// also means there will be 2 boxes between these need to move, then we need to add 2 more moves
	// which means the h3 manhatton distance is "h2ManhattonDistance+(h2ManhattonDistance-1)"
	public static int h3Manhattan(int[][] goal, int[][] toCompare) {
		int sum = 0;
		for (int i = 0; i < goal.length; i++) {
			for (int j = 0; j < goal[0].length; j++) {
				for (int a = 0; a < toCompare.length; a++) {
					for (int b = 0; b < toCompare[0].length; b++) {
						if (toCompare[a][b] == goal[i][j]) {
							int path = Math.abs(i - a) + Math.abs(j - b);
							sum += path + (path -1);
						}
					}
				}
			}
		}
		return sum;
	}
	
	// the successor function
	public static ArrayList<State> getSuccessors(int[][] state) {

		ArrayList<State> successors = new ArrayList<State>();

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				if (state[i][j] == 0) {
					if (i + 1 <= 2) { // able to move down
						String action = "MOVE DOWN";
						int[][] newState = globalFunc.copyMatrix(state);
						int costThisMove = newState[i + 1][j];
						newState[i + 1][j] = 0;
						newState[i][j] = costThisMove;
						State potentialMove = new State(newState, costThisMove, action);
						successors.add(potentialMove);
					}
					if (i - 1 >= 0) { // able to move up
						String action = "MOVE UP";
						int[][] newState = globalFunc.copyMatrix(state);
						int costThisMove = newState[i - 1][j];
						newState[i - 1][j] = 0;
						newState[i][j] = costThisMove;
						State potentialMove = new State(newState, costThisMove, action);
						successors.add(potentialMove);
					}
					if (j - 1 >= 0) { // able to move left
						String action = "MOVE LEFT";
						int[][] newState = globalFunc.copyMatrix(state);
						int costThisMove = newState[i][j - 1];
						newState[i][j - 1] = 0;
						newState[i][j] = costThisMove;
						State potentialMove = new State(newState, costThisMove, action);
						successors.add(potentialMove);
					}
					if (j + 1 <= 2) { // able to move right
						String action = "MOVE RIGHT";
						int[][] newState = globalFunc.copyMatrix(state);
						int costThisMove = newState[i][j + 1];
						newState[i][j + 1] = 0;
						newState[i][j] = costThisMove;
						State potentialMove = new State(newState, costThisMove, action);
						successors.add(potentialMove);
					}
				}
			}
		}
		return successors;
	}

	// print each step in the path, and final result
	public static void printPath(pNode lastNode, int _solutionTime, int _solutionSpace) {
		
		System.out.println("Path is : ");
		Stack<pNode> nodeStack = new Stack<pNode>(); // initialize a Stack (for each step)

		int solutionCost = 0;
		int length = 0;
		while (lastNode.getParent() != null) {
			nodeStack.push(lastNode);
			lastNode = lastNode.getParent();
			length++;
		}
		nodeStack.push(lastNode);

		while (!nodeStack.isEmpty()) {
			pNode current = nodeStack.pop();
			if (current.getAction() == null) {
				System.out.println("START state: " + Arrays.deepToString(current.getState()));
				System.out.println();
			} else {
				int currentCost = current.getCost();
				System.out.println("action: " + (current.getAction()));
				System.out.println("cost: " + currentCost);
				System.out.println("total cost: " + current.getPastCost());
				int[][] state  = current.getState();
				for(int i = 0; i < state.length; i++ ) {
					for (int j = 0; j < state[0].length; j++) {
						System.out.print(state[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println();
				solutionCost += currentCost;
			}
		}
		System.out.println("Length of solution path: " + length);
		System.out.println("Cost of solution path: " + solutionCost);
		System.out.println("Time(num of nodes popped off queue): " + _solutionTime); // (num of nodes popped off queue)
		System.out.println("Space(size of queue at its max): " + _solutionSpace); // (size of queue at its max)
	}
}
