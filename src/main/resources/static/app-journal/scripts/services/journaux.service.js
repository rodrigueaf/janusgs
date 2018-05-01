/**
 * Service de la gestion des comptees
 *
 * @param $resource
 * @returns
 */
angular.module('app')
    .factory('JournalService', ['$resource',
        function ($resource) {

            return $resource('journaux/:journalId', {id: '@id'}, {
                update: {method: 'PUT'},

                findAll: {method: 'GET', isArray: false},

                search: {url: 'journaux/search', method: 'POST', isArray: false}
            });
        }]);