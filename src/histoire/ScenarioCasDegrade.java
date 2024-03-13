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
		Gaulois vendeur=new Gaulois("jean", 0);
		Gaulois acheteur= new Gaulois("pierrot", 0);
		etal.occuperEtal(vendeur, "bière", 10);
		System.out.println("fin du test");
		try {
			System.out.println(etal.acheterProduit(-20, acheteur));		
		} catch (IllegalArgumentException e) {
			System.err.println("Quantité invalide ");
			e.printStackTrace();
		}
		System.out.println("fin du test");
	}
}
