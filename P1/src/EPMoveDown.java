import java.util.ArrayList;

/**
 * EPMoveDown is a class that represent a down move in the given 8-puzzle state
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPMoveDown implements Move{
    //The identifier of the blank tile left movement direction in the 8-puzzle game
    private static final String EP_Move_Down = "down";

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
            int temp = stateList.set(initialPosition + 3, 0);
            stateList.set(initialPosition, temp);

            return new EPState(stateList);
        }
        else{
            return state;
        }
    }

    @Override
    public String getMoveName(){
        return EP_Move_Down;
    }

    //Validate if the blank tile is in the last column of the board
    @Override
    public boolean isLegalMovement(State state) {
        return ((EPState)state).getStateList().indexOf(0) / 3 != 2;
    }

    //Shortcut to rapidly test the normal case of the methods
    public static void main(String[] args) {
        EPState state_error = new EPState("456123780");
        EPState state_column_1 = new EPState("123405678");
        EPState state_column_2 = new EPState();

        //Test isLegalMovement method
        EPMoveDown epMoveDown = new EPMoveDown();
        state_error.printState();
        System.out.println(epMoveDown.isLegalMovement(state_error));
        state_column_1.printState();
        System.out.println(epMoveDown.isLegalMovement(state_column_1));
        state_column_2.printState();
        System.out.println(epMoveDown.isLegalMovement(state_column_2));

        //Test move method
        (epMoveDown.move(state_column_1)).printState();
        (epMoveDown.move(state_column_2)).printState();
    }
}
