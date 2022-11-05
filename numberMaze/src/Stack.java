

public class Stack {
	private Object[] elements;
	private int top;
	
	public Stack(int capacity) {
		elements = new Object[capacity];
		top = -1;
	}
	public void push(Object data) {
		if(isFull()) {
			System.out.println("Stack is overflow");
		}
		else {
			top++;
			elements[top] = data;
		}
	}
	public Object pop() {
		if(isEmpty()) {
			System.out.println("Stack is empty");
			return null;
		}
		else {
			Object temp = elements[top];
			top--;
			return temp;
		}
	}
	public Object peek() {
		if(isEmpty()) {
			System.out.println("Stack is empty");
			return null;
		}
		else {
			return elements[top];
		}
	}
	public boolean isEmpty() {
		return (top == -1);
	}
	public boolean isFull() {
		return (top + 1 == elements.length);
	}
	public int size() {
		return top+1;
	}
}
