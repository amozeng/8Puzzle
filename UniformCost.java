import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class UniformCost {
	private pNode root;
	private int[][] goal;
	
	// pass start state and goal state through constructor
	public UniformCost(int[][] root, int[][] goal) {
		this.root = new pNode(root, 0, null);
		this.goal = goal;
	}
	
	// Uniform-Cost Search function
	public void uniformCostSearch() {
		
		// a set to check if new state generated from successor is repeated
		Set<String> noRepeatState = new HashSet<String>();
		noRepeatState.add(Arrays.deepToString(root.getState()));
		
		if(root == null) return;
		
		// use pastCostComparator, Uniform-Cost Search --> g(n)
		// use PriorityQueue to get the 
		PriorityQueue<pNode> nodeQueue = new PriorityQueue<pNode>(5, new pastCostComparator()); 
		nodeQueue.add(root);
		
		pNode currentNode = root;
		int solutionTime = 0;
		int solutionSpace = 0;
		while(!nodeQueue.isEmpty()) {
			int nodeQueueSize = nodeQueue.size();
			if(solutionSpace < nodeQueueSize) {
				solutionSpace = nodeQueueSize;
			}

			// current node = node with min value in PriorityQueue (base on Comparator)
			// Comparator --> g(n)
			currentNode = nodeQueue.poll();
			solutionTime++;
			
			int[][] currentState = currentNode.getState();

			
			if(globalFunc.isEqual(goal, currentState)) {;
				globalFunc.printPath(currentNode, solutionTime, solutionSpace);
				break;
			}
			ArrayList<State> currentNodeSuccessor = globalFunc.getSuccessors(currentState);
			
			for(State oneStateObject: currentNodeSuccessor) {
				int[][] thisState = oneStateObject.getState();
				int thisCost = oneStateObject.getCost();
				
				String stateString = Arrays.deepToString(thisState);
				
				// if this new generated state never repeat, 
				// add it to noRepeatState set and add it to the queue
				if(!noRepeatState.contains(stateString)) {
					
					noRepeatState.add(stateString);

					String thisAction = oneStateObject.getAction();
					pNode child = new pNode(thisState, thisCost, thisAction);
					child.setPastCost(currentNode.getPastCost() + child.getCost());
					
					currentNode.addChild(child);
					child.addParent(currentNode);
					nodeQueue.add(child);
				}
			}	
		}	
	}
}
