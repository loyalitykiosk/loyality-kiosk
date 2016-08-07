(function() {
    'use strict';

    angular
        .module('kioskApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('promotion', {
            parent: 'entity',
            url: '/promotion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kioskApp.promotion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/promotion/promotions.html',
                    controller: 'PromotionController',
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
                    $translatePartialLoader.addPart('promotion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('promotion-detail', {
            parent: 'entity',
            url: '/promotion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kioskApp.promotion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/promotion/promotion-detail.html',
                    controller: 'PromotionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('promotion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Promotion', function($stateParams, Promotion) {
                    return Promotion.get({id : $stateParams.id});
                }]
            }
        })
        .state('promotion.new', {
            parent: 'promotion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotion/promotion-dialog.html',
                    controller: 'PromotionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                dateStart: null,
                                dateEnd: null,
                                prizeName: null,
                                smsText: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('promotion', null, { reload: true });
                }, function() {
                    $state.go('promotion');
                });
            }]
        })
        .state('promotion.edit', {
            parent: 'promotion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotion/promotion-dialog.html',
                    controller: 'PromotionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Promotion', function(Promotion) {
                            return Promotion.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('promotion', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('promotion.delete', {
            parent: 'promotion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotion/promotion-delete-dialog.html',
                    controller: 'PromotionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Promotion', function(Promotion) {
                            return Promotion.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('promotion', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
