(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('PromotionDetailController', PromotionDetailController);

    PromotionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Promotion', 'User', 'Card'];

    function PromotionDetailController($scope, $rootScope, $stateParams, entity, Promotion, User, Card) {
        var vm = this;
        vm.promotion = entity;
        
        var unsubscribe = $rootScope.$on('kioskApp:promotionUpdate', function(event, result) {
            vm.promotion = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
