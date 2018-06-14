import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(author = "Lexhanatin", name = "BarbarianVillageFisher", info = "Just an empty script :(", version = 0.1, logo = "")
public final class BarbarianVillageFisher extends Script  {
	
	Area fishingArea = new Area(
		    new int[][]{
		        { 3103, 3422 },
		        { 3104, 3422 },
		        { 3112, 3433 },
		        { 3111, 3436 },
		        { 3100, 3436 },
		        { 3100, 3422 }
		    }
		);
	
    @Override
    public final int onLoop() throws InterruptedException {
    	if (canFish()) {
    		fish();
    	}
    	else {
    		bank();
    	}
        return random(150, 200);
    }
    
    private void fish() {
    	if (!fishingArea.contains(myPosition())) {
    		getWalking().webWalk(fishingArea);
    	}
    	else if (isFishing()) {
    		new ConditionalSleep(5000) {
    			@Override
    			public boolean condition() throws InterruptedException {
    				return !isFishing();
    			}
    		}.sleep();
    	}
    	else if (useFishingSpot()) {
    		new ConditionalSleep(5000) {
    			@Override
    			public boolean condition() throws InterruptedException {
    				return isFishing();
    			}
    		}.sleep();
    	}
    }
    
    private boolean canFish() {
    	return getInventory().contains("Fly fishing rod", "Feather") && !inventory.isFull();
    }
    
    private boolean isFishing() {
    	return myPlayer().isAnimating();
    }
    
    private boolean useFishingSpot() {
    	return getNpcs().closest("Rod Fishing spot").interact("Lure");
    }
    
    private void drop() throws InterruptedException {
    	if (inventory.isFull()) {
    		inventory.dropAllExcept("Fly fishing rod", "Feather");
    	}
    }
    
    private void bank() throws InterruptedException {
    	if (!Banks.EDGEVILLE.contains(myPosition())) {
    		getWalking().webWalk(Banks.EDGEVILLE);
    	}
    	else if (!getBank().isOpen()) {
    		getBank().open();
    	}
    	else if (!getInventory().isEmptyExcept("Fly fishing rod", "Feather")) {
    		getBank().depositAllExcept("Fly fishing rod", "Feather");
    	}
    	else if (getBank().contains("Fly fishing rod", "Feather")) {
    		getBank().withdrawAll("Fly fishing rod");
    		sleep(random(200, 400));
    		getBank().withdrawAll("Feather");
    	}
    }
}