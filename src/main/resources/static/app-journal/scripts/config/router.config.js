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

                var baseViewUri = "app-journal/views/ui/";
                var baseServiceUri = "app-journal/scripts/services/";
                var previsionServiceUri = "app-prevision/scripts/services/";
                var baseControllerUri = "app-journal/scripts/controllers/";
                var categorieServiceUri = "app-categorie/scripts/services/";
                var objectifServiceUri = "app-objectif/scripts/services/";
                var processusServiceUri = "app-processus/scripts/services/";
                var projetServiceUri = "app-projet/scripts/services/";

                $stateProvider
                /*******************************************************************************************/
                // route parent des comptes
                    .state('ui.journaux', {
                        url: '/journaux',
                        abstract: true,
                        template: '<div ui-view></div>',
                        resolve: load(["ui.select",
                            baseServiceUri + 'journaux.service.js',
                            objectifServiceUri + 'objectifs.service.js',
                            processusServiceUri + 'processus.service.js',
                            projetServiceUri + 'projets.service.js',
                            categorieServiceUri + 'categories.service.js',
                            previsionServiceUri + 'previsions.service.js',
                            baseControllerUri + 'journaux.controller.js'])
                    })

                    .state('ui.journaux.edit', {
                        url: '/edit',
                        controller: 'JournauxControllerEdit',
                        templateUrl: baseViewUri + 'journaux.edit.html',
                        ncyBreadcrumb: {
                            parent: 'Accueil',
                            label: 'Journal'
                        }
                    })
                    .state('ui.journaux.import', {
                        url: '/import',
                        controller: 'JournauxControllerImport',
                        templateUrl: baseViewUri + 'journaux.import.html',
                        ncyBreadcrumb: {
                            parent: 'Accueil',
                            label: 'Journal'
                        }
                    })
                    .state('ui.journaux.grid', {
                        url: '/grid',
                        controller: 'JournauxControllerDataGrid',
                        templateUrl: baseViewUri + 'journaux.grid.html',
                        ncyBreadcrumb: {
                            parent: 'Accueil',
                            label: 'Journal'
                        }
                    })
                ;
            }
        ]
    );

