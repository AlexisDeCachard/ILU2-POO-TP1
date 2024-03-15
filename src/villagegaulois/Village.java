package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Village.VillageSansChefException;

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
	
	public void usageVillageSansChefException() throws VillageSansChefException {
		if (chef== null) {
			throw new VillageSansChefException("Il n'y a pas de chef dans ce village !");
		}
	}

	public String afficherVillageois() throws VillageSansChefException {
		usageVillageSansChefException();
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
	
	public String installerVendeur(Gaulois vendeur,String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		String nomVendeur=vendeur.getNom();
		chaine.append(nomVendeur + " cherche un endroit pour vendre " + nbProduit+ " "+produit+".\n");
		int indiceEtalLibre=marche.trouverEtalLibre();
		if (indiceEtalLibre==-1) {
			chaine.append("Il n'y a plus d'�tals disponibles dans le march�.\n");
			return chaine.toString();
		}
		marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
		indiceEtalLibre++;
		chaine.append("Le vendeur " + nomVendeur + " vend des " + produit + " � l'�tal n�" + indiceEtalLibre + ".\n" );
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalVendantProduit=marche.trouverEtals(produit);
		StringBuilder chaine=new StringBuilder();
		if (etalVendantProduit.length==0) {
			chaine.append("Il n'y a pas de vendeurs proposant des " + produit+ " au march�.\n");
			return chaine.toString();
		}
		else if(etalVendantProduit.length==1){
			Gaulois vendeur=etalVendantProduit[0].getVendeur();
			chaine.append("Seul le vendeur " + vendeur.getNom() + " propose des "+produit+ " au march�.\n");
			return chaine.toString();
		}
		chaine.append("Les vendeurs qui proposent des "+ produit + " sont :\n");
		for (Etal etal : etalVendantProduit) {
			Gaulois vendeur=etal.getVendeur();
			chaine.append("- "+ vendeur.getNom()+"\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalLibere= marche.trouverVendeur(vendeur);
		return etalLibere.libererEtal();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
	public static class VillageSansChefException extends Exception {

		public VillageSansChefException() {
		    super();
		  }

		  public VillageSansChefException(String s) {
		    super(s);
		  }
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
				if (etalActuel.isEtalOccupe()&&etalActuel.contientProduit(produit)) {
					nbEtalsVendantProduit ++;
				}
			}
			Etal[] etalsVendantProduit = new Etal[nbEtalsVendantProduit];
			int indiceEtal = 0;
			for (int j = 0; j < etals.length; j++) {
				Etal etalActuel=etals[j];
					if (etalActuel.isEtalOccupe()&&etalActuel.contientProduit(produit)) {
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
				chaine.append("Il y a "+nbEtalsVides+" étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}
	
}