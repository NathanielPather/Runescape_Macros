import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.model.Character;
import org.osbot.rs07.api.model.NPC;

import java.awt.*;

@ScriptManifest(author = "Nathaniel 'Lexhanatin' Pather", info = "My first script", name = "CowKiller", version = 0, logo = "")
public class main extends Script {
	long start = System.currentTimeMillis();
	long end = start + 3900*1000; // 60 seconds * 1000 ms/sec

	@Override
	public void onStart() {
		log("Welcome to CowKiller by Lexhanatin.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script!");
	}
	
	private enum State {
		KILL, WAIT
	};
	
	private State getState() {
		NPC cow = npcs.closest("Cow");
		if (cow != null)
			return State.KILL;
		return State.WAIT;
		}
	
	@Override
	public int onLoop() throws InterruptedException {
		if(System.currentTimeMillis() < end){
			switch (getState()) {
			
			case WAIT:
				sleep(random(100, 250));
				break;
				
			case KILL:
				NPC cow = npcs.closest("Cow");
				Character me = myPlayer();
		        if(!myPlayer().isUnderAttack() && !cow.isUnderAttack() && cow.isAttackable()){
		            cow.interact("Attack");
		            sleep(random(1000, 1500));
		        }
			}
			return random(100, 200);
		}
		else {
			stop();
			return random(200, 300);
		}
	}

	@Override
	public void onExit() {
		log("Thanks for running my CowKiller");
	}

	@Override
	public void onPaint(Graphics2D g) {
	}
}