/**
 * Controlleur d'édition des objectifs
 */
angular.module('app')
    .controller('ObjectifsControllerEdit',
        ['$scope', '$state', '$stateParams', 'ObjectifService', 'uiNotif', 'utils',
            function ($scope, $state, $stateParams, ObjectifService, uiNotif, utils) {

                /**
                 * Initialisation des données
                 */
                var initObjectifs = {
                    identifiant: null,
                    libelle: '',
                    state: utils.states[0].value
                };

                if ($stateParams.initData === null) {
                    $scope.objectif = angular.copy(initObjectifs);
                } else {
                    $scope.objectif = angular.copy($stateParams.initData);
                }

                /*********************************************************************************/
                /**
                 * Ajout d'un objectif
                 */
                var create = function () {

                    $scope.promise = ObjectifService.save($scope.objectif).$promise;
                    $scope.promise.then(function (response) {

                        $scope.objectif = angular.copy(initObjectifs);
                        uiNotif.info(response.message);

                    }, function (error) {
                        uiNotif.info(error.data.message);
                    });
                };
                /*********************************************************************************/

                /**
                 * Mise à jour d'un objectif
                 */
                var update = function () {

                    $scope.promise = ObjectifService.update($scope.objectif).$promise;
                    $scope.promise.then(function (response) {

                        $scope.objectif = angular.copy(initObjectifs);
                        uiNotif.info(response.message);
                        $state.go('ui.objectifs.list');

                    }, function (error) {
                        $scope.objectif = angular.copy($stateParams.initData);
                        uiNotif.info(error.data.message);
                    });
                };
                /*********************************************************************************/

                /**
                 * Edition de objectif
                 */
                $scope.doEdit = function () {

                    if ($scope.objectif.identifiant === null) {
                        create();
                    } else {
                        update();
                    }
                };

            }
        ]);

/**
 * Controlleur de la page de liste des objectifs
 */
angular.module('app')
    .controller('ObjectifsControllerList',
        ['$scope', '$state', 'ObjectifService', 'uiNotif', 'utils',
            function ($scope, $state, ObjectifService, uiNotif, utils) {
                /**
                 * Initialisation des données
                 */
                $scope.objectifs = [];

                $scope.objectifsSelected = [];

                $scope.query = utils.initPagination;

                var initFilterForm = {
                    objectif: {
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
                 * Liste des objectifs
                 */
                $scope.getAll = function (page, limit) {

                    if ($scope.isResearch) {

                        $scope.filterForm.page = page - 1;
                        $scope.filterForm.size = limit;
                        $scope.doSearch();

                    } else {
                        $scope.promise = ObjectifService.findAll({page: page - 1, size: limit}).$promise;
                        $scope.promise.then(function (response) {

                                $scope.objectifs = response.data.content;
                                $scope.query.count = response.data.totalElements;

                            },
                            function (error) {
                                uiNotif.info(error.data.message);
                            });
                    }
                };

                /*********************************************************************************/
                /**
                 * Appel de la liste des objectifs
                 */
                $scope.getAll($scope.query.page, $scope.query.limit);


                /*********************************************************************************/

                /**
                 * Méthode de suppression d'un objectif
                 */
                $scope.remove = function (idObjectif, ev) {

                    var title = utils.dialogTitleRemoval;
                    var message = 'Cet objectif sera définitivement supprimé';

                    uiNotif.mdDialog(ev, title, message).then(function () {

                        $scope.promise = ObjectifService.remove({objectifId: idObjectif}).$promise;
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
                 * Méthode de recherche des objectifs
                 */
                $scope.doSearch = function () {

                    $scope.isResearch = true;

                    $scope.promise = ObjectifService.search($scope.filterForm).$promise;
                    $scope.promise.then(function (response) {

                            $scope.objectifs = response.data.content;
                            $scope.query.count = response.data.totalElements;

                        },
                        function (error) {
                            uiNotif.info(error.data.message);
                        });
                };
                /*********************************************************************************/

                /**
                 * Méthode pour rafraichir la liste des objectifs
                 */
                $scope.refresh = function () {
                    $scope.isResearch = false;
                    $scope.getAll($scope.query.page, $scope.query.limit);
                    $scope.filterForm = angular.copy(initFilterForm);
                };
            }
        ]);

