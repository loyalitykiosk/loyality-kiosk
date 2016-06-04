'use strict';

describe('Controller Tests', function() {

    describe('Kiosk Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKiosk, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKiosk = jasmine.createSpy('MockKiosk');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Kiosk': MockKiosk,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("KioskDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kioskApp:kioskUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
