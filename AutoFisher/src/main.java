import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.awt.*;

@ScriptManifest(author = "Nathaniel 'Lexhanatin' Pather", info = "My first script", name = "AutoFisher", version = 0, logo = "")
public class main extends Script {
	private Position[] path = {
		new Position(3277, 3166, 0), // Outside bank.
		new Position(3276, 3158, 0),
		new Position(3275, 3146, 0) // Fishing spot.
	};
	
	private Position[] path2 = {
			new Position(3275, 3146, 0), // Fishing spot.
			new Position(3276, 3158, 0),
			new Position(3277, 3166, 0) // Outside bank.
		};
	
	List toFish = Arrays.asList(path);
	List toBank = Arrays.asList(path2);

	@Override
	public void onStart() {
		log("Welcome to Auto Fisher by Lexhanatin.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script!");
	}

	private enum State {
		FISH, BANK, TOBANK, TOFISH, WAIT
	};

	private State getState() {
		NPC spot = npcs.closest("Fishing spot");
		NPC banker = npcs.closest("Banker");
		
		if (inventory.contains("Small fishing net") && !inventory.isFull() && spot != null)
			return State.FISH;
		if ((inventory.isFull() && banker == null) || !inventory.contains("Small fishing net"))
			return State.TOBANK;
		if ((inventory.isFull() && banker != null) || !inventory.contains("Small fishing net"))
			return State.BANK;
		if (inventory.contains("Small fishing net") && !inventory.isFull() && spot == null)
			return State.TOFISH;
		return State.WAIT;
	}

	@Override
	public int onLoop() throws InterruptedException {
		switch (getState()) {
		case FISH:
			NPC spot = npcs.closest("Fishing spot");
			if (spot != null && inventory.contains("Small fishing net")) {
				spot.interact("Net");
			}
			sleep(random(5000, 10000));
			break;
		case TOBANK:
			getWalking().walkPath(toBank);
			sleep(500);
			break;
		case BANK:
			if (!getBank().isOpen()){
			    getBank().open();
			} 
			if(inventory.isEmpty()){
				getBank().withdraw("Small fishing net", 1);
				sleep(random(500, 800));
				getBank().close();
			 } else if (inventory.isFull()) {
			        getBank().depositAllExcept("Small fishing net");
			        sleep(random(500, 800));
			        getBank().close();
			} else {
	        	getBank().close();
	        }
			break;
		case TOFISH:
			getWalking().walkPath(toFish);
			sleep(500);
			break;
		case WAIT:
			sleep(random(500, 700));
			break;
		}
		return random(200, 300);
	}

	@Override
	public void onExit() {
		log("Thanks for running my Tea Thiever!");
	}

	@Override
	public void onPaint(Graphics2D g) {

	}

} 