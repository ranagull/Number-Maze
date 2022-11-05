import java.awt.Color;
//Number class holds the attributes of numbers.
public class Number {
	//These are attributes of numbers.
    private int item;
    private String moveType;
    private Color color;
    private int scoreFactor;
    private boolean tried;
    private boolean moved;
    private Stack stack;
    
    public Number(int item) {
    	//Numbers' attributes are given according to their item.
    	//The users number and its attributes are created according to item.
        if(item == -2) {
            this.item = 5;
            this.color = Color.BLUE.darker();
            this.moveType = "user";
            this.scoreFactor = -1;
        }
        //Walls.
        else if(item == -1) {
            this.item = -1;
            this.color = Color.BLACK;
            this.moveType = "wall";
            this.scoreFactor = -1;
        }
        //Static screen numbers.
        else if(item < 4) {
        	this.moved = false;
            this.item = item;
            this.color = Color.GREEN.darker();
            this.moveType = "static";
            this.scoreFactor = 1;
        }
        //Random movement numbers.
        else if(item < 7) {
        	this.moved = false;
            this.item = item;
            this.color = Color.YELLOW.darker();
            this.moveType = "random";
            this.scoreFactor = 5;
        }
        //Pathfinding numbers.
        else if(item < 10) {
        	this.moved = false;
            this.item = item;
            this.color = Color.RED.darker();
            this.moveType = "pathFinding";
            this.scoreFactor = 25;
        }
        
    }
    //Getters setters and other methods.
    public Stack getStack() {
 		return stack;
 	}
    public Object popFromStack() {
    	return stack.pop();
    }
    public void addToStack(Object object) {
		stack.push(object);
	}
    public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public boolean isTried() {
		return tried;
	}

	public void setTried(boolean tried) {
		this.tried = tried;
	}

	public void display() {
        System.out.println(item);
    }
    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }
    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getScoreFactor() {
        return scoreFactor;
    }

    public void setScoreFactor(int scoreFactor) {
        this.scoreFactor = scoreFactor;
    }
}