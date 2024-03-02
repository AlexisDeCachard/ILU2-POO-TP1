package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal=new Etal();
		etal.libererEtal();
		Gaulois acheteur= new Gaulois(null, 0);
		System.out.println("fin du test");
		System.out.println(etal.acheterProduit(10, acheteur));
		System.out.println("fin du test");
	}
}
