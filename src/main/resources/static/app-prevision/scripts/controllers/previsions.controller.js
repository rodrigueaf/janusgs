/**
 * Controlleur d'édition des comptes
 */
angular.module('app')
    .controller('PrevisionsControllerEdit',
        ['$scope', 'PrevisionService', 'utils', 'uiNotif', 'CategorieService',
            'ProjetService', 'ProcessusService', 'ObjectifService',
            function ($scope, PrevisionService, utils, uiNotif,
                      CategorieService, ProjetService, ProcessusService, ObjectifService) {
                $scope.query = utils.initPagination;

                var initFilterForm = {
                    prevision: {
                        description: ''
                    },
                    page: $scope.query.page - 1,
                    size: $scope.query.limit
                };

                $scope.filterForm = angular.copy(initFilterForm);

                $scope.isResearch = false;

                $scope.previsions = [];

                $scope.getReduceDescription = function (fullDescription, taille) {
                    taille = angular.isUndefined(taille) ? 100 : taille;
                    if (fullDescription !== null && !angular.isUndefined(fullDescription)
                        && fullDescription.length > taille) {
                        return fullDescription.substring(0, taille) + "...";
                    }
                    return fullDescription;
                };

                $scope.getAll = function (page, limit) {

                    if ($scope.isResearch) {

                        $scope.filterForm.page = page - 1;
                        $scope.filterForm.size = limit;
                        $scope.doSearch();

                    } else {
                        $scope.promise = PrevisionService.findAll({page: page - 1, size: limit}).$promise;
                        $scope.promise.then(function (response) {
                                $scope.previsions = response.data.content;
                                $scope.query.count = response.data.totalElements;
                            },
                            function (error) {
                                uiNotif.info(error.data.message);
                            });
                    }
                };

                $scope.getAll($scope.query.page, $scope.query.limit);

                $scope.doSearch = function () {

                    $scope.isResearch = true;

                    $scope.promise = PrevisionService.search($scope.filterForm).$promise;
                    $scope.promise.then(function (response) {

                            $scope.previsions = response.data.content;
                            $scope.query.count = response.data.totalElements;

                        },
                        function (error) {
                            uiNotif.info(error.data.message);
                        });
                };

                $scope.fetchCategorie = function ($select, $event) {

                    $scope.max = 10;

                    if (!$event) {
                        $scope.page = 1;
                        $scope.categories = [];
                    } else {
                        $event.stopPropagation();
                        $event.preventDefault();
                        $scope.page++;
                    }

                    var categorieToSearch = {
                        categorie: {
                            libelle: $select.search,
                            state: null
                        },
                        parent:false,
                        size: $scope.max,
                        page: $scope.page - 1
                    };
                    $scope.loading = true;

                    Array.prototype.unique = function () {
                        var a = this.concat();
                        for (var i = 0; i < a.length; ++i) {
                            for (var j = i + 1; j < a.length; ++j) {
                                if (a[i].identifiant === a[j].identifiant)
                                    a.splice(j--, 1);
                            }
                        }

                        return a;
                    };
                    //appel vers le serveur
                    CategorieService.search(categorieToSearch).$promise.then(function (response) {
                        var content = response.data.content;

                        $scope.categories = $scope.categories.concat(content).unique();

                        $scope.endOfList = (response.data.totalElements === $scope.categories.length);
                        $scope.loading = false;
                    }, function (error) {
                        uiNotif.info(error.data.message);
                        $scope.loading = false;
                    });
                };

                $scope.fetchDomaine = function ($select, $event) {

                    $scope.max = 10;

                    if (!$event) {
                        $scope.page = 1;
                        $scope.domaines = [];
                    } else {
                        $event.stopPropagation();
                        $event.preventDefault();
                        $scope.page++;
                    }

                    var categorieToSearch = {
                        categorie: {
                            libelle: $select.search,
                            state: null
                        },
                        parent: true,
                        size: $scope.max,
                        page: $scope.page - 1
                    };
                    $scope.loading = true;

                    Array.prototype.unique = function () {
                        var a = this.concat();
                        for (var i = 0; i < a.length; ++i) {
                            for (var j = i + 1; j < a.length; ++j) {
                                if (a[i].identifiant === a[j].identifiant)
                                    a.splice(j--, 1);
                            }
                        }

                        return a;
                    };
                    //appel vers le serveur
                    CategorieService.search(categorieToSearch).$promise.then(function (response) {
                        var content = response.data.content;

                        $scope.domaines = $scope.domaines.concat(content).unique();

                        $scope.endOfList = (response.data.totalElements === $scope.domaines.length);
                        $scope.loading = false;
                    }, function (error) {
                        uiNotif.info(error.data.message);
                        $scope.loading = false;
                    });
                };

                $scope.fetchProjet = function ($select, $event) {

                    $scope.max = 10;

                    if (!$event) {
                        $scope.page = 1;
                        $scope.projets = [];
                    } else {
                        $event.stopPropagation();
                        $event.preventDefault();
                        $scope.page++;
                    }

                    var projetToSearch = {
                        projet: {
                            libelle: $select.search,
                            state: null
                        },
                        size: $scope.max,
                        page: $scope.page - 1
                    };
                    $scope.loading = true;

                    Array.prototype.unique = function () {
                        var a = this.concat();
                        for (var i = 0; i < a.length; ++i) {
                            for (var j = i + 1; j < a.length; ++j) {
                                if (a[i].identifiant === a[j].identifiant)
                                    a.splice(j--, 1);
                            }
                        }

                        return a;
                    };
                    //appel vers le serveur
                    ProjetService.search(projetToSearch).$promise.then(function (response) {
                        var content = response.data.content;

                        $scope.projets = $scope.projets.concat(content).unique();

                        $scope.endOfList = (response.data.totalElements === $scope.projets.length);
                        $scope.loading = false;
                    }, function (error) {
                        uiNotif.info(error.data.message);
                        $scope.loading = false;
                    });
                };

                $scope.fetchProcessus = function ($select, $event) {

                    $scope.max = 10;

                    if (!$event) {
                        $scope.page = 1;
                        $scope.processuss = [];
                    } else {
                        $event.stopPropagation();
                        $event.preventDefault();
                        $scope.page++;
                    }

                    var processusToSearch = {
                        processus: {
                            libelle: $select.search,
                            state: null
                        },
                        size: $scope.max,
                        page: $scope.page - 1
                    };
                    $scope.loading = true;

                    Array.prototype.unique = function () {
                        var a = this.concat();
                        for (var i = 0; i < a.length; ++i) {
                            for (var j = i + 1; j < a.length; ++j) {
                                if (a[i].identifiant === a[j].identifiant)
                                    a.splice(j--, 1);
                            }
                        }

                        return a;
                    };
                    //appel vers le serveur
                    ProcessusService.search(processusToSearch).$promise.then(function (response) {
                        var content = response.data.content;

                        $scope.processuss = $scope.processuss.concat(content).unique();

                        $scope.endOfList = (response.data.totalElements === $scope.processuss.length);
                        $scope.loading = false;
                    }, function (error) {
                        uiNotif.info(error.data.message);
                        $scope.loading = false;
                    });
                };

                $scope.fetchObjectif = function ($select, $event) {

                    $scope.max = 10;

                    if (!$event) {
                        $scope.page = 1;
                        $scope.objectifs = [];
                    } else {
                        $event.stopPropagation();
                        $event.preventDefault();
                        $scope.page++;
                    }

                    var objectifToSearch = {
                        objectif: {
                            libelle: $select.search,
                            state: null
                        },
                        size: $scope.max,
                        page: $scope.page - 1
                    };
                    $scope.loading = true;

                    Array.prototype.unique = function () {
                        var a = this.concat();
                        for (var i = 0; i < a.length; ++i) {
                            for (var j = i + 1; j < a.length; ++j) {
                                if (a[i].identifiant === a[j].identifiant)
                                    a.splice(j--, 1);
                            }
                        }

                        return a;
                    };
                    //appel vers le serveur
                    ObjectifService.search(objectifToSearch).$promise.then(function (response) {
                        var content = response.data.content;

                        $scope.objectifs = $scope.objectifs.concat(content).unique();

                        $scope.endOfList = (response.data.totalElements === $scope.objectifs.length);
                        $scope.loading = false;
                    }, function (error) {
                        uiNotif.info(error.data.message);
                        $scope.loading = false;
                    });
                };

                $scope.types = ['TACHE', 'MEMO', 'ACTIVITE', 'NOTE', 'RAPPEL'];
                $scope.etats = ['ATTENTE', 'INTEGRER', 'EN_COURS', 'EXECUTE', 'A_REPORTER', 'REPORTER', 'CLOTURER', 'ARCHIVER'];
                $scope.finalites = ['ACTIVITE', 'PROJET', 'PROCESSUS', 'OBJECTIF', 'PRINCIPE_VALEUR'];

                $scope.removePrevision = function (index, ev, identifiant) {
                    if (identifiant === null) {
                        $scope.previsions.splice(index, 1);
                    } else {
                        var title = utils.dialogTitleRemoval;
                        var message = 'Cette prévision sera définitivement supprimée';

                        uiNotif.mdDialog(ev, title, message).then(function () {

                            $scope.promise = PrevisionService.remove({previsionId: identifiant}).$promise;
                            $scope.promise.then(function (response) {
                                    uiNotif.info(response.message);
                                    $scope.previsions.splice(index, 1);
                                    $scope.refresh();
                                },
                                function (error) {
                                    uiNotif.info(error.data.message);
                                });

                        });
                    }
                };

                $scope.addPrevision = function () {
                    $scope.inserted = {
                        identifiant: null,
                        dateCreation: new Date(),
                        description: null,
                        etatItem: 'EN_COURS',
                        typeItem: 'TACHE',
                        importance: null,
                        urgence: null,
                        dateHeureEcheance: null,
                        categorie: null,
                        projet: null,
                        processus: null,
                        objectif: null,
                        datePrevueRealisation: null,
                        periodeDebutPrevueRealisation: null,
                        periodeFinPrevueRealisation: null
                    };
                    $scope.previsions.push($scope.inserted);
                };

                $scope.savePrevision = function (data, id) {
                    if (id !== null) {
                        data.identifiant = id;
                        $scope.promise = PrevisionService.update(data).$promise;
                        $scope.promise.then(function (response) {
                            uiNotif.info(response.message);
                        }, function (error) {
                            uiNotif.info(error.data.message);
                        });
                    } else {
                        $scope.promise = PrevisionService.save(data).$promise;
                        $scope.promise.then(function (response) {
                            uiNotif.info(response.message);
                        }, function (error) {
                            uiNotif.info(error.data.message);
                        });
                    }
                };

                $scope.refresh = function () {
                    $scope.isResearch = false;
                    $scope.getAll($scope.query.page, $scope.query.limit);
                    $scope.filterForm = angular.copy(initFilterForm);
                };
            }
        ]);
