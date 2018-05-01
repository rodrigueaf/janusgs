# language: fr
# auteur Claude Rodrigue AFFODOGANDJI
Fonctionnalité: Gestion des prévisions

  Les informations d'une prévision sont :
  - La description
  - La date pévue de réalisation
  - La date et heure échéance
  - Le taux d'achevement
  - Le type
  - L'état
  - L'observation
  - La recommandation
  - L'heure début
  - L'heure fin
  - L'estimation de la durée
  - L'urgence
  - L'importance
  - La période début de prévision pour la réalisation
  - La période fin de prévision pour la réalisation
  - La catégorie
  - Le projet ou le processus
  - L'objectif ou la vision
  - Le principe


  Scénario: Ajouter une prévision avec succès
    Etant donné qu'aucune prévision n'était pas enregistré
    Et qu'on dispose d'une prévision valide à enregistrer
    Lorsqu' on ajoute la prévision
    Alors on obtient une réponse confirmant la réussite de l'opération d'ajout de la prévision
    Et 1 prévision est créée
    Et la prévision créée est celui soumise à l'ajout

  Scénario: Ajouter une prévision invalide
    Etant donné qu'aucune prévision n'était pas enregistré
    Et qu'on dispose d'une prévision non valide à enregistrer
    Lorsqu' on ajoute la prévision
    Alors on obtient une réponse confirmant l'échec de l'opération d'ajout de la prévision
    Et 0 prévision demeure créée

  Scénario: Modifier une prévision avec succès
    Etant donné qu'une prévision était déjà enregistré
    Et qu'on récupère cette prévision pour le modifier
    Lorsqu' on modifie la prévision
    Alors on obtient une réponse confirmant la réussite de l'opération de modification de la prévision

  Scénario: Modifier une prévision avec une information non valide
    Etant donné qu'une prévision était déjà enregistré
    Et qu'on récupère cette prévision pour le modifier avec des informations non valide
    Lorsqu' on modifie la prévision
    Alors on obtient une réponse confirmant l'échec de l'opération de modification de la prévision

  Scénario: Consulter les détails d'une prévision
    Etant donné qu'une prévision était déjà enregistré
    Lorsqu' on récupère cette prévision
    Alors on obtient la prévision déjà enregistré

  Scénario: Récupérer la liste des lignes d'une prévision
    Etant donné qu'une prévision était déjà enregistré
    Lorsqu' on récupère la liste des lignes de la prévision
    Alors on a une liste contenant les lignes de la prévision déjà enregistré

  Scénario: Supprimer une prévision avec succès
    Etant donné qu'une prévision était déjà enregistré
    Lorsqu' on supprime la prévision
    Alors on obtient une réponse confirmant la réussite de l'opération de suppression de la prévision
    Et 0 prévision est créée

  Scénario: Rechercher une prévision
    Etant donné que deux prévision étaient déjà enregistrés
    Lorsqu' on recherche des prévisions en fonction d'un critère
    Alors les prévisions récupérées doivent respecter le critère défini