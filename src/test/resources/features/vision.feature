# language: fr
# auteur Claude Rodrigue AFFODOGANDJI

Fonctionnalité: Gestion des visions

  Les informations d'une vision sont :
  - Le libellé

  Scénario: Ajouter une vision avec succès
    Etant donné qu'aucune vision n'était pas enregistrée
    Et qu'on dispose d'une vision valide à enregistrer
    Lorsqu' on ajoute la vision
    Alors on obtient une réponse confirmant la réussite de l'opération d'ajout de la vision
    Et 1 vision est créée
    Et la vision créée est celui soumise à l'ajout

  Scénario: Ajouter une vision dont le libellé existe déjà
    Etant donné qu'une vision était déjà enregistrée
    Et qu'on dispose d'une vision avec le même libellé à enregistrer
    Lorsqu' on ajoute la vision
    Alors on obtient le message sur la vision "Une vision avec le même libellé existe déjà"
    Et 1 vision demeure créée

  Scénario: Ajouter une vision dont le libellé n'est pas renseigné
    Etant donné qu'aucune vision n'était pas enregistrée
    Et qu'on dispose d'une vision dont le libellé n'est pas renseigné
    Lorsqu' on ajoute la vision
    Alors on obtient le message sur la vision "Le libellé est obligatoire"
    Et 0 vision demeure créée

  Scénario: Modifier une vision avec succès
    Etant donné qu'une vision était déjà enregistrée
    Et qu'on récupère cette vision pour le modifier
    Lorsqu' on modifie la vision
    Alors on obtient une réponse confirmant la réussite de l'opération de modification de la vision

  Scénario: Modifier une vision en ne renseignant pas le libellé
    Etant donné qu'une vision était déjà enregistrée
    Et qu'on récupère cette vision pour le modifier en ne renseignant pas le libellé
    Lorsqu' on modifie la vision
    Alors on obtient le message sur la vision "Le libellé est obligatoire"

  Scénario: Modifier une vision en renseignant un libellé déjà existant
    Etant donné que deux visions étaient déjà enregistrées
    Et qu'on récupère cette vision pour modifier son libellé par une valeur qui existe déjà
    Lorsqu' on modifie la vision
    Alors on obtient le message sur la vision "Une vision avec le même libellé existe déjà"

  Scénario: Consulter les détails d'une vision
    Etant donné qu'une vision était déjà enregistrée
    Lorsqu' on récupère cette vision
    Alors on obtient la vision déjà enregistrée

  Scénario: Récupérer la liste des visions
    Etant donné qu'une vision était déjà enregistrée
    Lorsqu' on récupère la liste des visions
    Alors on a une liste contenant la vision déjà enregistrée

  Scénario: Supprimer une vision avec succès
    Etant donné qu'une vision était déjà enregistrée
    Lorsqu' on supprime la vision
    Alors on obtient une réponse confirmant la réussite de l'opération de suppression de la vision
    Et 0 vision est créée

  Scénario: Rechercher une vision
    Etant donné que deux visions étaient déjà enregistrées
    Lorsqu' on recherche des visions en fonction d'un critère
    Alors les visions récupérées doivent respecter le critère défini