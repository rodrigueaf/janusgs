/**
 * Controlleur d'édition des projets
 */
angular.module('app')
    .controller('ProjetsControllerEdit',
        ['$scope', '$state', '$stateParams', 'ProjetService', 'uiNotif', 'utils',
            function ($scope, $state, $stateParams, ProjetService, uiNotif, utils) {

                /**
                 * Initialisation des données
                 */
                var initProjets = {
                    identifiant: null,
                    libelle: '',
                    state: utils.states[0].value
                };

                if ($stateParams.initData === null) {
                    $scope.projet = angular.copy(initProjets);
                } else {
                    $scope.projet = angular.copy($stateParams.initData);
                }

                /*********************************************************************************/
                /**
                 * Ajout d'un projet
                 */
                var create = function () {

                    $scope.promise = ProjetService.save($scope.projet).$promise;
                    $scope.promise.then(function (response) {

                        $scope.projet = angular.copy(initProjets);
                        uiNotif.info(response.message);

                    }, function (error) {
                        uiNotif.info(error.data.message);
                    });
                };
                /*********************************************************************************/

                /**
                 * Mise à jour d'un projet
                 */
                var update = function () {

                    $scope.promise = ProjetService.update($scope.projet).$promise;
                    $scope.promise.then(function (response) {

                        $scope.projet = angular.copy(initProjets);
                        uiNotif.info(response.message);
                        $state.go('ui.projets.list');

                    }, function (error) {
                        $scope.projet = angular.copy($stateParams.initData);
                        uiNotif.info(error.data.message);
                    });
                };
                /*********************************************************************************/

                /**
                 * Edition de projet
                 */
                $scope.doEdit = function () {

                    if ($scope.projet.identifiant === null) {
                        create();
                    } else {
                        update();
                    }
                };

            }
        ]);

/**
 * Controlleur de la page de liste des projets
 */
angular.module('app')
    .controller('ProjetsControllerList',
        ['$scope', '$state', 'ProjetService', 'uiNotif', 'utils',
            function ($scope, $state, ProjetService, uiNotif, utils) {
                /**
                 * Initialisation des données
                 */
                $scope.projets = [];

                $scope.projetsSelected = [];

                $scope.query = utils.initPagination;

                var initFilterForm = {
                    projet: {
                        libelle: '',
                        state: null
                    },
                    page: $scope.query.page - 1,
                    size: $scope.query.limit
                };

                $scope.filterForm = angular.copy(initFilterForm);

                $scope.isResearch = false;

                /*********************************************************************************/

                /**
                 * Liste des projets
                 */
                $scope.getAll = function (page, limit) {

                    if ($scope.isResearch) {

                        $scope.filterForm.page = page - 1;
                        $scope.filterForm.size = limit;
                        $scope.doSearch();

                    } else {
                        $scope.promise = ProjetService.findAll({page: page - 1, size: limit}).$promise;
                        $scope.promise.then(function (response) {

                                $scope.projets = response.data.content;
                                $scope.query.count = response.data.totalElements;

                            },
                            function (error) {
                                uiNotif.info(error.data.message);
                            });
                    }
                };

                /*********************************************************************************/
                /**
                 * Appel de la liste des projets
                 */
                $scope.getAll($scope.query.page, $scope.query.limit);


                /*********************************************************************************/

                /**
                 * Méthode de suppression d'un projet
                 */
                $scope.remove = function (idProjet, ev) {

                    var title = utils.dialogTitleRemoval;
                    var message = 'Ce projet sera définitivement supprimé';

                    uiNotif.mdDialog(ev, title, message).then(function () {

                        $scope.promise = ProjetService.remove({projetId: idProjet}).$promise;
                        $scope.promise.then(function (response) {

                                uiNotif.info(response.message);
                                $scope.getAll($scope.query.page, $scope.query.limit);

                            },
                            function (error) {
                                uiNotif.info(error.data.message);
                            });

                    }, function () {
                        // traitement quand on clique sur 'NON'
                    });
                };

                /*********************************************************************************/

                /**
                 * Méthode de recherche des projets
                 */
                $scope.doSearch = function () {

                    $scope.isResearch = true;

                    $scope.promise = ProjetService.search($scope.filterForm).$promise;
                    $scope.promise.then(function (response) {

                            $scope.projets = response.data.content;
                            $scope.query.count = response.data.totalElements;

                        },
                        function (error) {
                            uiNotif.info(error.data.message);
                        });
                };
                /*********************************************************************************/

                /**
                 * Méthode pour rafraichir la liste des projets
                 */
                $scope.refresh = function () {
                    $scope.isResearch = false;
                    $scope.getAll($scope.query.page, $scope.query.limit);
                    $scope.filterForm = angular.copy(initFilterForm);
                };
            }
        ]);

