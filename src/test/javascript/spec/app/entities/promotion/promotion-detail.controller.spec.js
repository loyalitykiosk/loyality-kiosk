'use strict';

describe('Controller Tests', function() {

    describe('Promotion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPromotion, MockUser, MockCard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPromotion = jasmine.createSpy('MockPromotion');
            MockUser = jasmine.createSpy('MockUser');
            MockCard = jasmine.createSpy('MockCard');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Promotion': MockPromotion,
                'User': MockUser,
                'Card': MockCard
            };
            createController = function() {
                $injector.get('$controller')("PromotionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kioskApp:promotionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
