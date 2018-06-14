import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.ui.Skill;

import java.util.Random;
import java.awt.*;

@ScriptManifest(author = "Lexhanatin", info = "Lexhanatin", name = "DropParty", version = 0, logo = "")
public class main extends Script {
	
	long start = System.currentTimeMillis();
	long end = start + 120000; // 2 minutes
	
	Area partyArea = new Area(3163, 3486, 3165, 3485);
	
	Random r = new Random();
	int Low = 10;
	int High = 120000;

	@Override
	public void onStart() {
		log("Welcome to Drop Party by Lexhanatin.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, winning some drop parties!.");
	}

	private enum State {
		PARTY
	};

	private State getState() {
		return State.PARTY;
	}

	@Override
	public int onLoop() throws InterruptedException {
		switch (getState()) {
		case PARTY:
			GroundItem item = getGroundItems().closestThatContains("Rune", "mask", "partyhat", "Adamant", "Diamond", "Ruby", "Emerald", "Sapphire", "uncut", "Coins", "Saradomin", "Guthix", "Zamorok", "Ring of coins", "Ring of nature", "Gilded", "Black", "Hill", "gold", "trimmed", "dragonhide", "Mithril", "(g)", "(t)", "Armadyl", "Ancient", "Bandos" );
			if (item != null && item.isOnScreen()) {
				item.interact("Take");
			}
			else if(System.currentTimeMillis() > end){
	    		keyboard.typeString(" ");
	    		sleep(250);
	    		int Result = r.nextInt(High-Low) + Low;
	    		sleep(250);
	    		end = end + 120000 + Result;
	    		sleep(250);
	    		if (!partyArea.contains(myPosition())) {
		    		getWalking().webWalk(partyArea);
		    	}
	    	}
			else if(inventory.isFull()){
				if (!Banks.GRAND_EXCHANGE.contains(myPosition())) {
		    		getWalking().webWalk(Banks.GRAND_EXCHANGE);
		    	}
		    	else if (!getBank().isOpen()) {
		    		getBank().open();
		    	}
		    	else if (getBank().isOpen()) {
		    		getBank().depositAll();
		    		sleep(250);
		    		getBank().close();
		    	}
	    	}
			break;
		}
		return (1);
	}



	@Override
	public void onPaint(Graphics2D g) {
	}

} 