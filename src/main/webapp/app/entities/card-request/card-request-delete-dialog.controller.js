(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('CardRequestDeleteController',CardRequestDeleteController);

    CardRequestDeleteController.$inject = ['$uibModalInstance', 'entity', 'CardRequest'];

    function CardRequestDeleteController($uibModalInstance, entity, CardRequest) {
        var vm = this;
        vm.cardRequest = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            CardRequest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
