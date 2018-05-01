# language: fr
# auteur Claude Rodrigue AFFODOGANDJI

Fonctionnalité: Gestion des valeurs

  Les informations d'une valeur sont :
  - Le libellé

  Scénario: Ajouter une valeur avec succès
    Etant donné qu'aucune valeur n'était pas enregistrée
    Et qu'on dispose d'une valeur valide à enregistrer
    Lorsqu' on ajoute la valeur
    Alors on obtient une réponse confirmant la réussite de l'opération d'ajout de la valeur
    Et 1 valeur est créée
    Et la valeur créée est celui soumise à l'ajout

  Scénario: Ajouter une valeur dont le libellé existe déjà
    Etant donné qu'une valeur était déjà enregistrée
    Et qu'on dispose d'une valeur avec le même libellé à enregistrer
    Lorsqu' on ajoute la valeur
    Alors on obtient le message sur la valeur "Une valeur avec le même libellé existe déjà"
    Et 1 valeur demeure créée

  Scénario: Ajouter une valeur dont le libellé n'est pas renseigné
    Etant donné qu'aucune valeur n'était pas enregistrée
    Et qu'on dispose d'une valeur dont le libellé n'est pas renseigné
    Lorsqu' on ajoute la valeur
    Alors on obtient le message sur la valeur "Le libellé est obligatoire"
    Et 0 valeur demeure créée

  Scénario: Modifier une valeur avec succès
    Etant donné qu'une valeur était déjà enregistrée
    Et qu'on récupère cette valeur pour le modifier
    Lorsqu' on modifie la valeur
    Alors on obtient une réponse confirmant la réussite de l'opération de modification de la valeur

  Scénario: Modifier une valeur en ne renseignant pas le libellé
    Etant donné qu'une valeur était déjà enregistrée
    Et qu'on récupère cette valeur pour le modifier en ne renseignant pas le libellé
    Lorsqu' on modifie la valeur
    Alors on obtient le message sur la valeur "Le libellé est obligatoire"

  Scénario: Modifier une valeur en renseignant un libellé déjà existant
    Etant donné que deux valeurs étaient déjà enregistrées
    Et qu'on récupère cette valeur pour modifier son libellé par une valeur qui existe déjà
    Lorsqu' on modifie la valeur
    Alors on obtient le message sur la valeur "Une valeur avec le même libellé existe déjà"

  Scénario: Consulter les détails d'une valeur
    Etant donné qu'une valeur était déjà enregistrée
    Lorsqu' on récupère cette valeur
    Alors on obtient la valeur déjà enregistrée

  Scénario: Récupérer la liste des valeurs
    Etant donné qu'une valeur était déjà enregistrée
    Lorsqu' on récupère la liste des valeurs
    Alors on a une liste contenant la valeur déjà enregistrée

  Scénario: Supprimer une valeur avec succès
    Etant donné qu'une valeur était déjà enregistrée
    Lorsqu' on supprime la valeur
    Alors on obtient une réponse confirmant la réussite de l'opération de suppression de la valeur
    Et 0 valeur est créée

  Scénario: Rechercher une valeur
    Etant donné que deux valeurs étaient déjà enregistrées
    Lorsqu' on recherche des valeurs en fonction d'un critère
    Alors les valeurs récupérées doivent respecter le critère défini