/**
 * Created by PC27 on 30/11/2017.
 */
/**
 * Controlleur de la page d'accueil
 */
angular.module('app')
    .controller('HomeController',
        ['$scope', '$state',
            function ($scope, $state) {

                $scope.prevsionClick = function () {
                    $state.go('ui.previsions.edit');
                };

                $scope.journalClick = function () {
                    $state.go('ui.journaux.edit');
                };

                $scope.utilisateurClick = function () {
                    $state.go('ui.utilisateurs.list');
                };

                $scope.categorieClick = function () {
                    $state.go('ui.categories.list');
                };
                $scope.projetClick = function () {
                    $state.go('ui.projets.list');
                };
                $scope.processusClick = function () {
                    $state.go('ui.processus.list');
                };
                $scope.objectifClick = function () {
                    $state.go('ui.objectifs.list');
                }
            }
        ]);
