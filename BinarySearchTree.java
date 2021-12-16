package BST;
public class BinarySearchTree {
    private static class Node {
        private int data;      // Node에서 저장하는 data
        private Node parent;    // 현재 Node의 부모를 가리킴
        private Node left;      // 현재 Node의 왼쪽 자식을 가리킴
        private Node right;     // 현재 Node의 오른쪽 자식을 가리킴
        
        /* [필수] 생성자 */
        public Node(int d) {
        	data = d;
        	parent = null;
        	left = null;
        	right = null;
        }
        
        /* [필수] getData() */
        public int getData() {
        	return data;

        }
        
        /* [필수] getParent() */
        public Node getParent() {
        	return parent;

        }
        
        /* [필수] getLeft() */
        public Node getLeft() {
        	return left;

        }
        
        /* [필수] getRight() */
        public Node getRight() {
        	return right;

        }
        
        /* [필수] setParent() */
        public void setParent(Node n) {
        	parent=n;

        }
        
        /* [필수] setLeft() */
        public void setLeft(Node n) {
        	left=n;
        }
        
        /* [필수] setRight() */
        public void setRight(Node n) {
        	right=n;
        }
    }
    
    /*
     * [Class] BinarySearchTree
     *   1) 개요
     *     - Tree의 맨 위를 가리키는 root가 있음
     *     - 데이터를 추가할 때는 작은값이 왼쪽, 큰값이 오른쪽으로 추가되게 해야 함
     *   2) 필수 operation
     *     - size(): BST의 크기를 나타내는 함수
     *     - getRoot(): BST의 root를 반환하는 함수
     *     - add(): BST에 data를 추가하는 함수
     *   3) 추가 operation
     *     - remove(): BST의 특정 data를 삭제하는 함수
     *     - inorder(): inorder 방식으로 tree를 순회하는 함수
     *     - preorder(): preorder 방식으로 tree를 순회하는 함수
     */
    
    private Node root;      // BST의 root
    private int size;       // BST의 size
    
    /* [필수] 생성자 */
    public BinarySearchTree() {
    	root = null;
    	size=0;
    }
    
    /*
     * [필수] size()
     *  - BST의 size를 리턴
     */
    public int size() {
    	return size;
    }
    
    /*
     * [필수] getRoot()
     *  - BST의 root를 리턴
     */
    public Node getRoot() {
    	return root;
    }
    
    /*
     * [필수] add()
     *  - BST에 값 추가
     *  - 다음의 세 가지 경우 중 하나
     *    1) root가 없으면 root로 설정
     *    2-1) 현재 보고 있는 노드의 값보다 작으면 왼쪽으로 이동
     *    2-2) 현재 보고 있는 노드의 값보다 크면 오른쪽으로 이동
     *    3) 만약 해당 위치가 비어있으면 거기에 추가
   
     */
    public void add(int data) {
        Node n = new Node(data);
        if (root == null) {
            root = n; 
        } 
        else {
            Node base_N = root;  
            Node p;            
            
            while(true) {
                p = base_N;
                
                if (p.data > data) {
                	base_N=p.left;
                    if (p.left == null) {       
                        p.setLeft(n);
                        n.setParent(p);
                        return; 
                    }
                } 
                else { 
                	base_N=p.right;
                    if (p.right == null) {       
                        p.setRight(n);
                        n.setParent(p);
                        return;
                        
                    }
                }
            }
        }
    }
    
    public Node rightMost(Node n) {
    	while(n.right != null) {
    		n=n.right;	
    	}
    	return n;
    }
    
    public Node leftMost(Node n) {
    	while(n.left != null) {
    		n=n.left;
    	}
    	return n;
    }
    
    /*
     *  remove()
     *    - BST의 특정 data를 삭제하는 함수
     *      1) leaf node일 경우 그냥 삭제
     *      2) internal node일 경우 삭제하고 아래쪽 node를 올림
     *         - 왼쪽 sub-tree(작은 값들) 중에 가장 큰 값 or 오른쪽 sub-tree(큰 값들) 중에 가장 작은 값 중 하나를 올림
     *         - 왼쪽 sub-tree 중에 가장 큰 값을 우선적으로 올림
     *    - data가 없으면 -1을 return
     *  
     */
    public int remove(int data) {
        Node base_N = root;
        Node p= root;

        while (base_N.data != data) {
        	  p = base_N;

              if (base_N.data > data) {
                  base_N = base_N.left;
              } 
              else {
                  base_N = base_N.right;
              }
              if (base_N == null) {
              	return -1;
              }
              
        }
        if(base_N.left==null && base_N.right==null) {
        	if(base_N==p.left) {
        		p.left=null;
        	}
        	else if(base_N==p.right) {
        		p.right=null;
        	}
        	else {
        		root=null;
        	}
        }
        if(base_N.left !=null && base_N.right !=null) {
        	if(base_N==root) {
        		base_N=base_N.left;
        		
        		Node store_N = rightMost(base_N);
        		
        		base_N.right=store_N.left;
        		store_N.left.setParent(base_N);
        		store_N.right=root.right;
        		store_N.left=root.left;
        		root=store_N;
        	}
        	else {
        		if(base_N.left !=null && base_N.right !=null) {
        			p = base_N;
        			Node Pb_N = base_N.parent;
        			
        			base_N=base_N.left;
        		
        			Node store_N = rightMost(base_N);
        		
        			base_N.right.right=store_N.left;
        			store_N.left.setParent(base_N.right);
        			store_N.right=p.right;
        			store_N.left=p.left;
        			root.left=store_N;
        			
        		}
        		else if(base_N.left==null){
        			p=base_N;
        			Node Pb_N = p.parent;
        			base_N=base_N.right;
        			Node store_N=leftMost(base_N);
        			
        			base_N.right=store_N.right;
        			store_N.setParent(base_N);
        		}
        		else {
        			p=base_N;
        			Node Pb_N = p.parent;
        			base_N=base_N.left;
        			Node store_N=rightMost(base_N);
        			
        		}
        		
        	}
        }
        return data;
    }
    
    /*
     *  preorder()
     *    - BST의 모든 데이터를 preorder 방식으로 출력
     *    - 출력 예시로 제공
     */
    public void preorder(Node n) {
    	 if (n != null) {
    		 	System.out.print(n.data + " ");
    	        preorder(n.left);
    	        preorder(n.right);
    	    }
    }
    
    /*
     *  inorder()
     *    - BST의 모든 데이터를 inorder 방식으로 출력
     *    - 출력 예시로 제공 (숫자가 정렬되어서 출력되는게 정상입니다)
     */
    public void inorder(Node n) {
    	 if (n != null) {
    	        inorder(n.left);
    	        System.out.print(n.data + " ");
    	        inorder(n.right);
    	    }
    }
    
    /*
     *  inorder()
     *    - BST의 모든 데이터를 postorder 방식으로 출력
     *    - 형식은 preorder 참조
     *      예시) 1 2 3 4 5 
     */
    public void postorder(Node n) {
    	 if (n != null) {
    	        postorder(n.left);
    	        postorder(n.right);
    	        System.out.print(n.data + " ");
    	 }
    }

}
