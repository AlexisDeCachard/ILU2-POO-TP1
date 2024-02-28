package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche=new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur,String Produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		String nomVendeur=vendeur.getNom();
		chaine.append(nomVendeur + " cherche un endroit pour vendre " + nbProduit+ " "+Produit+".\n");
		int indiceEtalLibre=marche.trouverEtalLibre();
		if (indiceEtalLibre==-1) {
			chaine.append("Il n'y a plus d'�tals disponibles dans le march�.\n");
			return chaine.toString();
		}
		marche.utiliserEtal(indiceEtalLibre, vendeur, Produit, nbProduit);
		indiceEtalLibre++;
		chaine.append("Le vendeur " + nomVendeur + " vend des " + Produit + " � l'�tal n�" + indiceEtalLibre + ".\n" );
		return chaine.toString();
	}
	
	private static class Marche{
		private Etal[] etals;

		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i]=new Etal();
			}
		}
		
		private void utiliserEtal(int indicEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indicEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
		return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalsVendantProduit=0;
			for (int i = 0; i < etals.length; i++) {
				Etal etalActuel=etals[i];
				if (etalActuel.contientProduit(produit)) {
					nbEtalsVendantProduit ++;
				}
			}
			Etal[] etalsVendantProduit = new Etal[nbEtalsVendantProduit];
			int indiceEtal = 0;
			for (int j = 0; j < etals.length; j++) {
				Etal etalActuel=etals[j];
					if (etalActuel.contientProduit(produit)) {
						etalsVendantProduit[indiceEtal]=etalActuel;
						indiceEtal ++;
				}
			}
			return etalsVendantProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				Etal etalActuel = etals[i];
				Gaulois vendeur=etalActuel.getVendeur();
				String nomGaulois=gaulois.getNom();
				String nomVendeur=vendeur.getNom();
				if (nomGaulois.equals(nomVendeur)) {
					return etalActuel;
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalsVides=0;
			StringBuilder chaine = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				Etal etalActuel = etals[i];
				if (etalActuel.isEtalOccupe()) {
					chaine.append(etalActuel.afficherEtal()+"\n");
				}
				else {
					nbEtalsVides++;
				}
			}
			if (nbEtalsVides!=0) {
				chaine.append("Il y a "+nbEtalsVides+" �tals non utilis�s dans le march�.\n");
			}
			return chaine.toString();
		}
	}
	
}