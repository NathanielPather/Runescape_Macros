import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.filter.AreaFilter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(author = "Lexhanatin", name = "AlKharidMiner", info = "Just an empty script :(", version = 0.1, logo = "")
public final class AlKharidMiner extends Script  {
	
	long start = System.currentTimeMillis();
	long end = start + 3900*1000; // 60 seconds * 1000 ms/sec
	
	Area miningArea = new Area(
		    new int[][]{
		        { 3296, 3312 },
		        { 3295, 3312 },
		        { 3294, 3311 },
		        { 3293, 3310 },
		        { 3294, 3309 },
		        { 3295, 3308 },
		        { 3296, 3308 }
		    }
		);
	
    @Override
    public final int onLoop() throws InterruptedException {
    	
    	if (canMine()) {
    		if (!isMining()) {
    			mine();
    		}
    	}
    	
    	else if (inventory.isFull()) {
    		dropAll();
    	}
    	
    	else if(System.currentTimeMillis() > end){
    		stop();
    	}
    	
        return random(150, 200);
    }
    
    private void mine() {
    	
    	if (!miningArea.contains(myPosition())) {
    		getWalking().webWalk(miningArea);
    	}
    	
    	// Not used
    	/*
    	else if (isAreaOccupied()) {
    		worldSwitch();
    	}
    	*/
    	
    	else if (mineRock()) {
    		new ConditionalSleep(800, 1500) {
    			@Override
    			public boolean condition() throws InterruptedException {
    				return isMining();
    			}
    		}.sleep();
    	}
    	else if (isMining()) {
    		new ConditionalSleep(800, 1500) {
    			@Override
    			public boolean condition() throws InterruptedException {
    				return !isMining();
    			}
    		}.sleep();
    	}
    }
    
    private boolean canMine() {
    	
    	return getInventory().contains("Rune pickaxe") || equipment.isWieldingWeapon("Rune pickaxe") && !inventory.isFull();
    }
    
 // Not used
    private boolean isAreaOccupied() {
    	return getPlayers().filter(new AreaFilter<>(miningArea)).size() >= 2;
    }
    
    // Not used
    public boolean worldSwitch() {
    	return worlds.hopToF2PWorld();
    }
    
    private boolean isMining() {
    	return myPlayer().isAnimating();
    }
    
    private boolean mineRock() {
    	//ID 7488, 7455 (2)
    	return getObjects().closest(miningArea,7488, 7455).interact("Mine");
    }
    
    private void dropAll() throws InterruptedException {
    	if (inventory.isFull()) {
    		inventory.dropAllExcept("Rune pickaxe");
    	}
    }
    
    // Not used
    private void bank() throws InterruptedException {
    	
    	if (!Banks.DUEL_ARENA.contains(myPosition())) {
    		getWalking().webWalk(Banks.DUEL_ARENA);
    	}
    	
    	else if (!getBank().isOpen()) {
    		getBank().open();
    	}
    	
    	else if (!getInventory().isEmptyExcept("Rune pickaxe") || equipment.isWieldingWeapon("Rune pickaxe")) {
    		getBank().depositAll();
    	}
    	
    	else if (getBank().contains("Rune pickaxe")) {
    		getBank().withdrawAll("Rune pickaxe");
    		sleep(random(200, 400));
    	}
    }
}