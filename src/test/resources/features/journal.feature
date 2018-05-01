# language: fr
# auteur Claude Rodrigue AFFODOGANDJI

Fonctionnalité: Gestion du journal

  Les informations d'un journal sont :
  - La description
  - La date de réalisation
  - L'heure début de réalisation
  - L'heure fin de réalisation
  - Le taux d'achevement
  - Le type
  - L'observation
  - La recommandation
  - La catégorie
  - Le projet ou le processus
  - L'objectif ou la vision
  - Le principe
  - La prévision

  Scénario: Ajouter un journal avec succès
    Etant donné qu'aucun journal n'était pas enregistré
    Et qu'on dispose d'un journal valide à enregistrer
    Lorsqu' on ajoute le journal
    Alors on obtient une réponse confirmant la réussite de l'opération d'ajout de le journal
    Et 1 journal est créé
    Et le journal créé est celui soumis à l'ajout

  Scénario: Ajouter un journal invalide
    Etant donné qu'aucun journal n'était pas enregistré
    Et qu'on dispose d'un journal non valide à enregistrer
    Lorsqu' on ajoute le journal
    Alors on obtient une réponse confirmant l'échec de l'opération d'ajout de le journal
    Et 0 journal demeure créé

  Scénario: Modifier un journal avec succès
    Etant donné qu'un journal était déjà enregistré
    Et qu'on récupère cet journal pour le modifier
    Lorsqu' on modifie le journal
    Alors on obtient une réponse confirmant la réussite de l'opération de modification de le journal

  Scénario: Modifier un journal avec une information non valide
    Etant donné qu'un journal était déjà enregistré
    Et qu'on récupère cet journal pour le modifier avec des informations non valide
    Lorsqu' on modifie le journal
    Alors on obtient une réponse confirmant l'échec de l'opération de modification de le journal

  Scénario: Consulter les détails d'un journal
    Etant donné qu'un journal était déjà enregistré
    Lorsqu' on récupère cet journal
    Alors on obtient le journal déjà enregistré

  Scénario: Récupérer la liste des lignes d'un journal
    Etant donné qu'un journal était déjà enregistré
    Lorsqu' on récupère la liste des lignes du journal
    Alors on a une liste contenant les lignes du journal déjà enregistré

  Scénario: Supprimer un journal avec succès
    Etant donné qu'un journal était déjà enregistré
    Lorsqu' on supprime le journal
    Alors on obtient une réponse confirmant la réussite de l'opération de suppression de le journal
    Et 0 journal est créé

  Scénario: Rechercher un journal
    Etant donné que deux journal étaient déjà enregistrés
    Lorsqu' on recherche des journaux en fonction d'un critère
    Alors les journaux récupérés doivent respecter le critère défini