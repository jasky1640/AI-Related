import java.util.ArrayList;

/**
 * EPMoveLeft is a class that represent a left move in the given 8-puzzle state
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPMoveLeft implements Move{
    //The identifier of the blank tile left movement direction in the 8-puzzle game
    private static final String EP_Move_Left = "left";

    @Override
    public State move(State state){
        if(!(state instanceof EPState)){
            System.out.println("passed invalid state");
            System.exit(1);
        }

        if(isLegalMovement(state)) {
            ArrayList<Integer> stateList = ((EPState)state).getStateList();
            //swap the blank tile and the tile left to it
            int initialPosition = stateList.indexOf(0);
            int temp = stateList.set(initialPosition - 1, 0);
            stateList.set(initialPosition, temp);

            return new EPState(stateList);
        }
        else{
            return state;
        }
    }

    @Override
    public String getMoveName(){
        return EP_Move_Left;
    }

    //Validate if the blank tile is in the first column of the board
    @Override
    public boolean isLegalMovement(State state) {
        return ((EPState)state).getStateList().indexOf(0) % 3 != 0;
    }
}
