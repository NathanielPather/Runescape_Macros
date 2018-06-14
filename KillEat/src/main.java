import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.model.Character;
import org.osbot.rs07.api.model.NPC;
import java.awt.*;

@ScriptManifest(author = "Nathaniel 'Lexhanatin' Pather", info = "My first script", name = "AutoKillEat", version = 0, logo = "")
public class main extends Script {

	@Override
	public void onStart() {
		log("Welcome to AutoKillEat by Lexhanatin.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script!");
	}
	
	private enum State {
		KILL, WAIT
		//BANK, EAT,
	};
	
	private State getState() {
		// If inventory does not contain shrimp, bank
		/*
		if (!inventory.contains("Jug of wine"))
			return State.BANK;
		*/
		
		// If health is less than or equal to 50%, eat
		/*
		Character me = myPlayer();
		if (me.getHealthPercent() <= 50)
			return State.EAT;
		*/
		
		// If monster "Dwarf" exists && inventory contains Jug of wine, kill
		/*
		NPC man = npcs.closest("Dwarf");
		if ((man != null) && (inventory.contains("Jug of wine")))
			return State.KILL;
		return State.WAIT;
		*/
		
		NPC cow = npcs.closest("Cow");
		if (cow != null)
			return State.KILL;
		return State.WAIT;
	}

	@Override
	public int onLoop() throws InterruptedException {
		switch (getState()) {
		
		case WAIT:
			sleep(random(100, 250));
			break;
		
		/*
		case EAT:
			if (inventory.contains("Jug of wine")) {
				inventory.interact("Drink", "Jug of wine");
			}
			break;
		*/
			
		/*
		case BANK:
			// If Inventory does not contain Jug of wine, withdraw Jug of wine.
			if (!getBank().isOpen()){
			    getBank().open();
				sleep(random(1000, 2000));
				getBank().withdraw("Jug of wine", 28);
				sleep(random(1000, 2000));
				getBank().close();
				sleep(random(1000, 2000));
			}
			else {
	        	getBank().close();
	        }
			break;
		*/
			
		case KILL:
			NPC cow = npcs.closest("Cow");
			Character me = myPlayer();
	        if(!myPlayer().isUnderAttack()){
	            cow.interact("Attack");
	        }
		}
		return random(100, 200);
	}

	@Override
	public void onExit() {
		log("Thanks for running my AutoKillEat");
	}

	@Override
	public void onPaint(Graphics2D g) {
	}
}