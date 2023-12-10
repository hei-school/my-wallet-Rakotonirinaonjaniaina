using System;
using System.Collections.Generic;

public class Portefeuille
{
    public double Solde { get; private set; }
    private List<string> transactions;
    private List<string> historiqueTransactions;

    public Portefeuille()
    {
        Solde = 0;
        transactions = new List<string>();
        historiqueTransactions = new List<string>();
    }

    public void AjouterFonds(double montant)
    {
        Solde += montant;
        transactions.Add(string.Format("+{0} €", montant));
        historiqueTransactions.Add(string.Format("Ajout de fonds: +{0} €", montant));
    }

    public void RetirerFonds(double montant)
    {
        if (Solde >= montant)
        {
            Solde -= montant;
            transactions.Add(string.Format("-{0} €", montant));
            historiqueTransactions.Add(string.Format("Retrait de fonds: -{0} €", montant));
        }
        else
        {
            Console.WriteLine("Solde insuffisant pour ce retrait.");
        }
    }

    public void AfficherSolde()
    {
        Console.WriteLine("Solde actuel: " + Solde + " €");
    }

    public void AfficherHistoriqueTransactions()
    {
        Console.WriteLine("Historique des transactions:");
        foreach (var transaction in historiqueTransactions)
        {
            Console.WriteLine(transaction);
        }
    }

    public void TransfererFonds(double montant, string destinataire)
    {
        if (Solde >= montant)
        {
            Solde -= montant;
            transactions.Add(string.Format("-{0} €", montant));
            historiqueTransactions.Add(string.Format("Transfert de fonds: -{0} € vers {1}", montant, destinataire));
        }
        else
        {
            Console.WriteLine("Solde insuffisant pour ce transfert.");
        }
    }
}

public class Program
{
    public static void InterfaceUtilisateur(Portefeuille portefeuille)
    {
        while (true)
        {
            Console.WriteLine("\n1. Ajouter des fonds");
            Console.WriteLine("2. Retirer des fonds");
            Console.WriteLine("3. Afficher le solde");
            Console.WriteLine("4. Afficher l'historique des transactions");
            Console.WriteLine("5. Transférer des fonds");
            Console.WriteLine("6. Quitter");

            string choix = Console.ReadLine();

            switch (choix)
            {
                case "1":
                    Console.Write("Entrez le montant à ajouter: ");
                    double montantAjout = Convert.ToDouble(Console.ReadLine());
                    portefeuille.AjouterFonds(montantAjout);
                    break;
                case "2":
                    Console.Write("Entrez le montant à retirer: ");
                    double montantRetrait = Convert.ToDouble(Console.ReadLine());
                    portefeuille.RetirerFonds(montantRetrait);
                    break;
                case "3":
                    portefeuille.AfficherSolde();
                    break;
                case "4":
                    portefeuille.AfficherHistoriqueTransactions();
                    break;
                case "5":
                    Console.Write("Entrez le montant à transférer: ");
                    double montantTransfert = Convert.ToDouble(Console.ReadLine());
                    Console.Write("Entrez le destinataire du transfert: ");
                    string destinataire = Console.ReadLine();
                    portefeuille.TransfererFonds(montantTransfert, destinataire);
                    break;
                case "6":
                    return;
                default:
                    Console.WriteLine("Choix invalide. Veuillez entrer un numéro valide.");
                    break;
            }
        }
    }

    public static void Main(string[] args)
    {
        Portefeuille portefeuille = new Portefeuille();
        InterfaceUtilisateur(portefeuille);
    }
}
