/**
 * Controlleur d'édition des categories
 */
angular.module('app')
    .controller('CategoriesControllerEdit',
        ['$scope', '$state', '$stateParams', 'CategorieService', 'uiNotif', 'utils',
            function ($scope, $state, $stateParams, CategorieService, uiNotif, utils) {

                /**
                 * Initialisation des données
                 */
                var initCategories = {
                    identifiant: null,
                    libelle: '',
                    categorieParent: null,
                    state: utils.states[0].value
                };

                if ($stateParams.initData === null) {
                    $scope.categorie = angular.copy(initCategories);
                } else {
                    $scope.categorie = angular.copy($stateParams.initData);
                }

                /*********************************************************************************/
                /**
                 * Ajout d'un categorie
                 */
                var create = function () {

                    $scope.promise = CategorieService.save($scope.categorie).$promise;
                    $scope.promise.then(function (response) {

                        $scope.categorie = angular.copy(initCategories);
                        uiNotif.info(response.message);

                    }, function (error) {
                        uiNotif.info(error.data.message);
                    });
                };
                /*********************************************************************************/

                /**
                 * Mise à jour d'un categorie
                 */
                var update = function () {

                    $scope.promise = CategorieService.update($scope.categorie).$promise;
                    $scope.promise.then(function (response) {

                        $scope.categorie = angular.copy(initCategories);
                        uiNotif.info(response.message);
                        $state.go('ui.categories.list');

                    }, function (error) {
                        $scope.categorie = angular.copy($stateParams.initData);
                        uiNotif.info(error.data.message);
                    });
                };
                /*********************************************************************************/

                /**
                 * Edition de categorie
                 */
                $scope.doEdit = function () {

                    if ($scope.categorie.identifiant === null) {
                        create();
                    } else {
                        update();
                    }
                };

                /**
                 * récupérer dynamiquement les categories
                 * par lot de <code>$scope.max</code>
                 *
                 * @param $select
                 * @param $event
                 */
                $scope.fetch = function ($select, $event) {

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
            }
        ]);

/**
 * Controlleur de la page de liste des categories
 */
angular.module('app')
    .controller('CategoriesControllerList',
        ['$scope', '$state', 'CategorieService', 'uiNotif', 'utils', '$locale',
            function ($scope, $state, CategorieService, uiNotif, utils, $locale) {
                $locale.NUMBER_FORMATS.GROUP_SEP = ' ';
                /**
                 * Initialisation des données
                 */
                $scope.categories = [];

                $scope.categoriesSelected = [];

                $scope.query = utils.initPagination;

                var initFilterForm = {
                    categorie: {
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
                 * Liste des categories
                 */
                $scope.getAll = function (page, limit) {

                    if ($scope.isResearch) {

                        $scope.filterForm.page = page - 1;
                        $scope.filterForm.size = limit;
                        $scope.doSearch();

                    } else {
                        $scope.promise = CategorieService.findAll({page: page - 1, size: limit}).$promise;
                        $scope.promise.then(function (response) {

                                $scope.categories = response.data.content;
                                $scope.query.count = response.data.totalElements;

                            },
                            function (error) {
                                uiNotif.info(error.data.message);
                            });
                    }
                };

                /*********************************************************************************/
                /**
                 * Appel de la liste des categories
                 */
                $scope.getAll($scope.query.page, $scope.query.limit);


                /*********************************************************************************/

                /**
                 * Méthode de suppression d'un categorie
                 */
                $scope.remove = function (idCategorie, ev) {

                    var title = utils.dialogTitleRemoval;
                    var message = 'Ce categorie sera définitivement supprimé';

                    uiNotif.mdDialog(ev, title, message).then(function () {

                        $scope.promise = CategorieService.remove({categorieId: idCategorie}).$promise;
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
                 * Méthode de recherche des categories
                 */
                $scope.doSearch = function () {

                    $scope.isResearch = true;

                    $scope.promise = CategorieService.search($scope.filterForm).$promise;
                    $scope.promise.then(function (response) {

                            $scope.categories = response.data.content;
                            $scope.query.count = response.data.totalElements;

                        },
                        function (error) {
                            uiNotif.info(error.data.message);
                        });
                };
                /*********************************************************************************/

                /**
                 * Méthode pour rafraichir la liste des categories
                 */
                $scope.refresh = function () {
                    $scope.isResearch = false;
                    $scope.getAll($scope.query.page, $scope.query.limit);
                    $scope.filterForm = angular.copy(initFilterForm);
                };
            }
        ]);

