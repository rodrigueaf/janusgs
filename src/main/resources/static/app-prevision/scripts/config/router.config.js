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

                var baseViewUri = "app-prevision/views/ui/";
                var baseServiceUri = "app-prevision/scripts/services/";
                var categorieServiceUri = "app-categorie/scripts/services/";
                var baseControllerUri = "app-prevision/scripts/controllers/";
                var objectifServiceUri = "app-objectif/scripts/services/";
                var processusServiceUri = "app-processus/scripts/services/";
                var projetServiceUri = "app-projet/scripts/services/";

                $stateProvider
                /*******************************************************************************************/
                // route parent des comptes
                    .state('ui.previsions', {
                        url: '/previsions',
                        abstract: true,
                        template: '<div ui-view></div>',
                        resolve: load(["ui.select",
                            baseServiceUri + 'previsions.service.js',
                            objectifServiceUri + 'objectifs.service.js',
                            processusServiceUri + 'processus.service.js',
                            projetServiceUri + 'projets.service.js',
                            categorieServiceUri + 'categories.service.js',
                            baseControllerUri + 'previsions.controller.js'])
                    })

                    // route vers la modification ou l'ajout d'un compte
                    .state('ui.previsions.edit', {
                        url: '/edit',
                        controller: 'PrevisionsControllerEdit',
                        templateUrl: baseViewUri + 'previsions.edit.html',
                        ncyBreadcrumb: {
                            parent: 'Accueil',
                            label: 'Prévision'
                        }
                    })
                ;
            }
        ]
    );

