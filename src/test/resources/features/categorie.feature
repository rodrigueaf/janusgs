# language: fr
# auteur Claude Rodrigue AFFODOGANDJI

Fonctionnalité: Gestion des catégories

  Les informations d'une catégorie sont :
  - Le libellé

  Scénario: Ajouter une catégorie avec succès
    Etant donné qu'aucune catégorie n'était pas enregistrée
    Et qu'on dispose d'une catégorie valide à enregistrer
    Lorsqu' on ajoute la catégorie
    Alors on obtient une réponse confirmant la réussite de l'opération d'ajout de la catégorie
    Et 1 catégorie est créée
    Et la catégorie créée est celui soumise à l'ajout

  Scénario: Ajouter une catégorie dont le libellé existe déjà
    Etant donné qu'une catégorie était déjà enregistrée
    Et qu'on dispose d'une catégorie avec le même libellé à enregistrer
    Lorsqu' on ajoute la catégorie
    Alors on obtient le message sur la catégorie "Une catégorie avec le même libellé existe déjà"
    Et 1 catégorie demeure créée

  Scénario: Ajouter une catégorie dont le libellé n'est pas renseigné
    Etant donné qu'aucune catégorie n'était pas enregistrée
    Et qu'on dispose d'une catégorie dont le libellé n'est pas renseigné
    Lorsqu' on ajoute la catégorie
    Alors on obtient le message sur la catégorie "Le libellé est obligatoire"
    Et 0 catégorie demeure créée

  Scénario: Modifier une catégorie avec succès
    Etant donné qu'une catégorie était déjà enregistrée
    Et qu'on récupère cette catégorie pour le modifier
    Lorsqu' on modifie la catégorie
    Alors on obtient une réponse confirmant la réussite de l'opération de modification de la catégorie

  Scénario: Modifier une catégorie en ne renseignant pas le libellé
    Etant donné qu'une catégorie était déjà enregistrée
    Et qu'on récupère cette catégorie pour le modifier en ne renseignant pas le libellé
    Lorsqu' on modifie la catégorie
    Alors on obtient le message sur la catégorie "Le libellé est obligatoire"

  Scénario: Modifier une catégorie en renseignant un libellé déjà existant
    Etant donné que deux catégories étaient déjà enregistrées
    Et qu'on récupère cette catégorie pour modifier son libellé par une valeur qui existe déjà
    Lorsqu' on modifie la catégorie
    Alors on obtient le message sur la catégorie "Une catégorie avec le même libellé existe déjà"

  Scénario: Consulter les détails d'une catégorie
    Etant donné qu'une catégorie était déjà enregistrée
    Lorsqu' on récupère cette catégorie
    Alors on obtient la catégorie déjà enregistrée

  Scénario: Récupérer la liste des catégories
    Etant donné qu'une catégorie était déjà enregistrée
    Lorsqu' on récupère la liste des catégories
    Alors on a une liste contenant la catégorie déjà enregistrée

  Scénario: Supprimer une catégorie avec succès
    Etant donné qu'une catégorie était déjà enregistrée
    Lorsqu' on supprime la catégorie
    Alors on obtient une réponse confirmant la réussite de l'opération de suppression de la catégorie
    Et 0 catégorie est créée

  Scénario: Rechercher une catégorie
    Etant donné que deux catégories étaient déjà enregistrées
    Lorsqu' on recherche des catégories en fonction d'un critère
    Alors les catégories récupérées doivent respecter le critère défini