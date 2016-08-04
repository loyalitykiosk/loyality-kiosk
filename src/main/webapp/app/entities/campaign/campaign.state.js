(function() {
    'use strict';

    angular
        .module('kioskApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('campaign', {
            parent: 'app',
            url: '/campaign?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kioskApp.campaign.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/campaign/campaigns.html',
                    controller: 'CampaignController',
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
                    $translatePartialLoader.addPart('campaign');
                    $translatePartialLoader.addPart('cardType');
                    $translatePartialLoader.addPart('campaignType');
                    $translatePartialLoader.addPart('campaignStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('campaign-detail', {
            parent: 'entity',
            url: '/campaign/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kioskApp.campaign.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/campaign/campaign-detail.html',
                    controller: 'CampaignDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('campaign');
                    $translatePartialLoader.addPart('cardType');
                    $translatePartialLoader.addPart('campaignType');
                    $translatePartialLoader.addPart('campaignStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Campaign', function($stateParams, Campaign) {
                    return Campaign.get({id : $stateParams.id});
                }]
            }
        })
        .state('campaign.newcustom', {
            parent: 'campaign',
            url: '/new/custom',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campaign/campaign-dialog.html',
                    controller: 'CampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                customText: null,
                                cardType: null,
                                date: null,
                                type: 'CUSTOM',
                                status: null,
                                id: null,
                                promotion: {}
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('campaign', null, { reload: true });
                }, function() {
                    $state.go('campaign');
                });
            }]
        }).state('campaign.newpromo', {
                parent: 'campaign',
                url: '/new/promotion',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/campaign/campaign-dialog.html',
                        controller: 'CampaignDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    customText: null,
                                    cardType: null,
                                    date: null,
                                    type: 'PROMOTION',
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('campaign', null, { reload: true });
                    }, function() {
                        $state.go('campaign');
                    });
                }]
        })
        .state('campaign.delete', {
            parent: 'campaign',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campaign/campaign-delete-dialog.html',
                    controller: 'CampaignDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Campaign', function(Campaign) {
                            return Campaign.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('campaign', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
