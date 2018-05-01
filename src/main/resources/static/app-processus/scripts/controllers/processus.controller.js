/**
 * Controlleur d'édition des processus
 */
angular.module('app')
    .controller('ProcessusControllerEdit',
        ['$scope', '$state', '$stateParams', 'ProcessusService', 'uiNotif', 'utils',
            function ($scope, $state, $stateParams, ProcessusService, uiNotif, utils) {

                /**
                 * Initialisation des données
                 */
                var initProcessus = {
                    identifiant: null,
                    libelle: '',
                    state: utils.states[0].value
                };

                if ($stateParams.initData === null) {
                    $scope.processus = angular.copy(initProcessus);
                } else {
                    $scope.processus = angular.copy($stateParams.initData);
                }

                /*********************************************************************************/
                /**
                 * Ajout d'un processus
                 */
                var create = function () {

                    $scope.promise = ProcessusService.save($scope.processus).$promise;
                    $scope.promise.then(function (response) {

                        $scope.processus = angular.copy(initProcessus);
                        uiNotif.info(response.message);

                    }, function (error) {
                        uiNotif.info(error.data.message);
                    });
                };
                /*********************************************************************************/

                /**
                 * Mise à jour d'un processus
                 */
                var update = function () {

                    $scope.promise = ProcessusService.update($scope.processus).$promise;
                    $scope.promise.then(function (response) {

                        $scope.processus = angular.copy(initProcessus);
                        uiNotif.info(response.message);
                        $state.go('ui.processus.list');

                    }, function (error) {
                        $scope.processus = angular.copy($stateParams.initData);
                        uiNotif.info(error.data.message);
                    });
                };
                /*********************************************************************************/

                /**
                 * Edition de processus
                 */
                $scope.doEdit = function () {

                    if ($scope.processus.identifiant === null) {
                        create();
                    } else {
                        update();
                    }
                };

            }
        ]);

/**
 * Controlleur de la page de liste des processus
 */
angular.module('app')
    .controller('ProcessusControllerList',
        ['$scope', '$state', 'ProcessusService', 'uiNotif', 'utils',
            function ($scope, $state, ProcessusService, uiNotif, utils) {
                /**
                 * Initialisation des données
                 */
                $scope.processuss = [];

                $scope.processusSelected = [];

                $scope.query = utils.initPagination;

                var initFilterForm = {
                    processus: {
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
                 * Liste des processus
                 */
                $scope.getAll = function (page, limit) {

                    if ($scope.isResearch) {

                        $scope.filterForm.page = page - 1;
                        $scope.filterForm.size = limit;
                        $scope.doSearch();

                    } else {
                        $scope.promise = ProcessusService.findAll({page: page - 1, size: limit}).$promise;
                        $scope.promise.then(function (response) {

                                $scope.processuss = response.data.content;
                                $scope.query.count = response.data.totalElements;

                            },
                            function (error) {
                                uiNotif.info(error.data.message);
                            });
                    }
                };

                /*********************************************************************************/
                /**
                 * Appel de la liste des processus
                 */
                $scope.getAll($scope.query.page, $scope.query.limit);


                /*********************************************************************************/

                /**
                 * Méthode de suppression d'un processus
                 */
                $scope.remove = function (idProjet, ev) {

                    var title = utils.dialogTitleRemoval;
                    var message = 'Ce processus sera définitivement supprimé';

                    uiNotif.mdDialog(ev, title, message).then(function () {

                        $scope.promise = ProcessusService.remove({processusId: idProjet}).$promise;
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
                 * Méthode de recherche des processus
                 */
                $scope.doSearch = function () {

                    $scope.isResearch = true;

                    $scope.promise = ProcessusService.search($scope.filterForm).$promise;
                    $scope.promise.then(function (response) {

                            $scope.processuss = response.data.content;
                            $scope.query.count = response.data.totalElements;

                        },
                        function (error) {
                            uiNotif.info(error.data.message);
                        });
                };
                /*********************************************************************************/

                /**
                 * Méthode pour rafraichir la liste des processus
                 */
                $scope.refresh = function () {
                    $scope.isResearch = false;
                    $scope.getAll($scope.query.page, $scope.query.limit);
                    $scope.filterForm = angular.copy(initFilterForm);
                };
            }
        ]);

