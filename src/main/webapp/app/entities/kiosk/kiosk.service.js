(function() {
    'use strict';
    angular
        .module('kioskApp')
        .factory('Kiosk', Kiosk);

    Kiosk.$inject = ['$resource'];

    function Kiosk ($resource) {
        var resourceUrl =  'api/kiosks/:id';

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
