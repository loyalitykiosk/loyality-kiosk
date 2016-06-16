(function() {
    'use strict';
    angular
        .module('kioskApp')
        .factory('CardRequest', CardRequest);

    CardRequest.$inject = ['$resource'];

    function CardRequest ($resource) {
        var resourceUrl =  'api/card-requests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
