/**
 * Service de la gestion des comptes
 *
 * @param $resource
 * @returns
 */
angular.module('app')
    .factory('ObjectifService', ['$resource',
        function ($resource) {

            return $resource('objectifs/:objectifId', {id: '@id'}, {

                update: {method: 'PUT'},

                findAll: {method: 'GET', isArray: false},

                search: {url: 'objectifs/search', method: 'POST', isArray: false}
            });
        }]);