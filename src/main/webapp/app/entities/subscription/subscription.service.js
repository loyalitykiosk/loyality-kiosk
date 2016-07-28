(function() {
    'use strict';
    angular
        .module('kioskApp')
        .factory('Subscription', Subscription);

    Subscription.$inject = ['$resource'];

    function Subscription ($resource) {
        var resourceUrl =  'api/subscriptions/:id';

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
