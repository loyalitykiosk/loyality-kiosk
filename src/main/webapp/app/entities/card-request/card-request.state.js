(function() {
    'use strict';

    angular
        .module('kioskApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('card-request', {
            parent: 'entity',
            url: '/card-request',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kioskApp.cardRequest.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/card-request/card-requests.html',
                    controller: 'CardRequestController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cardRequest');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('card-request.edit', {
            parent: 'card-request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/card-request/card-request-dialog.html',
                    controller: 'CardRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CardRequest', function(CardRequest) {
                            return CardRequest.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('card-request', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('card-request.delete', {
            parent: 'card-request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/card-request/card-request-delete-dialog.html',
                    controller: 'CardRequestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CardRequest', function(CardRequest) {
                            return CardRequest.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('card-request', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
