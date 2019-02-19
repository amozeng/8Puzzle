import java.util.*;


public class BestFirstSearch {
	private pNode root;
	private int[][] goal;

	// pass start state and goal state through constructor
	public BestFirstSearch(int[][] root, int[][] goal) {
		this.root = new pNode(root, 0, null);
		this.goal = goal;
	}
	
	// Best-First Search function
	public void BestFirstSearch() {
		
		// a set to check if new state generated from successor is repeated
		Set<String> noRepeatState = new HashSet<String>();
		noRepeatState.add(Arrays.deepToString(root.getState()));
		
		if(root == null) return;
		
		// PriorityQueue for BestFirstSearch
		PriorityQueue<pNode> nodeQueue = new PriorityQueue<pNode>(5, new bestFirstComparator()); 
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
			currentNode = nodeQueue.poll();
			solutionTime++;
			
			int[][] currentState = currentNode.getState();

			// if found the goal state, stop
			if(globalFunc.isEqual(goal, currentState)) {;
				globalFunc.printPath(currentNode, solutionTime, solutionSpace);
				break;
			}
			
			// run successor function
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
					int thisNotMatchedPosNum = globalFunc.numNotMatchedPos(goal, thisState);
					pNode child = new pNode(thisState, thisCost, thisAction);
					child.setUnMatchedPosNum(thisNotMatchedPosNum);
					
					currentNode.addChild(child);
					child.addParent(currentNode);
					nodeQueue.add(child);
				}
			}	
		}
		
	}
}

