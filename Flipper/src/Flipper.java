import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.GrandExchange.Box;
import org.osbot.rs07.api.GrandExchange.Status;

@ScriptManifest(author = "Lexhanatin", name = "Flipper", info = "Just an empty script :(", version = 0.1, logo = "")
public final class Flipper extends Script  {
	
	//private static final String FILENAME = "C:\\Users\\Lexhanatin\\Desktop\\Items.txt";
	
    @Override
    public final int onLoop() throws InterruptedException {
    	if (canFlip()) {
    		flip();
    	}
    	else {
    		stop();
    	}
        return random(2000, 3000);
    }

  private void flip() throws InterruptedException {
    	if (!Banks.GRAND_EXCHANGE.contains(myPosition())) {
    		getWalking().webWalk(Banks.GRAND_EXCHANGE);
    	}
    	else if (!getGrandExchange().isOpen()) {
    		openExchange();
    	}
    	else if (getGrandExchange().isOpen()) {
    		flipItems();
    	}
    	else {
    		stop();
    	}
    }
  
	private boolean canFlip() {
    	return getInventory().getAmount("Coins") > 100000;
    }
	
	private boolean openExchange() {
		return getNpcs().closest("Grand Exchange Clerk").interact("Exchange");
	}
	
	private void flipItems() throws InterruptedException {
		String items;
		int ids;
		
		//String[] itemList = {"Raw swordfish", "Swordfish", "Raw lobster", "Lobster", "Iron bar", "Steel bar", "Mithril bar", "Adamantite bar", "Yew logs", "Oak logs"};
		//int[] idList = {371, 373, 377, 379, 2351, 2353, 2359, 2361, 1515, 1521};
		
		//String[] itemList = {"Rune dagger", "Rune battleaxe", "Rune scimitar", "Rune pickaxe", "Rune 2h sword", "Rune axe", "Death rune", "Chaos rune", "Nature rune", "Law rune"};
		//int[] idList = {1213, 1373, 1333, 1275, 1319, 1359, 560, 562, 561, 563};
		
		//String[] itemList = {"Green d'hide vamb", "Green d'hide chaps", "Green d'hide body", "Silk", "Gold amulet (u)", "Clay", "Raw tuna", "Chocolate dust", "Bucket of milk", "Raw beef"};
		//int[] idList = {1065, 1099, 1135, 950, 1673, 434, 359, 1975, 1927, 2132};
		
		String[] itemList = {"Chocolate bar", "Plank", "Cosmic rune", "Uncut emerald", "Uncut sapphire", "Uncut ruby", "Uncut diamond", "Wine of zamorak", "Hard leather", "Cowhide"};
		int[] idList = {1973, 960, 564, 1621, 1623, 1619, 1617, 245, 1743, 1739};
		
		for (int i = 0; i < itemList.length; i++) {
			items = itemList[i];
			ids = idList[i];
			
			if (getGrandExchange().getStatus(Box.BOX_1) == Status.EMPTY) {
				getGrandExchange().buyItem(ids, items, 50000, 1);
				sleep(random(2000,3000));
				
				if (getGrandExchange().getStatus(Box.BOX_1) == Status.FINISHED_BUY) {
					getGrandExchange().collect();
					sleep(random(2000,3000));
					
					if (getInventory().contains(items) && getGrandExchange().getStatus(Box.BOX_1) == Status.EMPTY) {
						getGrandExchange().sellItem(ids, 1, 1);
						sleep(random(2000,3000));
						
						if (getGrandExchange().getStatus(Box.BOX_1) == Status.FINISHED_SALE) {
							getGrandExchange().collect();
							sleep(random(2000,3000));
						}
					}
				}
			}
		}
	}
}
    
    /*
    private boolean buy() {
    	return createBuyOffer.interact("Create Buy offer");
    }
    */

    /*
    private void buy() {
    	Items items = new Items();
    	GrandExchange GrandExchange = new GrandExchange();
    	
    	while(items.itr.hasNext()) {
			GrandExchange.buyItem(371, items.element, 10000, 1);
			write();
    	}
    }
    */
    /*
    private void sell() {
    	Items items = new Items();
    	GrandExchange GrandExchange = new GrandExchange();
    	
    	while(items.itr.hasNext()) {
			GrandExchange.sellItem(371, 1, 1);
			write();
    	}
    }
    */
    
    /*
    
    private void write() {
    	BufferedWriter bw = null;
		FileWriter fw = null;
		Items items = new Items();
		
		try {
			GrandExchange GrandExchange = new GrandExchange();
			String content = items.element + GrandExchange.getOfferPrice();

			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
    
    */
    /*
    private boolean canFlip() {
    	return getInventory().getAmount("Coins") > 100000;
    }
    */
/*
final class Items {
	ArrayList<String> items = new ArrayList<String>();
	Iterator<String> itr = items.iterator();
	String element = itr.next();
	
	void addItems() {
		items.add("Raw lobster");
		items.add("Row swordfish");
	}
	
	public Iterator<String> itr() {
		return itr;
	}
	
	public String getElement() {
		return element;
	}
}
*/

/*
 Flip
 
 Be at grand exchange
 Have 100k
 Record buy and sell prices of items
 write to file
*/