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

                var baseViewUri = "app-projet/views/ui/";
                var baseServiceUri = "app-projet/scripts/services/";
                var baseControllerUri = "app-projet/scripts/controllers/";

                $stateProvider
                /*******************************************************************************************/
                // route parent des projets
                    .state('ui.projets', {
                        url: '/projets',
                        abstract: true,
                        template: '<div ui-view></div>',
                        resolve: load(['ui.select', baseServiceUri + 'projets.service.js',
                            baseControllerUri + 'projets.controller.js'])
                    })

                    // route vers la modification ou l'ajout d'un compte
                    .state('ui.projets.edit', {
                        url: '/edit',
                        params: {
                            initData: null,
                            isDetails: false
                        },
                        controller: 'ProjetsControllerEdit',
                        templateUrl: baseViewUri + 'projets.edit.html',
                        ncyBreadcrumb: {
                            parent: 'ui.projets.list',
                            label: '{{breadCrumbLabel}}'
                        }
                    })

                    // route vers la liste des projets
                    .state('ui.projets.list', {
                        url: '/list',
                        templateUrl: baseViewUri + 'projets.list.html',
                        controller: 'ProjetsControllerList',
                        resolve: load(['ui.select']),
                        ncyBreadcrumb: {
                            parent: 'home',
                            label: 'Liste des projets'
                        }

                    })
                ;
            }
        ]
    );

