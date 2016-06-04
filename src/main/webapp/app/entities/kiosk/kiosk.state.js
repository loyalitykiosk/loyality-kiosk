(function() {
    'use strict';

    angular
        .module('kioskApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kiosk', {
            parent: 'entity',
            url: '/kiosk?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kioskApp.kiosk.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kiosk/kiosks.html',
                    controller: 'KioskController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('kiosk');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('kiosk-detail', {
            parent: 'entity',
            url: '/kiosk/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kioskApp.kiosk.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kiosk/kiosk-detail.html',
                    controller: 'KioskDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('kiosk');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Kiosk', function($stateParams, Kiosk) {
                    return Kiosk.get({id : $stateParams.id});
                }]
            }
        })
        .state('kiosk.new', {
            parent: 'kiosk',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kiosk/kiosk-dialog.html',
                    controller: 'KioskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                location: null,
                                license: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('kiosk', null, { reload: true });
                }, function() {
                    $state.go('kiosk');
                });
            }]
        })
        .state('kiosk.edit', {
            parent: 'kiosk',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kiosk/kiosk-dialog.html',
                    controller: 'KioskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Kiosk', function(Kiosk) {
                            return Kiosk.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kiosk', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kiosk.delete', {
            parent: 'kiosk',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kiosk/kiosk-delete-dialog.html',
                    controller: 'KioskDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Kiosk', function(Kiosk) {
                            return Kiosk.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kiosk', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
