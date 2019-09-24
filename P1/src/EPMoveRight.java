import java.util.ArrayList;

/**
 * EPMoveRight is a class that represent a right move in the given 8-puzzle state
 * Author: Jiaqi Yang
 * Date: 9/23/2019
 */
public class EPMoveRight implements Move{
    //The identifier of the blank tile left movement direction in the 8-puzzle game
    private static final String EP_Move_Right = "right";

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
            int temp = stateList.set(initialPosition + 1, 0);
            stateList.set(initialPosition, temp);

            return new EPState(stateList);
        }
        else{
            return state;
        }
    }

    @Override
    public String getMoveName(){
        return EP_Move_Right;
    }

    //Validate if the blank tile is in the last column of the board
    @Override
    public boolean isLegalMovement(State state) {
        return ((EPState)state).getStateList().indexOf(0) % 3 != 2;
    }

    //Shortcut to rapidly test the normal case of the methods
    public static void main(String[] args) {
        EPState state_error = new EPState("123450678");
        EPState state_column_1 = new EPState("123456708");
        EPState state_column_2 = new EPState();

        //Test isLegalMovement method
        EPMoveRight epMoveRight = new EPMoveRight();
        state_error.printState();
        System.out.println(epMoveRight.isLegalMovement(state_error));
        state_column_1.printState();
        System.out.println(epMoveRight.isLegalMovement(state_column_1));
        state_column_2.printState();
        System.out.println(epMoveRight.isLegalMovement(state_column_2));

        //Test move method
        (epMoveRight.move(state_column_1)).printState();
        (epMoveRight.move(state_column_2)).printState();
    }
}
