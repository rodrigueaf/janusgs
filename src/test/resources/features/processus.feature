# language: fr
# auteur Claude Rodrigue AFFODOGANDJI

Fonctionnalité: Gestion des processus

  Les informations d'un processus sont :
  - Le libellé
  - La date début
  - La date fin

  Scénario: Ajouter un processus avec succès
    Etant donné qu'aucun processus n'était pas enregistré
    Et qu'on dispose d'un processus valide à enregistrer
    Lorsqu' on ajoute le processus
    Alors on obtient une réponse confirmant la réussite de l'opération d'ajout de le processus
    Et 1 processus est créé
    Et le processus créé est celui soumis à l'ajout

  Scénario: Ajouter un processus dont le libellé existe déjà
    Etant donné qu'un processus était déjà enregistré
    Et qu'on dispose d'un processus avec le même libellé à enregistrer
    Lorsqu' on ajoute le processus
    Alors on obtient le message sur le processus "Un processus avec le même libellé existe déjà"
    Et 1 processus demeure créé

  Scénario: Ajouter un processus dont le libellé n'est pas renseigné
    Etant donné qu'aucun processus n'était pas enregistré
    Et qu'on dispose d'un processus dont le libellé n'est pas renseigné
    Lorsqu' on ajoute le processus
    Alors on obtient le message sur le processus "Le libellé est obligatoire"
    Et 0 processus demeure créé

  Scénario: Modifier un processus avec succès
    Etant donné qu'un processus était déjà enregistré
    Et qu'on récupère cet processus pour le modifier
    Lorsqu' on modifie le processus
    Alors on obtient une réponse confirmant la réussite de l'opération de modification de le processus

  Scénario: Modifier un processus en ne renseignant pas le libellé
    Etant donné qu'un processus était déjà enregistré
    Et qu'on récupère cet processus pour le modifier en ne renseignant pas le libellé
    Lorsqu' on modifie le processus
    Alors on obtient le message sur le processus "Le libellé est obligatoire"

  Scénario: Modifier un processus en renseignant un libellé déjà existant
    Etant donné que deux processus étaient déjà enregistrés
    Et qu'on récupère cet processus pour modifier son libellé par une valeur qui existe déjà
    Lorsqu' on modifie le processus
    Alors on obtient le message sur le processus "Un processus avec le même libellé existe déjà"

  Scénario: Consulter les détails d'un processus
    Etant donné qu'un processus était déjà enregistré
    Lorsqu' on récupère cet processus
    Alors on obtient le processus déjà enregistré

  Scénario: Récupérer la liste des processus
    Etant donné qu'un processus était déjà enregistré
    Lorsqu' on récupère la liste des processus
    Alors on a une liste contenant le processus déjà enregistré

  Scénario: Supprimer un processus avec succès
    Etant donné qu'un processus était déjà enregistré
    Lorsqu' on supprime le processus
    Alors on obtient une réponse confirmant la réussite de l'opération de suppression de le processus
    Et 0 processus est créé

  Scénario: Rechercher un processus
    Etant donné que deux processus étaient déjà enregistrés
    Lorsqu' on recherche des processus en fonction d'un critère
    Alors les processus récupérés doivent respecter le critère défini