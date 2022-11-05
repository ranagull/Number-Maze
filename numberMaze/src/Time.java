public class Time {
	//Number values to check each time.
    public long time;
    public long qTime;
    public long mTime; 
    public long gTime;
    public int seconds;
    public int minutes;
    Time(){
    	//Gets the current time when time class is created.
        time = System.currentTimeMillis();
        qTime = System.currentTimeMillis();
        mTime = System.currentTimeMillis();      
        gTime =  System.currentTimeMillis();;
        seconds = 0;
        minutes = 0;
    }
    //Prints the game time in the screen.
    public void printTime(enigma.console.Console cn){
        cn.getTextWindow().setCursorPosition(115,22);
        if(seconds % 60 == 0 && seconds != 0) {
            minutes++;
            seconds = 0;
        }
        System.out.print("Time: " + minutes + ":" + seconds);

        if(time + 1000 < System.currentTimeMillis()) {
            time+=1000;
            seconds++;
        }

    }
    //Checks if enough time has passed so that an action can be done.
    //Mode declares which time variable is checked.
    public boolean checkTime(long passedTime, int mode) {
        if(mode == 1) {
             if(qTime + passedTime < System.currentTimeMillis()){
                 qTime += passedTime;
                 return true;
             }
             else {
                 return false;
             }
        }
        else if(mode == 2) {
             if(mTime + passedTime < System.currentTimeMillis()){
                 mTime += passedTime;
                 return true;
             }
             else {
                 return false;
             }
        }
       
        else {
        	  if(gTime + passedTime < System.currentTimeMillis()){
                  gTime += passedTime;
                  return true;
              }
              else {
                  return false;
              }
        }

    }
}