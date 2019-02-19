import java.util.*;

public class AStar {
	private pNode root;
	private int[][] goal;
	
	// pass start state and goal state through constructor
	public AStar(int[][] root, int[][] goal) {
		this.root = new pNode(root, 0, null);
		this.goal = goal;
	}
	
	// the A-Star search function
	public void AStarSearch(int h) {
		
		Set<String> noRepeatState = new HashSet<String>();
		noRepeatState.add(Arrays.deepToString(root.getState()));
		
		if(root == null) return;
		// use GHCostComparator, A-Star Search --> f(n) = g(n) + h(n)
		
		// use different Comparator for different h(n)
		PriorityQueue<pNode> nodeQueue;
		if(h == 1) {
			nodeQueue = new PriorityQueue<pNode>(5, new AStarH1()); 
		}else if (h == 2) {
			nodeQueue = new PriorityQueue<pNode>(5, new AStarH2()); 
		}else if (h == 3) {
			nodeQueue = new PriorityQueue<pNode>(5, new AStarH3()); 
		}else {
			nodeQueue =  null;
			System.out.println("h unrecogizable");
		}
		
		nodeQueue.add(root);
		
		pNode currentNode = root;
		int solutionTime = 0;
		int solutionSpace = 0;
		while(!nodeQueue.isEmpty()) {
			
			// to get the max solutionSpace
			int nodeQueueSize = nodeQueue.size();
			if(solutionSpace < nodeQueueSize) {
				solutionSpace = nodeQueueSize;
			}

			// current node = node with min value in PriorityQueue (base on Comparator)
			currentNode = nodeQueue.poll();
			solutionTime++; // calculate solutionTime
			
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
				String stateString = Arrays.deepToString(thisState);
				
				// if this new generated state never repeat, add it to noRepeatState set and add it to the queue
				if(!noRepeatState.contains(stateString)) {

					noRepeatState.add(stateString);
					
					int thisCost = oneStateObject.getCost();
					String thisAction = oneStateObject.getAction();
					pNode child = new pNode(thisState, thisCost, thisAction);
					child.setPastCost(currentNode.getPastCost() + child.getCost());
					
					// pass different data to the child node based on h
					if(h == 1) {
						int thisNotMatchedPosNum = globalFunc.numNotMatchedPos(goal, thisState);
						child.setUnMatchedPosNum(thisNotMatchedPosNum);
					}else if(h == 2) {
						int thisManhattanSum = globalFunc.manhattanSum(goal, thisState);
						child.setManhattanSum(thisManhattanSum);
					}else if(h == 3) {
						int thisH3Manhattan = globalFunc.h3Manhattan(goal, thisState);
						child.setH3Manhattan(thisH3Manhattan);
					}else {
						System.out.println("h unrecogizable");
					}
					
					currentNode.addChild(child);
					child.addParent(currentNode);
					nodeQueue.add(child);
				}
			}	
		}
	}
}
