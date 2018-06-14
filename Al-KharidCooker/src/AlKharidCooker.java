import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.utility.ConditionalSleep;
import org.osbot.rs07.script.API;

@ScriptManifest(author = "Lexhanatin", name = "AlKharidCooker", info = "Just an empty script :(", version = 0.1, logo = "")
public final class AlKharidCooker extends Script  {
    
	Area cookingArea = new Area(3271, 3183, 3275, 3179);
	
	String[] myList = { "Raw trout" };
	
	@Override
    public final int onLoop() throws InterruptedException {
    	// Makes sense
    	if (canCook()) {
    		cook();
    	}
    	// Makes sense
    	else {
    		bank();
    	}
        return random(150, 200);
    }
    
    private void cook() {
    	// Makes sense
    	if (!cookingArea.contains(myPosition())) {
    		getWalking().webWalk(cookingArea);
    	}
    	// Not sure if makes sense
    	else if (!isFoodSelected() && !isWidgetAvailable()) {
    		selectFood();
    		new ConditionalSleep(5000) {
    			@Override
    			public boolean condition() throws InterruptedException {
    				return isFoodSelected();
    			}
    		}.sleep();
    	}
    	// Not sure if makes sense
    	else if (isFoodSelected()) {
    		useRange();
    		new ConditionalSleep(5000) {
    			@Override
    			public boolean condition() throws InterruptedException {
    				return isWidgetAvailable();
    			}
    		}.sleep();
    	}
    	// Not sure if makes sense
    	else if (isWidgetAvailable()) {
    		cookAll();
    		new ConditionalSleep(70000) {
    			@Override
    			public boolean condition() throws InterruptedException {
    				return !canCook();
    			}
    		}.sleep();
    	}
    }
    // Makes sense
    private boolean isFoodSelected(){
    	return getInventory().isItemSelected();
    }
    // Makes sense
    private boolean selectFood() {
    	return inventory.interactWithNameThatContains("Use", "Raw");
    }
    // Makes sense
    private boolean useRange() {
    	return getObjects().closest("Range").interact("Use");
    }
    // Makes sense
    private boolean isWidgetAvailable() {
    	return getWidgets().getWidgetContainingText("How many would you like to cook?") != null;
    }
    // Makes sense
    private boolean cookAll() {
    	return getWidgets().interact(307, 2, "Cook All");
    }
    // Makes sense
    private boolean canCook() {
    	return getInventory().contains(item -> item.nameContains(myList));
    }
    
    private void bank() throws InterruptedException {
    	if (!Banks.AL_KHARID.contains(myPosition())) {
    		getWalking().webWalk(Banks.AL_KHARID);
    	}
    	else if (!getBank().isOpen()) {
    		getBank().open();
    	}
    	else if (!getInventory().onlyContains(item -> item.nameContains(myList))) {
    		getBank().depositAll();
    	}
    	// 
    	else if (getBank().contains(item -> item.nameContains(myList)) && getInventory().isEmpty()) {
    		getBank().withdrawAll(item -> item.nameContains(myList));
    	}
    }
}