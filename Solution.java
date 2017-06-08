public class Solution {
    
    public class Node{
        //node includes sapces (start,end] includes start index, excludes end index
        
        public int start; //start index
        public int end; //end index
        public int space; //reamining spaces from this node down
        public Node left;
        public Node right;
        
        
        public Node(int start, int end){
            this.start = start;
            this.end = end;
            this.space = end - start;
            this.left = null;
            this.right = null;
        }
    }
    
    public Node buildTree(Node node){
        //smallest node has a size of 1
        if(node.end - node.start <= 1){
            return node;
        }
        
        int split = (node.start + node.end) / 2; //left side has the remainder
        
        //System.out.println("node.start " + node.start + " split " +split + "node.end " + node.end);
        node.left = new Node(node.start, split);
        node.right = new Node(split, node.end);
        
        buildTree(node.left);
        buildTree(node.right);
        
        return node;
        
    }
    
    public int findPosition(Node node, int inFront){
        node.space -= 1; //visit node and remove a free space
        if(node.left == null){
            return node.start; //no children, return this node start index
        }
        if(node.left.space > inFront){ 
            //more spaces open to teh left of you than you are standing, traverse left
            return findPosition(node.left, inFront);
        }
        else{
            //else traverse right, taking out the number of empty spaces ot teh left of you.
            return findPosition(node.right, inFront - node.left.space);
        }
    }
    
    
	public ArrayList<Integer> order(ArrayList<Integer> heights, ArrayList<Integer> infronts) {
        Integer inputCount = heights.size();
	    
	    //build segment tree to keep track of open spaces on each side
	    Node openSpaceTree = new Node(0,inputCount);
	    buildTree(openSpaceTree);
	    
	    HashMap<Integer, Integer> heightMap = new HashMap<>();
	    ArrayList<Integer> result = new ArrayList<>(inputCount);
	    
	    
	    //need to map a height to the number taller than that height, not clear from problem
	    for(int i = 0; i< heights.size(); i++){
	        heightMap.put(heights.get(i), infronts.get(i));
	        result.add(null);
	    }
	    
	    //approach
	    //sort people so we insert shortest first
	    //can determine where shortest goes since he'll have exactly inFront people taller than them
	    //can determine how many tealler people are in front of you by counting the blank spaces
	    //since we are doing shortest first
	    
	    Collections.sort(heights);
	    
	    //O(n log(n))
	    
	    
	    for(Integer height : heights){
	        Integer numberInFront = heightMap.get(height);
	        Integer openTallerPersonSpace= -1;
	        
	        int openPosition = findPosition(openSpaceTree,numberInFront);
	        result.set(openPosition, height);
	    }
	    
	    
	    return result;
	}
}
