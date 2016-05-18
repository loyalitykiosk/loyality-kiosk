(function() {
    'use strict';
    angular
        .module('kioskApp')
        .factory('Card', Card);

    Card.$inject = ['$resource', 'DateUtils'];

    function Card ($resource, DateUtils) {
        var resourceUrl =  'api/cards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.ownerBirthDate = DateUtils.convertLocalDateFromServer(data.ownerBirthDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var dataToSend = angular.copy(data);
                    dataToSend.ownerBirthDate = DateUtils.convertLocalDateToServer(dataToSend.ownerBirthDate);
                    return angular.toJson(dataToSend);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var dataToSend = angular.copy(data);
                    dataToSend.ownerBirthDate = DateUtils.convertLocalDateToServer(dataToSend.ownerBirthDate);
                    return angular.toJson(dataToSend);
                }
            }
        });
    }
})();
