import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.ui.RS2Widget;

import java.awt.*;

@ScriptManifest(author = "Nathaniel 'Lexhanatin' Pather", info = "My first script", name = "AutoClaySoftener", version = 0, logo = "")
public class main extends Script {
	long start = System.currentTimeMillis();
	long end = start + 3900*1000; // 60 seconds * 1000 ms/sec

	@Override
	public void onStart() {
		log("Welcome to AutoClaySoftener by Lexhanatin.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script!");
	}
	
	private enum State {
		BANK, SOFTEN, WAIT
	};
	
	private State getState() {
		// If inventory is empty, bank
		// OR
		// If inventory contains 14 jug AND 14 soft clay, bank
		if (inventory.isEmpty())
				return State.BANK;
		else if ((inventory.getAmount("Jug") == 14) && (inventory.getAmount("Soft clay") == 14))
			return State.BANK;
		
		// If inventory contains 14 Jug of water AND 14 Clay, soften
		if ((inventory.getAmount("Jug of water") == 14) && (inventory.getAmount("Clay") == 14))
			return State.SOFTEN;
		return State.WAIT;
	}

	@Override
	public int onLoop() throws InterruptedException {
		if(System.currentTimeMillis() < end){
			switch (getState()) {
			
			case SOFTEN:
				if ((inventory.getAmount("Jug of water") == 14) && (inventory.getAmount("Clay") == 14)) {
					inventory.interact("Use", "Jug of water");
					sleep(random(80, 100));
					if (inventory.isItemSelected()) {
						inventory.interact("Use", "Clay");
						sleep(random(80, 100));
					}
					sleep(random(80, 100));
	
					RS2Widget softClay = widgets.get(162, 40);
					if (softClay != null) {
						softClay.interact("Make All");
						sleep(random(10000, 15000));
					}
				}
				
				break;
				
			case BANK:
				if (!getBank().isOpen()){
				    getBank().open();
				    
				    // If Inventory is empty, withdraw 14 jugs of water AND 14 clay
				} if(inventory.isEmpty()){
					getBank().withdraw("Jug of water", 14);
					sleep(random(500, 800));
					getBank().withdraw("Clay", 14);
					sleep(random(500, 800));
					getBank().close();
					
					// If inventory contains 14 jugs of water AND 14 clay, deposit
				 } else if ((inventory.getAmount("Jug") == 14) && (inventory.getAmount("Soft clay") == 14)) {
				        getBank().depositAll();
				        sleep(random(500, 800));
				        getBank().withdraw("Jug of water", 14);
				        sleep(random(500, 800));
						getBank().withdraw("Clay", 14);
						sleep(random(500, 800));
				        getBank().close();
				        
		        } else if ((inventory.onlyContains("Jug")) || (inventory.onlyContains("Jug of water")) || (inventory.onlyContains("Clay")) || (inventory.onlyContains("Soft clay"))) {
			        getBank().depositAll();
			        sleep(random(500, 800));
			        getBank().withdraw("Jug of water", 14);
			        sleep(random(500, 800));
					getBank().withdraw("Clay", 14);
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
		log("Thanks for running my AutoClaySoftener!");
	}

	@Override
	public void onPaint(Graphics2D g) {
	}
}