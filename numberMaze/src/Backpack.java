import java.awt.event.KeyEvent;

public class Backpack {
	//backpacks 1 and 2 are created as stacks
	public Stack backpack1;
	public Stack backpack2;
	Backpack(){
		backpack1 = new Stack(8);
		backpack2 = new Stack(8);
	}
	//Prints the backpacks and updates them when elements are added or moved.
	public void printBackpack(enigma.console.Console cn){
		int cx = 115;
		int cy = 7;
		cn.getTextWindow().setCursorPosition(cx,cy - 1);
		System.out.println("Backpacks");
		for(int i = 0; i < 4; i++) {
			cn.getTextWindow().setCursorPosition(cx,cy);
			for(int j = 0; j < 8; j++) {
				System.out.println("|");
				cy++;
				cn.getTextWindow().setCursorPosition(cx,cy);
			}
			System.out.println("+");
			if(i == 1) {
				cx += 3;
			}
			else {
				cx += 4;
			}	
			cy = 7;
		}
		Stack dummy = new Stack(8);
		int size = backpack1.size();
		while(!backpack1.isEmpty()) {
			cn.getTextWindow().setCursorPosition(117,cy + 8 - size);
			Object object = backpack1.pop();
			System.out.println(object);
			dummy.push(object);
			cy++;
		}
		while(!dummy.isEmpty()) {
			backpack1.push(dummy.pop());
		}
		cy = 7;
		size = backpack2.size();
		while(!backpack2.isEmpty()) {
			cn.getTextWindow().setCursorPosition(124,cy + 8 - size);
			Object object = backpack2.pop();
			System.out.println(object);
			dummy.push(object);
			cy++;
		}
		while(!dummy.isEmpty()) {
			backpack2.push(dummy.pop());
		}	
		cx = 116;
		cy = 15;
		for(int i = 0; i < 6; i++) {		
			cn.getTextWindow().setCursorPosition(cx,cy);
			System.out.println("-");
			if(i == 2) {
				cx += 5;
			}
			else {
				cx++;
			}
		}
		cn.getTextWindow().setCursorPosition(115,16);
		System.out.println("Left   Right");
		cn.getTextWindow().setCursorPosition(117,17);
		System.out.println("Q      W");
	}
	//Swaps the elements between backpacks.
	//Checks if it is okay to swap before doing so.
	public int swapElement(int rkey, int keypr,enigma.console.Console cn) {
		 if(keypr==1) {    // if keyboard button pressed
	            if(rkey==KeyEvent.VK_Q && !backpack1.isFull() && !backpack2.isEmpty()) {
	            	cn.getTextWindow().setCursorPosition(124,15 - backpack2.size());
	            	System.out.println(" ");
	            	backpack1.push(backpack2.pop());
	            }
	            else if(rkey == KeyEvent.VK_W && !backpack2.isFull() && !backpack1.isEmpty()) {
	            	cn.getTextWindow().setCursorPosition(117,15 - backpack1.size());
	            	System.out.println(" ");
	            	backpack2.push(backpack1.pop());
	            }
	       
	           return compare(cn);
		 }
		 else return -3;
	}
	//Compares if there are two same elements in the same backpack levels
	//Uses dummy stacks to empty the stacks and checks the stacks.
	public int compare(enigma.console.Console cn) {
		Stack holder1 = new Stack(8); 
		Stack holder2 = new Stack(8);
		int size1 = backpack1.size();
		int size2 = backpack2.size();
		int turn = -3;
		if(size1 < size2) {
			for(int i = 0; i < size2 - size1; i++) {
 				holder2.push(backpack2.pop());
			}
		}
		else {
			for(int i = 0; i < size1 - size2; i++) {
				holder1.push(backpack1.pop());
			}
		}
		while(!backpack1.isEmpty()) {
			if((int) backpack1.peek() == (int) backpack2.peek()) {
				cn.getTextWindow().setCursorPosition(117,15 - size1);
            	System.out.println(" ");
            	cn.getTextWindow().setCursorPosition(124,15 - size2);
            	System.out.println(" ");
            	backpack2.pop();
			   turn = (int) backpack1.pop();
				
			}
			else {
				 holder1.push(backpack1.pop());
				 holder2.push(backpack2.pop());
				 
			}			
		}
		
		while(!holder1.isEmpty()) {
			backpack1.push(holder1.pop());
		}
		while(!holder2.isEmpty()) {
			backpack2.push(holder2.pop());
		}
		return turn;
	}
	//Adds a new element to the left backpack.
	//If the backpack is full deletes the top element and inserts.
	public void add(int a) {
	        if(backpack1.isFull()) {
	            backpack1.pop();
	        }
	        backpack1.push(a);
	}
}