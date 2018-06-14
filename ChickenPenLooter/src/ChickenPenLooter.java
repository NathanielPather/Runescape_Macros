import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.utility.ConditionalSleep;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;

@ScriptManifest(author = "Lexhanatin", name = "ChickenPenLooter", info = "Just an empty script :(", version = 0.1, logo = "")
public final class ChickenPenLooter extends Script  {
	
	Area boneArea = new Area(
		    new int[][]{
		        { 3173, 3307 },
		        { 3173, 3304 },
		        { 3169, 3299 },
		        { 3170, 3298 },
		        { 3170, 3296 },
		        { 3169, 3294 },
		        { 3169, 3291 },
		        { 3171, 3289 },
		        { 3173, 3289 },
		        { 3175, 3288 },
		        { 3176, 3288 },
		        { 3177, 3289 },
		        { 3183, 3289 },
		        { 3185, 3291 },
		        { 3185, 3294 },
		        { 3186, 3296 },
		        { 3186, 3297 },
		        { 3185, 3298 },
		        { 3185, 3300 },
		        { 3183, 3302 },
		        { 3182, 3302 },
		        { 3181, 3303 },
		        { 3180, 3303 },
		        { 3179, 3304 },
		        { 3179, 3307 }
		    }
		);
	
    @Override
    public final int onLoop() throws InterruptedException {
    	if (canPick()) {
    		pick();
    	}
    	else {
    		bank();
    	}
        return random(150, 200);
    }
    
    private void pick() throws InterruptedException {
    	GroundItem items = getGroundItems().closest("Bones", "Raw chicken");
    	if (items != null && items.isOnScreen()) {
    		items.interact("Take");
    		sleep(random(1000,2500));
    	}
    	if (items != null && items.isOnScreen()) {
    		items.interact("Take");
    		sleep(random(1000,2500));
    	}
    	if (!worldOne()) {
    		switchToWorldOne();
    		sleep(random(60000,70000));
    	}
    	else if (!boneArea.contains(myPosition())) {
    		getWalking().webWalk(boneArea);
    	}
    	else if (isPicking()) {
    		new ConditionalSleep(5000) {
    			@Override
    			public boolean condition() throws InterruptedException {
    				return !isPicking();
    			}
    		}.sleep();
    	}
    	
    }
    
    private boolean canPick() {
    	return !getInventory().isFull();
    }
    
    private boolean worldOne() {
    	return getWorlds().getCurrentWorld() == 301;
    }
    
    private boolean switchToWorldOne() {
    	return getWorlds().hop(1);
    }
    
    private boolean isPicking() {
    	return myPlayer().isMoving();
    }
    
    private void bank() throws InterruptedException {
    	if (!Banks.LUMBRIDGE_UPPER.contains(myPosition())) {
    		getWalking().webWalk(Banks.LUMBRIDGE_UPPER);
    	}
    	else if (!getBank().isOpen()) {
    		getBank().open();
    	}
    	else if (!getInventory().isEmpty()) {
    		getBank().depositAll();
    	}
    	else {
    		getBank().close();
    	}
    }
}