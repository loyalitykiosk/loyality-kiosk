(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('CardDetailController', CardDetailController);

    CardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Card', 'User'];

    function CardDetailController($scope, $rootScope, $stateParams, entity, Card, User) {
        var vm = this;
        vm.card = entity;
        
        var unsubscribe = $rootScope.$on('kioskApp:cardUpdate', function(event, result) {
            vm.card = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
