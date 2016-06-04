(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('KioskDeleteController',KioskDeleteController);

    KioskDeleteController.$inject = ['$uibModalInstance', 'entity', 'Kiosk'];

    function KioskDeleteController($uibModalInstance, entity, Kiosk) {
        var vm = this;
        vm.kiosk = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Kiosk.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
