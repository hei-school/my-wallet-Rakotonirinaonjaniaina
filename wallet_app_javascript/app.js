const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

class Portefeuille {
  constructor() {
    this.solde = 0;
    this.transactions = [];
    this.historiqueTransactions = [];
  }

  ajouterFonds(montant) {
    this.solde += montant;
    this.transactions.push(`+${montant} €`);
    this.historiqueTransactions.push(`Ajout de fonds: +${montant} €`);
  }

  retirerFonds(montant) {
    if (this.solde >= montant) {
      this.solde -= montant;
      this.transactions.push(`-${montant} €`);
      this.historiqueTransactions.push(`Retrait de fonds: -${montant} €`);
    } else {
      console.log("Solde insuffisant pour ce retrait.");
    }
  }

  afficherSolde() {
    console.log(`Solde actuel: ${this.solde} €`);
  }

  afficherHistoriqueTransactions() {
    console.log("Historique des transactions:");
    this.historiqueTransactions.forEach(transaction => {
      console.log(transaction);
    });
  }

  transfererFonds(montant, destinataire) {
    if (this.solde >= montant) {
      this.solde -= montant;
      this.transactions.push(`-${montant} €`);
      this.historiqueTransactions.push(`Transfert de fonds: -${montant} € vers ${destinataire}`);
    } else {
      console.log("Solde insuffisant pour ce transfert.");
    }
  }
}

function interfaceUtilisateur(portefeuille) {
  const menu = () => {
    console.log("\n1. Ajouter des fonds");
    console.log("2. Retirer des fonds");
    console.log("3. Afficher le solde");
    console.log("4. Afficher l'historique des transactions");
    console.log("5. Transférer des fonds");
    console.log("6. Quitter");
    rl.question("Entrez le numéro de votre choix: ", (choix) => {
      switch (choix) {
        case "1":
          rl.question("Entrez le montant à ajouter: ", (montantAjout) => {
            portefeuille.ajouterFonds(parseFloat(montantAjout));
            menu();
          });
          break;
        case "2":
          rl.question("Entrez le montant à retirer: ", (montantRetrait) => {
            portefeuille.retirerFonds(parseFloat(montantRetrait));
            menu();
          });
          break;
        case "3":
          portefeuille.afficherSolde();
          menu();
          break;
        case "4":
          portefeuille.afficherHistoriqueTransactions();
          menu();
          break;
        case "5":
          rl.question("Entrez le montant à transférer: ", (montantTransfert) => {
            rl.question("Entrez le destinataire du transfert: ", (destinataire) => {
              portefeuille.transfererFonds(parseFloat(montantTransfert), destinataire);
              menu();
            });
          });
          break;
        case "6":
          rl.close();
          break;
        default:
          console.log("Choix invalide. Veuillez entrer un numéro valide.");
          menu();
      }
    });
  };
  menu();
}

const portefeuille = new Portefeuille();
interfaceUtilisateur(portefeuille);
