# language: fr
# auteur Claude Rodrigue AFFODOGANDJI

Fonctionnalité: Gestion des objectifs

  Les informations d'un objectif sont :
  - Le libellé

  Scénario: Ajouter un objectif avec succès
    Etant donné qu'aucun objectif n'était pas enregistré
    Et qu'on dispose d'un objectif valide à enregistrer
    Lorsqu' on ajoute l'objectif
    Alors on obtient une réponse confirmant la réussite de l'opération d'ajout de l'objectif
    Et 1 objectif est créé
    Et l'objectif créé est celui soumis à l'ajout

  Scénario: Ajouter un objectif dont le libellé existe déjà
    Etant donné qu'un objectif était déjà enregistré
    Et qu'on dispose d'un objectif avec le même libellé à enregistrer
    Lorsqu' on ajoute l'objectif
    Alors on obtient le message sur l'objectif "Un objectif avec le même libellé existe déjà"
    Et 1 objectif demeure créé

  Scénario: Ajouter un objectif dont le libellé n'est pas renseigné
    Etant donné qu'aucun objectif n'était pas enregistré
    Et qu'on dispose d'un objectif dont le libellé n'est pas renseigné
    Lorsqu' on ajoute l'objectif
    Alors on obtient le message sur l'objectif "Le libellé est obligatoire"
    Et 0 objectif demeure créé

  Scénario: Modifier un objectif avec succès
    Etant donné qu'un objectif était déjà enregistré
    Et qu'on récupère cet objectif pour le modifier
    Lorsqu' on modifie l'objectif
    Alors on obtient une réponse confirmant la réussite de l'opération de modification de l'objectif

  Scénario: Modifier un objectif en ne renseignant pas le libellé
    Etant donné qu'un objectif était déjà enregistré
    Et qu'on récupère cet objectif pour le modifier en ne renseignant pas le libellé
    Lorsqu' on modifie l'objectif
    Alors on obtient le message sur l'objectif "Le libellé est obligatoire"

  Scénario: Modifier un objectif en renseignant un libellé déjà existant
    Etant donné que deux objectifs étaient déjà enregistrés
    Et qu'on récupère cet objectif pour modifier son libellé par une valeur qui existe déjà
    Lorsqu' on modifie l'objectif
    Alors on obtient le message sur l'objectif "Un objectif avec le même libellé existe déjà"

  Scénario: Consulter les détails d'un objectif
    Etant donné qu'un objectif était déjà enregistré
    Lorsqu' on récupère cet objectif
    Alors on obtient l'objectif déjà enregistré

  Scénario: Récupérer la liste des objectifs
    Etant donné qu'un objectif était déjà enregistré
    Lorsqu' on récupère la liste des objectifs
    Alors on a une liste contenant l'objectif déjà enregistré

  Scénario: Supprimer un objectif avec succès
    Etant donné qu'un objectif était déjà enregistré
    Lorsqu' on supprime l'objectif
    Alors on obtient une réponse confirmant la réussite de l'opération de suppression de l'objectif
    Et 0 objectif est créé

  Scénario: Rechercher un objectif
    Etant donné que deux objectifs étaient déjà enregistrés
    Lorsqu' on recherche des objectifs en fonction d'un critère
    Alors les objectifs récupérés doivent respecter le critère défini