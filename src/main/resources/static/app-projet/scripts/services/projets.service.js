/**
 * Service de la gestion des comptes
 *
 * @param $resource
 * @returns
 */
angular.module('app')
    .factory('ProjetService', ['$resource',
        function ($resource) {

            return $resource('projets/:projetId', {id: '@id'}, {

                update: {method: 'PUT'},

                findAll: {method: 'GET', isArray: false},

                search: {url: 'projets/search', method: 'POST', isArray: false}
            });
        }]);