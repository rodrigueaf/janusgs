/**
 * Controlleur d'édition des comptes
 */
angular.module('app')
    .controller('JournauxControllerEdit',
        ['$scope', 'PrevisionService', 'utils', 'uiNotif', 'CategorieService',
            'ProjetService', 'ProcessusService', 'ObjectifService', 'JournalService',
            function ($scope, PrevisionService, utils, uiNotif,
                      CategorieService, ProjetService, ProcessusService, ObjectifService, JournalService) {
                $scope.query = utils.initPagination;

                var initFilterForm = {
                    journal: {
                        description: ''
                    },
                    page: $scope.query.page - 1,
                    size: $scope.query.limit
                };

                $scope.filterForm = angular.copy(initFilterForm);

                $scope.isResearch = false;

                $scope.journaux = [];

                $scope.getReduceDescription = function (fullDescription, taille) {
                    taille = angular.isUndefined(taille) ? 50 : taille;
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
                        $scope.promise = JournalService.findAll({page: page - 1, size: limit}).$promise;
                        $scope.promise.then(function (response) {
                                $scope.journaux = response.data.content;
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

                    $scope.promise = JournalService.search($scope.filterForm).$promise;
                    $scope.promise.then(function (response) {

                            $scope.journaux = response.data.content;
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
                        parent: false,
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

                $scope.types = ['TACHE', 'MEMO', 'ACTIVITE', 'NOTE', 'RAPPEL'];

                $scope.removePrevision = function (index, ev, identifiant) {
                    if (identifiant === null) {
                        $scope.journaux.splice(index, 1);
                    } else {
                        var title = utils.dialogTitleRemoval;
                        var message = 'Ce journal sera définitivement supprimé';

                        uiNotif.mdDialog(ev, title, message).then(function () {

                            $scope.promise = JournalService.remove({journalId: identifiant}).$promise;
                            $scope.promise.then(function (response) {
                                    uiNotif.info(response.message);
                                    $scope.journaux.splice(index, 1);
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
                        dateRealisation: null,
                        heureDebutRealisation: null,
                        heureFinRealisation: null,
                        description: null,
                        typeItem: 'TACHE',
                        categorie: null,
                        projet: null,
                        processus: null,
                        observation: null,
                        recommandation: null
                    };
                    $scope.journaux.push($scope.inserted);
                };

                $scope.savePrevision = function (data, id) {
                    if (id !== null) {
                        data.identifiant = id;
                        $scope.promise = JournalService.update(data).$promise;
                        $scope.promise.then(function (response) {
                            uiNotif.info(response.message);
                        }, function (error) {
                            uiNotif.info(error.data.message);
                        });
                    } else {
                        $scope.promise = JournalService.save(data).$promise;
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

                $scope.loadJournal = function (item, journal) {
                    PrevisionService.findOne({previsionId: item.identifiant}).$promise
                        .then(function (response) {
                                var content = response.data;
                                journal.domaine = content.domaine;
                                journal.description = content.description;
                                journal.categorie = content.categorie;
                                journal.projet = content.projet;
                                journal.processus = content.processus;
                                journal.typeItem = content.typeItem;
                            },
                            function (error) {
                                uiNotif.info(error.data.message);
                            });
                };

                $scope.fetch = function ($select, $event) {

                    $scope.max = 10;

                    if (!$event) {
                        $scope.page = 1;
                        $scope.previsions = [];
                    } else {
                        $event.stopPropagation();
                        $event.preventDefault();
                        $scope.page++;
                    }

                    var previsionForSearch = {
                        prevision: {
                            description: $select.search
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

                    PrevisionService.search(previsionForSearch).$promise
                        .then(function (response) {
                                var content = response.data.content;
                                $scope.previsions = $scope.previsions.concat(content).unique();
                                $scope.endOfList = (response.data.totalElements === $scope.previsions.length);
                                $scope.loading = false;
                            },
                            function (error) {
                                uiNotif.info(error.data.message);
                                $scope.loading = false;
                            });
                };
            }
        ]);

angular.module('app')
    .controller('JournauxControllerImport',
        ['$scope', 'PrevisionService', 'utils', 'uiNotif', 'CategorieService',
            'ProjetService', 'ProcessusService', 'ObjectifService', 'JournalService',
            function ($scope, PrevisionService, utils, uiNotif,
                      CategorieService, ProjetService, ProcessusService, ObjectifService, JournalService) {
                $scope.journal = null;

                var refactorer = function (text) {
                    return text.split('\n');
                };

                $scope.savePrevision = function () {
                    $scope.promise = JournalService.import(refactorer($scope.journal)).$promise;
                    $scope.promise.then(function (response) {
                        uiNotif.info(response.message);
                        $scope.journal = null;
                    }, function (error) {
                        uiNotif.info(error.data.message);
                    });
                };
            }
        ]);
