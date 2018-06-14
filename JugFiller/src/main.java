import org.osbot.rs07.api.model.Character;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;

import java.util.Arrays;
import java.util.List;
import java.awt.*;

@ScriptManifest(author = "Lexhanatin", info = "My first script", name = "JugFiller", version = 0, logo = "")
public class main extends Script {
	 long start = System.currentTimeMillis();
	 long end = start + 3900*1000; // 60 seconds * 1000 ms/sec
	
	private Position[] path = {
		new Position(3253, 3426, 0), // Outside bank.
		new Position(3246, 3429, 0),
		new Position(3239, 3433, 0) // Fountain
	};
	
	private Position[] path2 = {
			new Position(3239, 3433, 0), // Fountain
			new Position(3246, 3429, 0),
			new Position(3253, 3426, 0) // Outside bank.
		};
	List toFill = Arrays.asList(path);
	List toBank = Arrays.asList(path2);

	@Override
	public void onStart() {
		log("Welcome to Jug Filler by Lexhanatin.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script!");
	}

	private enum State {
		FILL, BANK, WAIT
	};

	private State getState() {
		Entity fountain = objects.closest("Fountain");
		NPC banker = npcs.closest("Banker");
		
		if (inventory.getAmount("Jug") == 28 && fountain != null)
			return State.FILL;
		if ((banker != null) && inventory.getAmount("Jug of water") == 28 ||
			(banker != null) && inventory.isEmpty())
			return State.BANK;
		return State.WAIT;
	}

	@Override
	public int onLoop() throws InterruptedException {
		if(System.currentTimeMillis() < end){
			switch (getState()) {
			case FILL:
				getWalking().walkPath(toFill);
				sleep(500);
				
				Entity fountain = objects.closest("Fountain");
				if (inventory.getAmount("Jug") == 28 && fountain != null) {
					inventory.interact("Use", "Jug");
					sleep(random(80, 100));
					if (inventory.isItemSelected()) {
						fountain.interact("Use", "Jug -> Fountain");
						sleep(random(80, 100));
						
						Character me = myPlayer();
						if(myPlayer().isAnimating()) {
							sleep(5000);
						}
					}
				}
				sleep(random(5000, 10000));
				break;
			case BANK:
				if (!getBank().isOpen()){
				    getBank().open();
				} 
				if(inventory.isEmpty()){
					getBank().withdraw("Jug", 28);
					sleep(random(500, 800));
					getBank().close();
				 } else if (inventory.isFull()) {
				        getBank().depositAll();
				        sleep(random(500, 800));
				        getBank().withdraw("Jug", 28);
				        sleep(random(500, 800));
				        getBank().close();
				} else {
		        	getBank().close();
		        }
				break;
			case WAIT:
				sleep(random(500, 700));
				break;
			}
			return random(200, 300);
		}
		else {
			stop();
			return random(200, 300);
		}
	}

	@Override
	public void onExit() {
		log("Thanks for running my Jug Filler!");
	}

	@Override
	public void onPaint(Graphics2D g) {

	}
} 