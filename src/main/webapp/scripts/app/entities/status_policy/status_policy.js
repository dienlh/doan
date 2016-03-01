'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_policy', {
                parent: 'app',
                url: '/status_policys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_policys'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_policy/status_policys.html',
                        controller: 'Status_policyController'
                    }
                },
                resolve: {
                }
            })
            .state('status_policy.detail', {
                parent: 'app',
                url: '/status_policy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_policy'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_policy/status_policy-detail.html',
                        controller: 'Status_policyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_policy', function($stateParams, Status_policy) {
                        return Status_policy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_policy.new', {
                parent: 'status_policy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_policy/status_policy-dialog.html',
                        controller: 'Status_policyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('status_policy', null, { reload: true });
                    }, function() {
                        $state.go('status_policy');
                    })
                }]
            })
            .state('status_policy.edit', {
                parent: 'status_policy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_policy/status_policy-dialog.html',
                        controller: 'Status_policyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_policy', function(Status_policy) {
                                return Status_policy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_policy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_policy.delete', {
                parent: 'status_policy',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_policy/status_policy-delete-dialog.html',
                        controller: 'Status_policyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_policy', function(Status_policy) {
                                return Status_policy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_policy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
