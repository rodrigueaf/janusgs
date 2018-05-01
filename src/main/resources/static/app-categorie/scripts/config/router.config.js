/**
 * @ngdoc function
 * @name app.config:uiRouter
 * @description
 *
 * Configuration des routes des pages du microservice security
 */
angular.module('app')
    .config(['$stateProvider',
            function ($stateProvider) {

                var baseViewUri = "app-categorie/views/ui/";
                var baseServiceUri = "app-categorie/scripts/services/";
                var baseControllerUri = "app-categorie/scripts/controllers/";

                $stateProvider
                /*******************************************************************************************/
                // route parent des categories
                    .state('ui.categories', {
                        url: '/categories',
                        abstract: true,
                        template: '<div ui-view></div>',
                        resolve: load(['ui.select', baseServiceUri + 'categories.service.js',
                            baseControllerUri + 'categories.controller.js'])
                    })

                    // route vers la modification ou l'ajout d'un compte
                    .state('ui.categories.edit', {
                        url: '/edit',
                        params: {
                            initData: null,
                            isDetails: false
                        },
                        controller: 'CategoriesControllerEdit',
                        templateUrl: baseViewUri + 'categories.edit.html',
                        ncyBreadcrumb: {
                            parent: 'ui.categories.list',
                            label: '{{breadCrumbLabel}}'
                        }
                    })

                    // route vers la liste des categories
                    .state('ui.categories.list', {
                        url: '/list',
                        templateUrl: baseViewUri + 'categories.list.html',
                        controller: 'CategoriesControllerList',
                        resolve: load(['ui.select']),
                        ncyBreadcrumb: {
                            parent: 'home',
                            label: 'Liste des categories'
                        }

                    })
                ;
            }
        ]
    );

