(function() {
    'use strict';
    angular
        .module('kioskApp')
        .factory('Campaign', Campaign);

    Campaign.$inject = ['$resource', 'DateUtils'];

    function Campaign ($resource, DateUtils) {
        var resourceUrl =  'api/campaigns/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date = DateUtils.convertLocalDateFromServer(data.date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var dateToSend = angular.copy(data);
                    dateToSend.date = DateUtils.convertLocalDateToServer(data.date);
                    return angular.toJson(dateToSend);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    // data.date = DateUtils.convertLocalDateToServer(data.date);
                    var dateToSend = angular.copy(data);
                    dateToSend.date = DateUtils.convertLocalDateToServer(data.date);
                    return angular.toJson(dateToSend);
                }
            },
            'validate': {
                method: 'POST',
                url : 'api/campaigns/count',
                transformRequest: function (data) {
                    // data.date = DateUtils.convertLocalDateToServer(data.date);
                    var dateToSend = angular.copy(data);
                    dateToSend.date = DateUtils.convertLocalDateToServer(data.date);
                    return angular.toJson(dateToSend);
                }
            }
        });
    }
})();
