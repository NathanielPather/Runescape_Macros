import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.model.Character;
import java.awt.*;

@ScriptManifest(author = "Nathaniel 'Lexhanatin' Pather", info = "My first script", name = "Healer", version = 0, logo = "")
public class main extends Script {

	@Override
	public void onStart() {
		log("Welcome to Healer by Lexhanatin.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script!");
	}
	
	private enum State {
		HEAL, IDLE, WAIT
	};
	
	private State getState() {
		Character me = myPlayer();
		if (me.getHealthPercent() <= 70)
			return State.HEAL;
		if (!inventory.contains("Trout"))
			return State.IDLE;
		return State.WAIT;
	}

	@Override
	public int onLoop() throws InterruptedException {
		switch (getState()) {
			case HEAL:
				Character me = myPlayer();
				if (me.getHealthPercent() <= 70)
					inventory.interact("Eat", "Trout");
				break;
			case IDLE:
				sleep(20000);
			case WAIT:
				sleep(random(100, 250));
				break;
		}
		return(random(100,200));
	}

	@Override
	public void onExit() {
		log("Thanks for running my Healer");
	}

	@Override
	public void onPaint(Graphics2D g) {
	}
}