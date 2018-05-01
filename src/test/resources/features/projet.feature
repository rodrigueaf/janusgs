# language: fr
# auteur Claude Rodrigue AFFODOGANDJI

Fonctionnalité: Gestion des projets

  Les informations d'un projet sont :
  - Le libellé
  - La date début
  - La date fin
  - La liste des catégories

  Scénario: Ajouter un projet avec succès
    Etant donné qu'aucun projet n'était pas enregistré
    Et qu'on dispose d'un projet valide à enregistrer
    Lorsqu' on ajoute le projet
    Alors on obtient une réponse confirmant la réussite de l'opération d'ajout de le projet
    Et 1 projet est créé
    Et le projet créé est celui soumis à l'ajout

  Scénario: Ajouter un projet dont le libellé existe déjà
    Etant donné qu'un projet était déjà enregistré
    Et qu'on dispose d'un projet avec le même libellé à enregistrer
    Lorsqu' on ajoute le projet
    Alors on obtient le message sur le projet "Un projet avec le même libellé existe déjà"
    Et 1 projet demeure créé

  Scénario: Ajouter un projet dont le libellé n'est pas renseigné
    Etant donné qu'aucun projet n'était pas enregistré
    Et qu'on dispose d'un projet dont le libellé n'est pas renseigné
    Lorsqu' on ajoute le projet
    Alors on obtient le message sur le projet "Le libellé est obligatoire"
    Et 0 projet demeure créé

  Scénario: Modifier un projet avec succès
    Etant donné qu'un projet était déjà enregistré
    Et qu'on récupère cet projet pour le modifier
    Lorsqu' on modifie le projet
    Alors on obtient une réponse confirmant la réussite de l'opération de modification de le projet

  Scénario: Modifier un projet en ne renseignant pas le libellé
    Etant donné qu'un projet était déjà enregistré
    Et qu'on récupère cet projet pour le modifier en ne renseignant pas le libellé
    Lorsqu' on modifie le projet
    Alors on obtient le message sur le projet "Le libellé est obligatoire"

  Scénario: Modifier un projet en renseignant un libellé déjà existant
    Etant donné que deux projet étaient déjà enregistrés
    Et qu'on récupère cet projet pour modifier son libellé par une valeur qui existe déjà
    Lorsqu' on modifie le projet
    Alors on obtient le message sur le projet "Un projet avec le même libellé existe déjà"

  Scénario: Consulter les détails d'un projet
    Etant donné qu'un projet était déjà enregistré
    Lorsqu' on récupère cet projet
    Alors on obtient le projet déjà enregistré

  Scénario: Récupérer la liste des projets
    Etant donné qu'un projet était déjà enregistré
    Lorsqu' on récupère la liste des projets
    Alors on a une liste contenant le projet déjà enregistré

  Scénario: Supprimer un projet avec succès
    Etant donné qu'un projet était déjà enregistré
    Lorsqu' on supprime le projet
    Alors on obtient une réponse confirmant la réussite de l'opération de suppression de le projet
    Et 0 projet est créé

  Scénario: Rechercher un projet
    Etant donné que deux projet étaient déjà enregistrés
    Lorsqu' on recherche des projets en fonction d'un critère
    Alors les projets récupérés doivent respecter le critère défini