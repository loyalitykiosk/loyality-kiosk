(function() {
    'use strict';
    angular
        .module('kioskApp')
        .factory('Promotion', Promotion);

    Promotion.$inject = ['$resource', 'DateUtils'];

    function Promotion ($resource, DateUtils) {
        var resourceUrl =  'api/promotions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateStart = DateUtils.convertLocalDateFromServer(data.dateStart);
                    data.dateEnd = DateUtils.convertLocalDateFromServer(data.dateEnd);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateStart = DateUtils.convertLocalDateToServer(data.dateStart);
                    data.dateEnd = DateUtils.convertLocalDateToServer(data.dateEnd);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateStart = DateUtils.convertLocalDateToServer(data.dateStart);
                    data.dateEnd = DateUtils.convertLocalDateToServer(data.dateEnd);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
