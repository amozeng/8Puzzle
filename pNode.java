import java.util.*;

public class pNode {

	private int state[][];
	
	
	public pNode(int _state[][], int _cost, String _action) {   //constructor
		this.state = _state;
		this.cost = _cost;
		this.action = _action;
		childrenNode = new ArrayList<pNode>();
	}         
	
	private pNode parentNode;
	private ArrayList<pNode> childrenNode;
	
	//bookkeeping
	private int cost;
	private String action;
	private int pastCost, GHCost;
	private int unMatchedPosNum;
	private int manhattanSum, h3Manhattan;
	private int length;
	
	
	public int[][] getState() {
		return this.state;
	}
	
	public void addChild(pNode node) {
		this.childrenNode.add(node);
	}

	public ArrayList<pNode> getChildren(){
		return childrenNode;
	}
	
	public void addParent(pNode node) {
		this.parentNode = node;
	}
	
	public pNode getParent() {
		return parentNode;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int _cost) {
		this.cost = _cost;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String _action) {
		this.action = _action;
	}
	
	public int getUnMatchedPosNum() {
		return unMatchedPosNum;
	}
	
	public void setUnMatchedPosNum(int _unMatchedPosNum) {
		this.unMatchedPosNum = _unMatchedPosNum;
	}
	
	public int getManhattanSum() {
		return manhattanSum;
	}
	
	public void setManhattanSum(int _manhattanSum) {
		this.manhattanSum = _manhattanSum;
	}
	
	public int getH3Manhattan() {
		return h3Manhattan;
	}
	
	public void setH3Manhattan(int _h3Manhattan) {
		this.h3Manhattan = _h3Manhattan;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int _length) {
		this.length = _length;
	}
	
	public void setPastCost(int _pastCost) {
		this.pastCost = _pastCost;
	}
	
	public int getPastCost() {
		return pastCost;
	}
	
	public int getGHCost() {
		if(this.parentNode != null) {
			this.GHCost = this.parentNode.getGHCost() + this.cost + this.unMatchedPosNum;
		}else {
			this.GHCost = this.cost + this.unMatchedPosNum;
		}
		return GHCost;
	}

	
//	public ArrayList<int[][]> getSuccessors(){
//		ArrayList<int[][]> successors = new ArrayList<int[][]>();
//
//		if(this.state[0][0] == 0) {
//			int[][] moveRight = this.state;
//			int tmp = moveRight[0][0];
//			moveRight[0][0] = moveRight[0][1];
//			moveRight[0][1] = tmp;
//			successors.add(moveRight);
//			int[][] moveDown = this.state;
//			tmp = moveDown[0][0];
//			moveDown[0][0] = moveDown[1][0];
//			moveDown[1][0] = tmp;
//			successors.add(moveDown);
//		}else if (this.state[0][1] == 0) {
//			int[][] moveLeft = this.state;
//			int tmp = moveLeft[0][0];
//			moveLeft[0][0] = moveLeft[0][1];
//			moveLeft[0][1] = tmp;
//			successors.add(moveLeft);
//			int[][] moveDown = this.state;
//			tmp = moveDown[1][1];
//			moveDown[1][1] = moveDown[0][1];
//			moveDown[0][1] = tmp;
//			successors.add(moveDown);
//			int[][] moveRight = this.state;
//			tmp = moveRight[0][2];
//			moveRight[0][2] = moveRight[0][1];
//			moveRight[0][1] = tmp;
//			successors.add(moveRight);
//		}
//		else if (this.state[0][2] == 0) {
//			int[][] moveLeft = this.state;
//			int tmp = moveLeft[0][1];
//			moveLeft[0][1] = moveLeft[0][2];
//			moveLeft[0][2] = tmp;
//			successors.add(moveLeft);
//			
//			int[][] moveDown = this.state;
//			tmp = moveDown[1][2];
//			moveDown[1][2] = moveDown[0][2];
//			moveDown[0][2] = tmp;
//			successors.add(moveDown);
//		}else if (this.state[1][0] == 0) {
//			int[][] moveUp = this.state;
//			int tmp = moveUp[0][0];
//			moveUp[0][0] = moveUp[1][0];
//			moveUp[1][0] = tmp;
//			successors.add(moveUp);
//			
//			int[][] moveRight = this.state;
//			tmp = moveRight[1][1];
//			moveRight[1][1] = moveRight[1][0];
//			moveRight[1][0] = tmp;
//			successors.add(moveRight);
//			
//			int[][] moveDown = this.state;
//			tmp = moveDown[2][0];
//			moveDown[2][0] = moveDown[1][0];
//			moveDown[1][0] = tmp;
//			successors.add(moveDown);
//		}else if (this.state[1][1] == 0) {  // center
//			int[][] moveUp = this.state;
//			int tmp = moveUp[0][1];
//			moveUp[0][1] = moveUp[1][1];
//			moveUp[1][1] = tmp;
//			successors.add(moveUp);
//			
//			int[][] moveLeft = this.state;
//			tmp = moveLeft[1][0];
//			moveLeft[1][0] = moveLeft[1][1];
//			moveLeft[1][1] = tmp;
//			successors.add(moveLeft);
//			
//			int[][] moveRight = this.state;
//			tmp = moveRight[1][2];
//			moveRight[1][2] = moveRight[1][1];
//			moveRight[1][1] = tmp;
//			successors.add(moveRight);
//			
//			int[][] moveDown = this.state;
//			tmp = moveDown[2][1];
//			moveDown[1][1] = moveDown[1][1];
//			moveDown[1][1] = tmp;
//			successors.add(moveDown);
//		}else if (this.state[1][2] == 0) {  // middleRight
//			int[][] moveUp = this.state;
//			int tmp = moveUp[0][2];
//			moveUp[0][2] = moveUp[1][2];
//			moveUp[1][2] = tmp;
//			successors.add(moveUp);
//			
//			int[][] moveLeft = this.state;
//			tmp = moveLeft[1][1];
//			moveLeft[1][1] = moveLeft[1][2];
//			moveLeft[1][2] = tmp;
//			successors.add(moveLeft);
//			
//			int[][] moveDown = this.state;
//			tmp = moveDown[2][2];
//			moveDown[2][2] = moveDown[1][2];
//			moveDown[1][2] = tmp;
//			successors.add(moveDown);
//		}else if (this.state[2][0] == 0) {  // lowLeft
//			int[][] moveUp = this.state;
//			int tmp = moveUp[1][0];
//			moveUp[1][0] = moveUp[2][0];
//			moveUp[2][0] = tmp;
//			successors.add(moveUp);
//			
//			int[][] moveRight = this.state;
//			tmp = moveRight[2][1];
//			moveRight[2][1] = moveRight[2][0];
//			moveRight[2][0] = tmp;
//			successors.add(moveRight);
//		}else if (this.state[2][1] == 0) {  // lowCenter
//			int[][] temp = new int[3][3];
//			
//			int[][] moveUp = new int[3][3];
//			for(int i=0; i<this.state.length; i++)
//				  for(int j=0; j<this.state[i].length; j++)
//				    moveUp[i][j]=this.state[i][j];
//			int tmp = moveUp[1][1];
//			moveUp[1][1] = moveUp[2][1];
//			moveUp[2][1] = tmp;
//			System.out.println("after move up is:");
//			System.out.println(Arrays.deepToString(moveUp));
//			successors.add(moveUp);
//			
//
//			
//			int[][] moveLeft = new int [3][3];
//			for(int i=0; i<this.state.length; i++)
//				  for(int j=0; j<this.state[i].length; j++)
//				    moveLeft[i][j]=this.state[i][j];
//			//System.out.print("ML"+Arrays.deepToString(moveLeft));
//			tmp = moveLeft[2][0];
//			moveLeft[2][0] = moveLeft[2][1];
//			moveLeft[2][1] = tmp;
//			successors.add(moveLeft);
//			System.out.println("after move left:");
//			System.out.println(Arrays.deepToString(moveLeft));
//			
//			int[][] moveRight = this.state;
//			tmp = moveRight[2][2];
//			moveRight[2][2] = moveRight[2][1];
//			moveRight[2][1] = tmp;
//			successors.add(moveRight);
//			System.out.println("after move Right:");
//			System.out.println(Arrays.deepToString(moveRight));
//		}else if (this.state[2][2] == 0) {  // lowRight
//			int[][] moveUp = this.state;
//			int tmp = moveUp[1][2];
//			moveUp[1][2] = moveUp[2][2];
//			moveUp[2][2] = tmp;
//			successors.add(moveUp);
//			
//			int[][] moveLeft = this.state;
//			tmp = moveLeft[2][1];
//			moveLeft[2][1] = moveLeft[2][2];
//			moveLeft[2][2] = tmp;
//			successors.add(moveLeft);
//		}
//		return successors;
//	}
}
