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

                var baseViewUri = "app-processus/views/ui/";
                var baseServiceUri = "app-processus/scripts/services/";
                var baseControllerUri = "app-processus/scripts/controllers/";

                $stateProvider
                /*******************************************************************************************/
                // route parent des processus
                    .state('ui.processus', {
                        url: '/processus',
                        abstract: true,
                        template: '<div ui-view></div>',
                        resolve: load(['ui.select', baseServiceUri + 'processus.service.js',
                            baseControllerUri + 'processus.controller.js'])
                    })

                    // route vers la modification ou l'ajout d'un compte
                    .state('ui.processus.edit', {
                        url: '/edit',
                        params: {
                            initData: null,
                            isDetails: false
                        },
                        controller: 'ProcessusControllerEdit',
                        templateUrl: baseViewUri + 'processus.edit.html',
                        ncyBreadcrumb: {
                            parent: 'ui.processus.list',
                            label: '{{breadCrumbLabel}}'
                        }
                    })

                    // route vers la liste des processus
                    .state('ui.processus.list', {
                        url: '/list',
                        templateUrl: baseViewUri + 'processus.list.html',
                        controller: 'ProcessusControllerList',
                        resolve: load(['ui.select']),
                        ncyBreadcrumb: {
                            parent: 'home',
                            label: 'Liste des processus'
                        }

                    })
                ;
            }
        ]
    );

