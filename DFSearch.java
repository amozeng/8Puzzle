import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFSearch {
	private pNode root;
	private int[][] goal;
	
	// pass start state and goal state through constructor
	public DFSearch(int[][] root, int[][] goal) {
		this.root = new pNode(root, 0, null);
		this.goal = goal;
	}
	
	
	public void dfs() {
		
		if(root == null) return;
		
		// a set to check if new state generated from successor is repeated
		Set<String> noRepeatState = new HashSet<String>();
		noRepeatState.add(Arrays.deepToString(root.getState()));
		
		Stack<pNode> nodeStack = new Stack<pNode>();
		nodeStack.push(root);
		
		pNode currentNode = null;
		
		int solutionTime = 0;
		int solutionSpace = 0;
		
		while(!nodeStack.empty()) {
			
			// find stack max size (solutionSpace)
			int nodeStackSize = nodeStack.size();
			if(solutionSpace < nodeStackSize) {
				solutionSpace = nodeStackSize;
			}
			
			currentNode = nodeStack.pop();
			solutionTime++; // (solutionTime, num of node popped off the stack)
			
			
			int[][] currentState = currentNode.getState();

			if(globalFunc.isEqual(goal, currentState)) {
				
				globalFunc.printPath(currentNode, solutionTime, solutionSpace);

				break;
			}
			
			//System.out.println("current is: " + Arrays.deepToString(currentState));

			ArrayList<State> currentNodeSuccessor = globalFunc.getSuccessors(currentState);
			
			for(State oneStateObject: currentNodeSuccessor) {
				int[][] thisState = oneStateObject.getState();
				
				String stateString = Arrays.deepToString(thisState);
				
				// if this new generated state never repeat, 
				// add it to noRepeatState set and add it to the queue
				if(!noRepeatState.contains(stateString)) {

					noRepeatState.add(stateString);
					
					int thisCost = oneStateObject.getCost();
					String thisAction = oneStateObject.getAction();
					pNode child = new pNode(thisState, thisCost, thisAction);
					currentNode.addChild(child);
					nodeStack.add(child);
					child.addParent(currentNode);
				}
			}	
		}
	}
}
