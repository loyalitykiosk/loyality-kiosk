(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('PromotionDeleteController',PromotionDeleteController);

    PromotionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Promotion'];

    function PromotionDeleteController($uibModalInstance, entity, Promotion) {
        var vm = this;
        vm.promotion = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Promotion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
