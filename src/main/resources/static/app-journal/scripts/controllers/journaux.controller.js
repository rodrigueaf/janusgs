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

                $scope.options = {
                    maxDate: new Date(),
                    showWeeks: false,
                    startingDay: 1
                };

                $scope.datePickerOpenStatus = {};
                $scope.datePickerOpenStatus.heureDebutRealisation = false;

                $scope.openCalendar = function openCalendar(date) {
                    $scope.datePickerOpenStatus[date] = true;
                }
            }
        ]);

angular.module('app')
    .controller('JournauxControllerImport',
        ['$scope', 'PrevisionService', 'utils', 'uiNotif', 'CategorieService',
            'ProjetService', 'ProcessusService', 'ObjectifService', 'JournalService',
            function ($scope, PrevisionService, utils, uiNotif,
                      CategorieService, ProjetService, ProcessusService, ObjectifService, JournalService) {
                $scope.journal = null;

                String.prototype.count = function (c) {
                    var result = 0, i = 0;
                    for (i; i < this.length; i++) if (this[i] === c) result++;
                    return result;
                };

                var controlerText = function (text) {
                    var tab = text.match(/([0-9]{2}\/[0-9]{2}\/[0-9]{4};([0-9]{2}:[0-9]{2})?;([0-9]{2}:[0-9]{2})?;[^;]+)+/);
                    if(tab !== null){
                        for(var i = 0; i < tab.length; i++){
                            if(tab[i] === text){
                                return true;
                            }
                        }
                    }
                    return false;
                };

                var refactorer = function (words) {
                    var tab = words.split('\n');
                    var newTab = [];
                    for (var i = 0; i < tab.length; i++) {
                        if (tab[i].count('"') === 4) {
                            newTab.push(tab[i]);
                        }
                        else if (tab[i].includes('""')) {
                            var text = tab[i] + '\n';
                            for (var j = i + 1; j < tab.length; j++) {
                                if (!tab[j].includes('""')) {
                                    text += tab[j] + '\n';
                                } else {
                                    text += tab[j];
                                    i = j;
                                    break;
                                }
                            }
                            newTab.push(text);
                        } else {
                            if (controlerText(tab[i])) {
                                newTab.push(tab[i]);
                            } else {
                                uiNotif.info("Le texte importé ne respecte pas le format requis à la ligne " + (i + 1));
                                return null;
                            }
                        }
                    }
                    return newTab;
                };

                $scope.savePrevision = function () {
                    var result = refactorer($scope.journal);
                    if (result !== null) {
                        $scope.promise = JournalService.import(result).$promise;
                        $scope.promise.then(function (response) {
                            uiNotif.info(response.message);
                            $scope.journal = null;
                        }, function (error) {
                            uiNotif.info(error.data.message);
                        });
                    }
                };
            }
        ]);

angular.module('app')
    .controller('JournauxControllerDataGrid',
        ['$scope', 'PrevisionService', 'utils', 'uiNotif', 'CategorieService',
            'ProjetService', 'ProcessusService', 'ObjectifService', 'JournalService',
            'uiGridConstants',
            function ($scope, PrevisionService, utils, uiNotif,
                      CategorieService, ProjetService, ProcessusService, ObjectifService, JournalService,
                      uiGridConstants) {

                var gridApi;

                PrevisionService.search({
                    prevision: {description: ""},
                    size: 9999999,
                    page: 0
                }).$promise.then(function (response) {
                        $scope.previsions = response.data.content;
                    },
                    function (error) {
                        uiNotif.info(error.data.message);
                    });

                ProcessusService.search({
                    processus: {libelle: "", state: null},
                    size: 9999999,
                    page: 0
                }).$promise.then(function (response) {
                    $scope.processuss = response.data.content;
                }, function (error) {
                    uiNotif.info(error.data.message);
                });

                ProjetService.search({
                    projet: {libelle: "", state: null},
                    size: 9999999,
                    page: 0
                }).$promise.then(function (response) {
                    $scope.projets = response.data.content;
                }, function (error) {
                    uiNotif.info(error.data.message);
                });

                CategorieService.search({
                    categorie: {libelle: "", state: null},
                    parent: true,
                    size: 9999999,
                    page: 0
                }).$promise.then(function (response) {
                    $scope.domaines = response.data.content;
                }, function (error) {
                    uiNotif.info(error.data.message);
                });

                CategorieService.search({
                    categorie: {libelle: "", state: null},
                    parent: false,
                    size: 9999999,
                    page: 0
                }).$promise.then(function (response) {
                    $scope.categories = response.data.content;
                }, function (error) {
                    uiNotif.info(error.data.message);
                });

                $scope.types = [{id: 'TACHE', value: 'TACHE'},
                    {id: 'MEMO', value: 'MEMO'},
                    {id: 'ACTIVITE', value: 'ACTIVITE'},
                    {id: 'NOTE', value: 'NOTE'},
                    {id: 'RAPPEL', value: 'RAPPEL'}];

                $scope.description = '';
                $scope.showDescription = false;
                $scope.rowInDescriptionEditing = null;

                $scope.descriptionCellClicked = function (row, col) {
                    $scope.showDescription = true;
                    $scope.description = row.entity.description;
                    $scope.rowInDescriptionEditing = row.entity;
                };

                $scope.validerDescription = function () {
                    $scope.showDescription = false;
                    if ($scope.rowInDescriptionEditing !== null) {
                        $scope.rowInDescriptionEditing.description = $scope.description;
                        saveJournal($scope.rowInDescriptionEditing);
                        $scope.rowInDescriptionEditing = null;
                    }
                };

                $scope.cancelDescription = function () {
                    $scope.showDescription = false;
                    $scope.rowInDescriptionEditing = null;
                };

                $scope.gridOptions = {
                    data: 'myData',
                    rowHeight: 38,
                    enableFullRowSelection: true,
                    enableCellEditOnFocus: true,
                    enableColumnResizing: true,
                    enableFiltering: true,
                    enableGridMenu: true,
                    showGridFooter: true,
                    showColumnFooter: true,
                    fastWatch: true,
                    rowIdentity: getRowId,
                    getRowIdentity: getRowId,
                    importerDataAddCallback: function importerDataAddCallback(grid, newObjects) {
                        $scope.myData = $scope.data.concat(newObjects);
                    },
                    columnDefs: [
                        {name: '#', field: 'identifiant', width: 50, enableCellEdit: false},
                        {
                            name: 'Prévision',
                            field: 'prevision.description',
                            width: 150,
                            editableCellTemplate: 'ui-grid/dropdownEditor',
                            editDropdownIdLabel: 'description',
                            editDropdownValueLabel: 'description',
                            editDropdownOptionsFunction: function (rowEntity, colDef) {
                                return $scope.previsions;
                            }
                        },
                        {
                            name: 'Date',
                            field: 'dateRealisation',
                            width: 150,
                            type: 'date',
                            enableFiltering: true,
                            enableCellEdit: true,
                            cellFilter: 'date:"dd/MM/yyyy"'
                        },
                        {
                            name: 'Heure début',
                            field: 'heureDebutRealisation',
                            width: 100,
                            enableFiltering: true,
                            enableCellEdit: true,
                            cellFilter: 'date:"HH:mm"',
                            editableCellTemplate: '<input type="time" style="font-size: 14px;" ui-grid-editor ng-model="MODEL_COL_FIELD"/>',
                            cellClass: function (grid, row, col, rowRenderIndex, colRenderIndex) {
                                if (grid.getCellValue(row, col) !== null && grid.getCellValue(row, col).getTime() === 0) {
                                    return 'text-hidden';
                                }
                            }
                        },
                        {
                            name: 'Heure fin',
                            field: 'heureFinRealisation',
                            width: 100,
                            enableFiltering: true,
                            enableCellEdit: true,
                            cellFilter: 'date:"HH:mm"',
                            editableCellTemplate: '<input type="time" style="font-size: 14px;" ui-grid-editor ng-model="MODEL_COL_FIELD"/>',
                            cellClass: function (grid, row, col, rowRenderIndex, colRenderIndex) {
                                if (grid.getCellValue(row, col) !== null && grid.getCellValue(row, col).getTime() === 0) {
                                    return 'text-hidden';
                                }
                            }
                        },
                        {
                            name: 'description',
                            width: 200,
                            enableCellEdit: false,
                            cellTemplate: '<div><div ng-click="grid.appScope.descriptionCellClicked(row,col)" class="ui-grid-cell-contents"' +
                            ' title="TOOLTIP">{{COL_FIELD CUSTOM_FILTERS}}</div></div>'
                        },
                        {
                            name: 'domaine',
                            field: 'domaine.libelle',
                            width: 150,
                            editDropdownIdLabel: 'libelle',
                            editDropdownValueLabel: 'libelle',
                            editableCellTemplate: 'uiSelectDomaine',
                            editDropdownOptionsFunction: function (rowEntity, colDef) {
                                return $scope.domaines;
                            }
                        },
                        {
                            name: 'type',
                            field: 'typeItem',
                            width: 100,
                            editableCellTemplate: 'ui-grid/dropdownEditor',
                            editDropdownOptionsArray: $scope.types
                        },
                        {
                            name: 'catégorie',
                            field: 'categorie.libelle',
                            width: 150,
                            editDropdownIdLabel: 'libelle',
                            editDropdownValueLabel: 'libelle',
                            editableCellTemplate: 'uiSelectCategorie',
                            editDropdownOptionsFunction: function (rowEntity, colDef) {
                                return $scope.categories;
                            }
                        },
                        {
                            name: 'projet',
                            field: 'projet.libelle',
                            width: 150,
                            editableCellTemplate: 'uiSelectProjet',
                            editDropdownIdLabel: 'libelle',
                            editDropdownValueLabel: 'libelle',
                            editDropdownOptionsFunction: function (rowEntity, colDef) {
                                return $scope.projets;
                            }
                        },
                        {
                            name: 'processus',
                            field: 'processus.libelle',
                            width: 150,
                            editableCellTemplate: 'uiSelectProcessus',
                            editDropdownIdLabel: 'libelle',
                            editDropdownValueLabel: 'libelle',
                            editDropdownOptionsFunction: function (rowEntity, colDef) {
                                return $scope.processuss;
                            }
                        },
                        {name: 'observation', width: 150, enableCellEdit: true}
                    ],
                    onRegisterApi: function onRegisterApi(registeredApi) {
                        gridApi = registeredApi;
                        gridApi.edit.on.afterCellEdit($scope, function (rowEntity, colDef, newValue, oldValue) {
                            $scope.msg.lastCellEdited = 'Ligne id:' + rowEntity.identifiant + '; Colonne:' + colDef.name
                                + '; nouvelle valeur: [' + newValue + ']; ancienne valeur: [' + oldValue + ']';
                            $scope.$apply();
                            if (newValue !== oldValue)
                                saveJournal(rowEntity);
                        });
                    }
                };

                var saveJournal = function (rowEntity) {
                    if (!rowEntity.newItem) {
                        $scope.promise = JournalService.update(rowEntity).$promise;
                        $scope.promise.then(function (response) {
                            uiNotif.info(response.message);
                        }, function (error) {
                            uiNotif.info(error.data.message);
                        });
                    } else {
                        rowEntity.identifiant = null;
                        $scope.promise = JournalService.save(rowEntity).$promise;
                        $scope.promise.then(function (response) {
                            uiNotif.info(response.message);
                            $scope.refreshData();
                        }, function (error) {
                            uiNotif.info(error.data.message);
                        });
                    }
                };

                $scope.msg = {};

                function getRowId(row) {
                    return row.identifiant;
                }

                $scope.toggleFilterRow = function () {
                    $scope.gridOptions.enableFiltering = !$scope.gridOptions.enableFiltering;
                    gridApi.core.notifyDataChange(uiGridConstants.dataChange.COLUMN);
                };

                var i = 0;
                $scope.refreshData = function () {
                    $scope.myData = [];
                    $scope.getAll($scope.query.page, $scope.query.limit);
                };

                $scope.query = utils.initPagination;

                $scope.getAll = function (page, limit) {

                    $scope.promise = JournalService.findAll({page: page - 1, size: limit}).$promise;
                    $scope.promise.then(function (response) {
                            $scope.myData = [];
                            var data = response.data.content;
                            $scope.journaux = response.data.content;
                            $scope.query.count = response.data.totalElements;

                            data.forEach(function (row) {
                                i++;
                                row.dateRealisation = new Date(row.dateRealisation);
                                if (row.heureDebutRealisation !== null) {
                                    var split = row.heureDebutRealisation.split(':');
                                    row.heureDebutRealisation = new Date(1970, 0, 1, split[0], split[1], split[2]);
                                }
                                if (row.heureFinRealisation !== null) {
                                    split = row.heureFinRealisation.split(':');
                                    row.heureFinRealisation = new Date(1970, 0, 1, split[0], split[1], split[2]);
                                }
                                row.newItem = false;
                                $scope.myData.push(row);
                            });
                        },
                        function (error) {
                            uiNotif.info(error.data.message);
                        });
                };

                $scope.getAll($scope.query.page, $scope.query.limit);

                Date.prototype.addHours = function (h) {
                    this.setTime(this.getTime() + (h * 60 * 60 * 1000));
                    return this;
                };

                $scope.addPrevision = function () {
                    var now = new Date();
                    now.setSeconds(0, 0);
                    $scope.inserted = {
                        identifiant: ($scope.myData.length !== 0) ? $scope.myData[0].identifiant + 1 : 1,
                        dateRealisation: now,
                        heureDebutRealisation: now,
                        heureFinRealisation: now.addHours(1),
                        description: null,
                        typeItem: 'TACHE',
                        categorie: null,
                        projet: null,
                        processus: null,
                        observation: null,
                        recommandation: null,
                        newItem: true
                    };
                    $scope.myData.unshift($scope.inserted);
                };

                $scope.removePrevision = function (ev) {

                    if (gridApi.selection.getSelectedGridRows().length !== 0) {
                        var title = utils.dialogTitleRemoval;
                        var message = 'Voulez-vous vraiement effectuer cette opération?';

                        uiNotif.mdDialog(ev, title, message).then(function () {

                            gridApi.selection.getSelectedGridRows().forEach(function (row) {
                                $scope.promise = JournalService.remove({journalId: row.entity.identifiant}).$promise;
                                $scope.promise.then(function (response) {
                                        $scope.refreshData();
                                    },
                                    function (error) {
                                        uiNotif.info(error.data.message);
                                    });
                            });
                        });
                    } else {
                        uiNotif.info("Aucune ligne n'a été sélectionnée");
                    }
                }
            }
        ]);
