class Portefeuille:
    def __init__(self):
        self.solde = 0  
        self.transactions = []
        self.historique_transactions = []

    def ajouter_fonds(self, montant):
        self.solde += montant
        self.transactions.append(f"+{montant} €")
        self.historique_transactions.append(f"Ajout de fonds: +{montant} €")

    def retirer_fonds(self, montant):
        if self.solde >= montant:
            self.solde -= montant
            self.transactions.append(f"-{montant} €")
            self.historique_transactions.append(f"Retrait de fonds: -{montant} €")
        else:
            print("Solde insuffisant pour ce retrait.")

    def afficher_solde(self):
        print(f"Solde actuel: {self.solde} €")

    def afficher_historique_transactions(self):
        print("Historique des transactions:")
        for transaction in self.historique_transactions:
            print(transaction)

    def transferer_fonds(self, montant, destinataire):
        if self.solde >= montant:
            self.solde -= montant
            self.transactions.append(f"-{montant} €")
            self.historique_transactions.append(f"Transfert de fonds: -{montant} € vers {destinataire}")
        else:
            print("Solde insuffisant pour ce transfert.")


def interface_utilisateur(portefeuille):
    while True:
        print("\n1. Ajouter des fonds")
        print("2. Retirer des fonds")
        print("3. Afficher le solde")
        print("4. Afficher l'historique des transactions")
        print("5. Transférer des fonds")
        print("6. Quitter")

        choix = input("Entrez le numéro de votre choix: ")

        if choix == "1":
            montant = float(input("Entrez le montant à ajouter: "))
            portefeuille.ajouter_fonds(montant)
        elif choix == "2":
            montant = float(input("Entrez le montant à retirer: "))
            portefeuille.retirer_fonds(montant)
        elif choix == "3":
            portefeuille.afficher_solde()
        elif choix == "4":
            portefeuille.afficher_historique_transactions()
        elif choix == "5":
            montant = float(input("Entrez le montant à transférer: "))
            destinataire = input("Entrez le destinataire du transfert: ")
            portefeuille.transferer_fonds(montant, destinataire)
        elif choix == "6":
            break
        else:
            print("Choix invalide. Veuillez entrer un numéro valide.")


portefeuille = Portefeuille()
interface_utilisateur(portefeuille)
