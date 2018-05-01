/**
 * Service de la gestion des comptes
 *
 * @param $resource
 * @returns
 */
angular.module('app')
    .factory('CategorieService', ['$resource',
        function ($resource) {

            return $resource('categories/:categorieId', {id: '@id'}, {

                update: {method: 'PUT'},

                findAll: {method: 'GET', isArray: false},

                search: {url: 'categories/search', method: 'POST', isArray: false}
            });
        }]);