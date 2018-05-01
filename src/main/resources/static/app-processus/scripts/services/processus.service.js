/**
 * Service de la gestion des comptes
 *
 * @param $resource
 * @returns
 */
angular.module('app')
    .factory('ProcessusService', ['$resource',
        function ($resource) {

            return $resource('processus/:processusId', {id: '@id'}, {

                update: {method: 'PUT'},

                findAll: {method: 'GET', isArray: false},

                search: {url: 'processus/search', method: 'POST', isArray: false}
            });
        }]);