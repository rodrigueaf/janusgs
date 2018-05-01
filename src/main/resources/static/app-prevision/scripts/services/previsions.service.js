/**
 * Service de la gestion des comptees
 *
 * @param $resource
 * @returns
 */
angular.module('app')
    .factory('PrevisionService', ['$resource',
        function ($resource) {

            return $resource('previsions/:previsionId', {id: '@id'}, {
                update: {method: 'PUT'},

                findAll: {method: 'GET', isArray: false},

                findOne: {method: 'GET', isArray: false},

                search: {url: 'previsions/search', method: 'POST', isArray: false}
            });
        }]);