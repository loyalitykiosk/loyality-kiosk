(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('CardRequestDialogController', CardRequestDialogController);

    CardRequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CardRequest', 'Kiosk'];

    function CardRequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CardRequest, Kiosk) {
        var vm = this;
        vm.cardRequest = entity;
        vm.kiosks = Kiosk.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kioskApp:cardRequestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            CardRequest.update(vm.cardRequest, onSaveSuccess, onSaveError);
        };

        vm.createCard = function () {
            vm.isSaving = true;
            CardRequest.save(vm.cardRequest, onSaveSuccess, onSaveError);
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
