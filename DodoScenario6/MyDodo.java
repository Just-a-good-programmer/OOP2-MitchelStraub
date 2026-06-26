import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.1 -- 29-07-2017
 */
public class MyDodo extends Dodo
{

    public MyDodo() {
        super( EAST );
    }

    public void act() {
        pickUpNearestEggInList();
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, 
     * i.e. there are no obstructions or end of world in the cell in front of her.
     * 
     * <p> Initial:   Dodo is somewhere in the world
     * <p> Final:     Same as initial situation
     * 
     * @return  boolean true if Dodo can move (thus, no obstructions ahead)
     *                  false if Dodo can't move
     *                      there is an obstruction or end of world ahead
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }
    
    public void moveRandomly() {
        int myNrOfStepsTaken = 0;
        while (myNrOfStepsTaken < Mauritius.MAXSTEPS){
            setDirection(randomDirection());
            if(!borderAhead() & !fenceAhead()){
                move();
                myNrOfStepsTaken++;
            } 
            
            
        }
    }
    
    public void pickUpNearestEggInList() {
        
        int myNrOfStepsTaken = 0;
        int stepsLeft = ((Mauritius) getWorld()).getSteps();
        int score = ((Mauritius) getWorld()).getScore();
        
        while(myNrOfStepsTaken < Mauritius.MAXSTEPS){ 
            Egg closest = findClosestEgg();
            if(closest != null){
            int direction= 1;
            int dx = closest.getX() - getX();
            int dy = closest.getY() - getY();
            int stepsX = Math.abs(dx);
            int stepsY = Math.abs(dy);
            
            if (dx > 0) {
    		direction = EAST;
    		setDirection(direction);
            } if (dx < 0) {
    		direction = WEST;
    		setDirection(direction);
            }
            while (stepsX > 0) {
                move();
                stepsX--;
                myNrOfStepsTaken++;
                stepsLeft = stepsLeft - 1;
                ((Mauritius) getWorld()).updateScore(stepsLeft, score);
            }
            if (dy > 0) {
    		direction = SOUTH;
    		setDirection(direction);
            } if (dy < 0) {
    		direction = NORTH;
    		setDirection(direction);
            }
            while (stepsY > 0) {
                move();    
                stepsY--;
                myNrOfStepsTaken++;
                stepsLeft = stepsLeft - 1;
                ((Mauritius) getWorld()).updateScore(stepsLeft, score);
            }
            if ( onEgg()) {
                pickUpEgg();
                if(closest instanceof BlueEgg){
                    score = score + 1;
                }if (closest instanceof GoldenEgg){
                    score = score + 5;
                } if (closest instanceof SurpriseEgg) {
                    score = score + 1; 
                    
                }
            } else {
            break;
            }
            ((Mauritius) getWorld()).updateScore(stepsLeft, score);
        }
        
        } 
    }
    public Egg findClosestEgg() {
        List<Egg> eggs = getListOfEggsInWorld();
        int myX = getX();
        int myY = getY();
        int steps = Mauritius.MAXSTEPS;
        Egg nearestEgg = null;
        for (Egg egg : eggs){
            int dx = egg.getX() - myX;
            int dy = egg.getY() - myY;
            int distance = Math.abs(dx) + Math.abs(dy);
            if (distance < steps){
		steps = distance;
		nearestEgg = egg;
            } 
            
        }
        return nearestEgg;
    }
    /**
     * Places all the Egg objects in the world in a list.
     * 
     * @return List of Egg objects in the world
     */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }
    
    public List<Integer> createListOfNumbers() {
        return new ArrayList<> (Arrays.asList( 2, 43, 7, -5, 12, 7 ));
    }

    /**
     * Method for praciticing with lists.
     */
    public void practiceWithLists( ){
        List<Integer> listOfNumbers = createListOfNumbers();
        
        //the following is incorrect and is to be fixed in challenge 6.1c
        System.out.println("First element: " + listOfNumbers.get(1) ); 
    }

    public void practiceWithListsOfSurpriseEggs( ){
        List<SurpriseEgg>  listOfEgss = SurpriseEgg.generateListOfSurpriseEggs( 12, getWorld() );
    }
}
