(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('CampaignDetailController', CampaignDetailController);

    CampaignDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Campaign', 'User', 'Promotion'];

    function CampaignDetailController($scope, $rootScope, $stateParams, entity, Campaign, User, Promotion) {
        var vm = this;
        vm.campaign = entity;

        var unsubscribe = $rootScope.$on('kioskApp:campaignUpdate', function(event, result) {
            vm.campaign = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
