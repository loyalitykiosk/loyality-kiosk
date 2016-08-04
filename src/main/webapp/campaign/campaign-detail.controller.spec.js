'use strict';

describe('Controller Tests', function() {

    describe('Campaign Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCampaign, MockUser, MockPromotion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCampaign = jasmine.createSpy('MockCampaign');
            MockUser = jasmine.createSpy('MockUser');
            MockPromotion = jasmine.createSpy('MockPromotion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Campaign': MockCampaign,
                'User': MockUser,
                'Promotion': MockPromotion
            };
            createController = function() {
                $injector.get('$controller')("CampaignDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kioskApp:campaignUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
