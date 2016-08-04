(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('CampaignDeleteController',CampaignDeleteController);

    CampaignDeleteController.$inject = ['$uibModalInstance', 'entity', 'Campaign'];

    function CampaignDeleteController($uibModalInstance, entity, Campaign) {
        var vm = this;
        vm.campaign = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Campaign.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
