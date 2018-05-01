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

                var baseViewUri = "app-objectif/views/ui/";
                var baseServiceUri = "app-objectif/scripts/services/";
                var baseControllerUri = "app-objectif/scripts/controllers/";

                $stateProvider
                /*******************************************************************************************/
                // route parent des objectifs
                    .state('ui.objectifs', {
                        url: '/objectifs',
                        abstract: true,
                        template: '<div ui-view></div>',
                        resolve: load(['ui.select', baseServiceUri + 'objectifs.service.js',
                            baseControllerUri + 'objectifs.controller.js'])
                    })

                    // route vers la modification ou l'ajout d'un compte
                    .state('ui.objectifs.edit', {
                        url: '/edit',
                        params: {
                            initData: null,
                            isDetails: false
                        },
                        controller: 'ObjectifsControllerEdit',
                        templateUrl: baseViewUri + 'objectifs.edit.html',
                        ncyBreadcrumb: {
                            parent: 'ui.objectifs.list',
                            label: '{{breadCrumbLabel}}'
                        }
                    })

                    // route vers la liste des objectifs
                    .state('ui.objectifs.list', {
                        url: '/list',
                        templateUrl: baseViewUri + 'objectifs.list.html',
                        controller: 'ObjectifsControllerList',
                        resolve: load(['ui.select']),
                        ncyBreadcrumb: {
                            parent: 'home',
                            label: 'Liste des objectifs'
                        }

                    })
                ;
            }
        ]
    );

