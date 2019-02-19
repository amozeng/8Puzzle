import java.util.*;

public class IDsearch {
	private pNode root;
	private int[][] goal;

	// pass start state and goal state through constructor
	public IDsearch(int[][] root, int[][] goal) {
		this.root = new pNode(root, 0, null);
		this.root.setLength(0);
		this.goal = goal;
	}

	// Iterative-Deepening Search function
	public void ids(int maxLevel) {
		
		if(root == null) return;
		
		int solutionTime = 0;
		int solutionSpace = 0;
		
		// if depth get bigger than i, stop
		// add 1 to i
		// start from beginning again
		outerLoop:
		for(int i = 0; i < maxLevel; i++) {
			
			// a set to check if new state generated from successor is repeated
			Set<String> noRepeatState = new HashSet<String>();
			noRepeatState.add(Arrays.deepToString(root.getState()));
			
			// create a stack for Iterative-Deepening Search
			Stack<pNode> nodeStack = new Stack<pNode>();
			nodeStack.push(root);
			pNode currentNode = null;
			
			whileLoop:
			while(!nodeStack.isEmpty()) {
				
				// find stack max size (solutionSpace)
				int nodeStackSize = nodeStack.size();
				if(solutionSpace < nodeStackSize) {
					solutionSpace = nodeStackSize;
				}
				
				// get the last-added node from the stack
				currentNode = nodeStack.pop();
				solutionTime++;

				// found the goal, stop
				if(globalFunc.isEqual(goal, currentNode.getState())) {
					// find goal and jump out of this loop
					globalFunc.printPath(currentNode, solutionTime, solutionSpace);
					break outerLoop;
				}
				
				int currentNodeLength = currentNode.getLength();
				ArrayList<State> currentNodeSuccessor = globalFunc.getSuccessors(currentNode.getState());
				for(State oneStateObject: currentNodeSuccessor) {
					int[][] thisState = oneStateObject.getState();
					int thisCost = oneStateObject.getCost();
					String thisAction = oneStateObject.getAction();
					String stateString = Arrays.deepToString(thisState);
					
					// if this new generated state never repeat, 
					// add it to noRepeatState set and add it to the queue
					if(!noRepeatState.contains(stateString)) {

						noRepeatState.add(stateString);
						pNode child = new pNode(thisState, thisCost, thisAction);
						currentNode.addChild(child);
						int childLength = currentNodeLength + 1; // calculate length for this child
						child.setLength(childLength);
						child.addParent(currentNode);
						
						// if the length for this child is bigger than i
						// continue with the next last-added node on the stack
						if (child.getLength() > i) continue whileLoop;
						// if not, add this child node on stak
						nodeStack.add(child);

					}
				}	
			}
		}
		
		/*
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
					// find goal and jump out of this loop
					globalFunc.printPath(currentNode, solutionTime, solutionSpace);
					break outerLoop;
				}
				
				System.out.println("current is: " + Arrays.deepToString(currentState));

				ArrayList<State> currentNodeSuccessor = globalFunc.getSuccessors(currentState);
				
				for(State oneStateObject: currentNodeSuccessor) {
					int[][] thisState = oneStateObject.getState();
					int thisCost = oneStateObject.getCost();
					String thisAction = oneStateObject.getAction();
					String stateString = Arrays.deepToString(thisState);
					int thisNotMatchedPosNum = globalFunc.numNotMatchedPos(goal, thisState);
					
					if(!noRepeatState.contains(stateString)) {

						noRepeatState.add(stateString);
						pNode child = new pNode(thisState, thisCost, thisAction, thisNotMatchedPosNum);
						currentNode.addChild(child);
						nodeStack.add(child);
						child.addParent(currentNode);
					}
				}	
			}
		*/
	}
}
