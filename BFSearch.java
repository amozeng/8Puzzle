import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BFSearch {
	private pNode root;
	private int[][] goal;
	
	// pass start state and goal state through constructor
	public BFSearch(int[][] root, int[][] goal) {
		this.root = new pNode(root, 0, null);
		this.goal = goal;
	}
	
	// Breadth-First Search function
	public void bfs() {
		
		// a set to check if new state generated from successor is repeated
		Set<String> noRepeatState = new HashSet<String>();
		noRepeatState.add(Arrays.deepToString(root.getState()));
		
		if(root == null) return;
		
		// a node Queue
		Queue<pNode> nodeQueue = new LinkedList<pNode>();
		nodeQueue.add(root);
		
		pNode currentNode = null;
		int solutionTime = 0;
		int solutionSpace = 0;
		
		while(!nodeQueue.isEmpty()) {
			
			int nodeQueueSize = nodeQueue.size();
			if(solutionSpace < nodeQueueSize) {
				solutionSpace = nodeQueueSize;
			}
			
			// get the node we add first in the queue
			currentNode = nodeQueue.poll();
			solutionTime++;
			
			int[][] currentState = currentNode.getState();

			// found goal, stop
			if(globalFunc.isEqual(goal, currentState)) {
				globalFunc.printPath(currentNode, solutionTime, solutionSpace);
				break;
			}
			
			// successor function
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
					child.addParent(currentNode);
					nodeQueue.add(child);
				}
			}	
		}
	}
	
	
}
