(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('CardRequestController', CardRequestController);

    CardRequestController.$inject = ['$scope', '$state', 'CardRequest'];

    function CardRequestController ($scope, $state, CardRequest) {
        var vm = this;
        vm.cardRequests = [];
        vm.loadAll = function() {
            CardRequest.query(function(result) {
                vm.cardRequests = result;
            });
        };

        vm.loadAll();
        
    }
})();
