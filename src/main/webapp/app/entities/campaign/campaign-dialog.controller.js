(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('CampaignDialogController', CampaignDialogController);

    CampaignDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Campaign', 'User', 'Promotion','Principal'];

    function CampaignDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Campaign, User, Promotion, Principal) {
        var vm = this;
        vm.campaign = entity;
        vm.users = User.query();
        vm.promotions = Promotion.query();
        vm.isValid = isFormValid;
        vm.smsNumber = 0;
        vm.smsBalance = 0;
        Principal.identity(true).then(function(account) {
            vm.smsBalance = account.smsBalance;
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kioskApp:campaignUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.campaign.id !== null) {
                Campaign.update(vm.campaign, onSaveSuccess, onSaveError);
            } else {
                Campaign.save(vm.campaign, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };


        function isFormValid() {
            if (vm.campaign.type == 'PROMOTION'){
                return vm.campaign.date != null && vm.campaign.cardType !=null && vm.campaign.promotion !=null && vm.smsNumber>0;
            }
            if (vm.campaign.type == 'CUSTOM'){
                return vm.campaign.date != null && vm.campaign.cardType !=null && vm.campaign.customText != null && vm.smsNumber>0;
            }
        }


        function countSms() {
            Campaign.validate(vm.campaign,
            function (result){
                vm.smsNumber = result.value;
            },
            function () {
                vm.smsNumber = 0;
            }
            );
        }

        $scope.$watchGroup(['vm.campaign.cardType'],countSms, true);
    }
})();
