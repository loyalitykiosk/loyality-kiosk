(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('KioskDetailController', KioskDetailController);

    KioskDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Kiosk', 'User'];

    function KioskDetailController($scope, $rootScope, $stateParams, entity, Kiosk, User) {
        var vm = this;
        vm.kiosk = entity;
        
        var unsubscribe = $rootScope.$on('kioskApp:kioskUpdate', function(event, result) {
            vm.kiosk = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
